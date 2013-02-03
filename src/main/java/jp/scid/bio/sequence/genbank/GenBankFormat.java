package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

import jp.scid.bio.sequence.SequenceBioDataFormat;
import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class GenBankFormat extends SequenceBioDataFormat<GenBank> {
//    private final static Logger logger = Logger.getLogger(GenBankFormat.class.getName());

    private final static String ELEMENT_CONTINUANCE_PREFIX = "  ";
    private final static Pattern LONG_ORIGIN_POSITION_PATTERN = Pattern.compile("^ \\d");

    private LocusFormat locusFormat = new LocusFormat();
    private DefinitionFormat definitionFormat = new DefinitionFormat();
    private AccessionFormat accessionFormat = new AccessionFormat();
    private VersionFormat versionFormat = new VersionFormat();
    private KeywordsFormat keywordsFormat = new KeywordsFormat();
    private SourceFormat sourceFormat = new SourceFormat();
    private ReferenceFormat referenceFormat = new ReferenceFormat();
    private CommentFormat commentFormat = new CommentFormat();
    private FeaturesFormat featuresFormat = new FeaturesFormat();
    private OriginFormat originFormat = new OriginFormat();
    private TerminateFormat terminateFormat = new TerminateFormat();
    
    protected AbstractAttributeFormat firstAttributeFormat;
    
    public GenBankFormat() {
    }

    @Override
    public boolean isDataStartLine(String line) {
        return locusFormat.isHeadLine(line);
    }
    
    @Override
    public LineParser createLineBuilder() {
        GenBank.Builder builder = new GenBank.Builder();
        return new LineParser(builder);
    }
    
    public GenBank parse(Iterable<String> sourceLines) throws ParseException {
        Iterator<String> source = sourceLines.iterator();
        String line;
        
        if (!source.hasNext() || !isDataStartLine(line = source.next())) {
            throw new ParseException("need LOCUS line at first of source lines", 0);
        }

        LineParser parser = createLineBuilder();
        parser.appendLine(line);
        
        while ((line = source.next()) != null) {
            if (isDataStartLine(line)) {
                break;
            }
            
            parser.appendLine(line);
        }
        
        return parser.build();
    }

    protected Builder createBuilder() {
        return new GenBank.Builder();
    }

    AbstractAttributeFormat findAttributeFormat(String line) {
        for (AbstractAttributeFormat format: attributeFormats()) {
            if (format.isHeadLine(line))
                return format;
        }
        
        return null;
    }

    boolean isAttributeStartLine(String line) {
        if (line.startsWith(ELEMENT_CONTINUANCE_PREFIX)) {
            return false;
        }
        else if (LONG_ORIGIN_POSITION_PATTERN.matcher(line).find()) {
            return false;
        }
        
        return true;
    }

    private Iterable<AbstractAttributeFormat> attributeFormats() {
        List<AbstractAttributeFormat> attributeFormats =
                Arrays.<AbstractAttributeFormat>asList(
                        locusFormat, definitionFormat, accessionFormat, versionFormat,
                        keywordsFormat, sourceFormat, referenceFormat, commentFormat,
                        featuresFormat, originFormat, terminateFormat);
        return attributeFormats;
    }
    
    class LineParser extends SequenceBioDataFormat.LineParser<GenBank> {
        private final Queue<String> attributeLines = new ArrayDeque<String>(); 
        private final GenBank.Builder builder;
        
        LineParser(Builder builder) {
            if (builder == null) throw new IllegalArgumentException("builder must not be null");
            this.builder = builder;
        }

        @Override
        public void appendLine(String line) throws ParseException {
            if (isAttributeStartLine(line)) {
                buildAttribute();
            }
            
            attributeLines.add(line);
        }
        
        @Override
        public GenBank build() throws ParseException {
            buildAttribute();
            return builder.build();
        }

        private void buildAttribute() throws ParseException {
            if (attributeLines.isEmpty()) {
                return;
            }
            
            AbstractAttributeFormat attrFormat = findAttributeFormat(attributeLines.element());
            if (attrFormat != null) {
                GenBankAttribute attribute = attrFormat.parse(attributeLines);
                attribute.setMeToBuilder(builder);
            }
            
            attributeLines.clear();
        }
    }
}
