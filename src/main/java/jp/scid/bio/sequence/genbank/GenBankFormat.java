package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.scid.bio.GenBank;
import jp.scid.bio.GenBank.Builder;
import jp.scid.bio.GenBankAttribute;
import jp.scid.bio.SequenceBioDataFormat;
import jp.scid.bio.UnknownAttribute;

public class GenBankFormat implements SequenceBioDataFormat<GenBank> {
    private final static Logger logger = Logger.getLogger(GenBankFormat.class.getName());

    private final static String ELEMENT_CONTINUANCE_PREFIX = "  ";

    private int attributeKeyDigits = 12;

    private final Map<String, GenBankAttribute.Format> attributeFormatMap;
    
    private final List<GenBankAttribute.Format> attributeFormats;

    private Terminate.Format terminateFormat = new Terminate.Format();
    
    protected GenBankAttribute.Format firstAttributeFormat;
    
    public GenBankFormat(GenBankAttribute.Format firstAttributeFormat, AbstractAttributeFormat... formats) {
        if (firstAttributeFormat == null)
            throw new IllegalArgumentException("firstAttributeFormat must not be null");
        
        this.firstAttributeFormat = firstAttributeFormat;
        
        this.attributeFormats = Arrays.<GenBankAttribute.Format>asList(formats);
        
        attributeFormatMap = new HashMap<String, GenBankAttribute.Format>();
        
        for (AbstractAttributeFormat format: formats) {
            attributeFormatMap.put(format.getIdentifier(), format);
        }
    }
    
    public GenBankFormat() {
        this(
            new LocusFormat(), new DefinitionFormat(), new Accession.Format(),
            new Version.Format(), new Keywords.Format(), new Source.Format(),
            new Reference.Format(), new Comment.Format(),
            new Features.Format(), new Origin.Format());
    }

    public boolean isStartOfData(String line) {
        return getFirstAttributeFormat().isHeadLine(line);
    }
    
    public boolean isEndOfData(String line) {
        return terminateFormat.isTerminateLine(line);
    }
    
    public GenBankAttribute.Format getFirstAttributeFormat() {
        return firstAttributeFormat;
    }
    
    public GenBank parse(Iterable<String> sourceLines) throws ParseException {
        Iterator<String> source = sourceLines.iterator();
        final String first;
        
        if (!source.hasNext() || !isStartOfData(first = source.next())) {
            throw new ParseException("need LOCUS line at first of source lines", 0);
        }
        
        GenBank.Builder builder = createBuilder();

        AttributeBuilder attrBuilder = new AttributeBuilder(getFirstAttributeFormat());
        attrBuilder.append(first);

        while (source.hasNext()) {
            final String line = source.next();
            
            if (isEndOfData(line)) {
                break;
            }
            
            if (isAttributeHead(line)) {
                appendAttribute(builder, attrBuilder);

                GenBankAttribute.Format nextParser = findAttributeFormat(line);

                if (nextParser == null) {
                    logger.log(Level.WARNING, "Unknown attribute: {0}", line);
                    nextParser = new UnknownAttribute.Format();
                }

                attrBuilder = new AttributeBuilder(nextParser);
            }

            attrBuilder.append(line);
        }

        appendAttribute(builder, attrBuilder);

        GenBank genBank = builder.build();

        return genBank;
    }

    void appendAttribute(GenBank.Builder builder, AttributeBuilder attrBuilder) {
        try {
            GenBankAttribute attribute = attrBuilder.getAttribute();
            builder.setAttribute(attribute);
        }
        catch (ParseException e) {
            logger.log(Level.WARNING, "Cannot parse genbank attribute: " + e.getLocalizedMessage()
                + attrBuilder.getLines());
        }
    }

    protected Builder createBuilder() {
        return new GenBank.Builder();
    }

    static class AttributeBuilder {
        final GenBankAttribute.Format parser;

        final List<String> lines = new LinkedList<String>();

        public AttributeBuilder(GenBankAttribute.Format parser) {
            this.parser = parser;
        }

        public AttributeBuilder() {
            this(new UnknownAttribute.Format());
        }

        public void append(String line) {
            lines.add(line);
        }

        public boolean getLinesEmpty() {
            return lines.isEmpty();
        }
        
        public List<String> getLines() {
            return Collections.unmodifiableList(lines);
        }

        public GenBankAttribute getAttribute() throws ParseException {
            return parser.parse(getLines());
        }
    }

    public boolean isAttributeHead(String line) {
        return !line.startsWith(ELEMENT_CONTINUANCE_PREFIX);
    }

    @SuppressWarnings("unused")
    private String parseAttributeKey(String dataLine) throws ParseException {
        final String key;
        if (dataLine.length() < attributeKeyDigits) {
            key = dataLine;
        }
        else {
            key = dataLine.substring(0, attributeKeyDigits);
        }

        int keyEnd = key.indexOf(' ');
        String tKey = key.substring(0, keyEnd);

        if (tKey.isEmpty()) {
            throw new ParseException("attribute key not found: " + dataLine, 0);
        }

        return tKey;
    }
    
    public GenBankAttribute.Format findAttributeFormat(String line) {
        for (GenBankAttribute.Format format: attributeFormats) {
            if (format.isHeadLine(line))
                return format;
        }
        
        return null;
    }
}
