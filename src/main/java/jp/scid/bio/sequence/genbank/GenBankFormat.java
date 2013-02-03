package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;

import jp.scid.bio.sequence.SequenceBioDataFormat;
import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class GenBankFormat extends SequenceBioDataFormat<GenBank> {
//  private final static Logger logger = Logger.getLogger(GenBankFormat.class.getName());

    static enum AttibuteKey {
        LOCUS() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.locusFormat;
            }
        },
        DEFINITION() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.definitionFormat;
            }
        },
        ACCESSION() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.accessionFormat;
            }
        },
        VERSION() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.versionFormat;
            }
        },
        KEYWORDS() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.keywordsFormat;
            }
        },
        SOURCE() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.sourceFormat;
            }
        },
        REFERENCE() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.referenceFormat;
            }
        },
        COMMENT() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.commentFormat;
            }
        },
        FEATURES() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.featuresFormat;
            }
        },
        ORIGIN() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.originFormat;
            }
        },
        BASE_COUNT() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.baseCountFormat;
            }
            
            @Override
            String key() {
                return "BASE COUNT";
            }
        },
        TERMINATE() {
            @Override
            AbstractAttributeFormat getFormat(GenBankFormat owner) {
                return owner.terminateFormat;
            }
            
            @Override
            String key() {
                return "//";
            }
        },
        ;
        
        private final static Map<String, AttibuteKey> keyMap;
        static {
            keyMap = new HashMap<String, AttibuteKey>();
            for (AttibuteKey key: AttibuteKey.values()) {
                keyMap.put(key.key(), key);
            }
        }
        
        private AttibuteKey() {
        }
        
        String key() {
            return name();
        }

        public static AttibuteKey findByKey(String keyString) {
            return keyMap.get(keyString);
        }
        
        abstract AbstractAttributeFormat getFormat(GenBankFormat owner);
    }
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
    private AbstractAttributeFormat baseCountFormat = null;
    private TerminateFormat terminateFormat = new TerminateFormat();
    
    private int attributeKeyDigits = 12;
    
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

    boolean isAttributeStartLine(String line) {
        if (line.startsWith(ELEMENT_CONTINUANCE_PREFIX)) {
            return false;
        }
        else if (LONG_ORIGIN_POSITION_PATTERN.matcher(line).find()) {
            return false;
        }
        
        return true;
    }
    
    private AttibuteKey parseAttributeKey(String line) throws ParseException {
        final String keyString;
        if (line.length() < attributeKeyDigits) {
            keyString = line.trim();
        }
        else {
            keyString = line.substring(0, attributeKeyDigits).trim();
        }
        
        AttibuteKey key = AttibuteKey.findByKey(keyString);
        if (key == null) {
            throw new ParseException("invalid attribute key line: " + line, 0);
        }
        
        return key;
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
            
            AttibuteKey attributeKey = parseAttributeKey(attributeLines.element());
            AbstractAttributeFormat attrFormat = attributeKey.getFormat(GenBankFormat.this);
            if (attrFormat != null) {
                GenBankAttribute attribute = attrFormat.parse(attributeLines);
                attribute.setMeToBuilder(builder);
            }
            
            attributeLines.clear();
        }
    }
}
