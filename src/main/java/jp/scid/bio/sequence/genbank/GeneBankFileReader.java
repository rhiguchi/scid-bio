package jp.scid.bio.sequence.genbank;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.scid.bio.GenBank;
import jp.scid.bio.GenBankAttribute;
import jp.scid.bio.GenBankAttribute.Format;
import jp.scid.bio.UnknownAttribute;

public class GeneBankFileReader {
    private final static Logger logger = Logger.getLogger(GeneBankFileReader.class.getName());
    private final static String ELEMENT_CONTINUANCE_PREFIX = "  ";
    
    private final LocusFormat locusFormat = new LocusFormat();
    private final Definition.Format definitionFormat = new Definition.Format();
    private final Accession.Format accessionFormat = new Accession.Format();
    private final Version.Format versionFormat = new Version.Format();
    private final Keywords.Format keywordsFormat = new Keywords.Format();
    private final Source.Format sourceFormat = new Source.Format();
    private final Reference.Format referenceFormat = new Reference.Format();
    private final Comment.Format commentFormat = new Comment.Format();
    private final Features.Format featuresFormat = new Features.Format();
    private final Origin.Format originFormat = new Origin.Format();
    private final Terminate.Format terminateFormat = new Terminate.Format();
    
    private final List<GenBankAttribute.Format> attributeFormats;
    
    public GeneBankFileReader() {
        attributeFormats =
                Arrays.<GenBankAttribute.Format>asList(
                        locusFormat, definitionFormat, accessionFormat, versionFormat,
                        keywordsFormat, sourceFormat, referenceFormat, commentFormat,
                        featuresFormat, originFormat, terminateFormat);
    }
    
    public List<GenBank> readFromBufferedReader(BufferedReader reader) throws IOException {
        Queue<GenBank> results = new ArrayDeque<GenBank>();
        GenBank genBank;
        
        while ((genBank = readGeneBank(reader)) != null) {
            results.add(genBank);
        }
        
        return new ArrayList<GenBank>(results);
    }

    public GenBank readGeneBank(BufferedReader reader) throws IOException {
        // read locus
        String line = reader.readLine();
        if (line == null) {
            return null;
        }

        GeneBankFileReader.AttributeBuilder attrBuilder = new AttributeBuilder(locusFormat);
        attrBuilder.append(line);
        
        // others
        Queue<GenBankAttribute> attributes = new ArrayDeque<GenBankAttribute>(); 
        
        while ((line = reader.readLine()) != null) {
            if (isAttributeStartLine(line)) {
                addAttributeTo(attributes, attrBuilder);
                
                // next attribute
                GenBankAttribute.Format nextParser = findAttributeFormat(line);

                if (nextParser == null) {
                    logger.log(Level.WARNING, "Unknown attribute: {0}", line);
                    nextParser = new UnknownAttribute.Format();
                }

                attrBuilder = new AttributeBuilder(nextParser);
                
                if (nextParser instanceof Terminate.Format) {
                    attrBuilder.append(line);
                    break;
                }
            }

            attrBuilder.append(line);
        }
        
        addAttributeTo(attributes, attrBuilder);
        
        return createGeneBank(attributes);
    }

    boolean isEndOfData(String line) {
        return terminateFormat.isTerminateLine(line);
    }
    
    private void addAttributeTo(
            Queue<GenBankAttribute> attributes, GeneBankFileReader.AttributeBuilder attrBuilder) {
        try {
            GenBankAttribute attribute = attrBuilder.build();
            attributes.add(attribute);
        }
        catch (ParseException e) {
            logger.log(Level.WARNING, "fail to parse attribute " + attrBuilder.attirbuteName(), e);
        }
    }
    
    private Format findAttributeFormat(String line) {
        for (GenBankAttribute.Format format: attributeFormats) {
            if (format.isHeadLine(line))
                return format;
        }
        
        return null;
    }

    private GenBank createGeneBank(Iterable<GenBankAttribute> attributes) {
        GenBank.Builder builder = new GenBank.Builder();
        
        for (GenBankAttribute attribute: attributes) {
            if (attribute instanceof AbstractGenBankAttribute) {
                ((AbstractGenBankAttribute) attribute).setMeToBuilder(builder);
            }
        }
        
        return builder.build();
    }

    public boolean isAttributeStartLine(String line) {
        return !line.startsWith(ELEMENT_CONTINUANCE_PREFIX);
    }

    private static class AttributeBuilder {
        private final GenBankAttribute.Format parser;
        private final Queue<String> lines = new ArrayDeque<String>();

        public AttributeBuilder(GenBankAttribute.Format parser) {
            this.parser = parser;
        }

        public void append(String line) {
            lines.add(line);
        }
        
        String attirbuteName() {
            return parser.getClass().getName();
        }

        public GenBankAttribute build() throws ParseException {
            return parser.parse(lines);
        }
    }
}