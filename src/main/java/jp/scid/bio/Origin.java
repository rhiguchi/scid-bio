package jp.scid.bio;

import java.text.ParseException;
import java.util.Iterator;

public class Origin implements GenBankAttribute {
    private final String sequence;

    Origin(String sequence) {
        super();
        this.sequence = sequence;
    } 
    
    public String getSequence() {
        return sequence;
    }
    
    public static class Format extends AbstractAttributeFormat {
        private final static int[] GENBANK_INT_ARRAY =
                new int[]{10, 21, 32, 43, 54, 65};
        
        public Format() {
            super("ORIGIN");
        }
        
        public Origin parse(Iterable<String> source) throws ParseException {
            Iterator<String> sourceIte = source.iterator();
            ensureHeadLineExistence(sourceIte);
            
            StringBuilder sequenceBuilder = new StringBuilder();
            
            while (sourceIte.hasNext()) {
                String line = sourceIte.next();
                parseSequence(sequenceBuilder, line);
            }
            
            Builder builder = new Builder(sequenceBuilder);
            
            return builder.build();
        }
        
        protected void parseSequence(StringBuilder accume, String line) {
            if (line.length() == 75) {
                getGenbankOriginSequence(accume, line);
            }
            else {
                getGenbankOriginSequencePartial(accume, line);
            }
        }
        
        void getGenbankOriginSequence(StringBuilder accume, String line) {
            accume.append(line.substring(10, 20))
            .append(line.substring(21, 31))
            .append(line.substring(32, 42))
            .append(line.substring(43, 53))
            .append(line.substring(54, 64))
            .append(line.substring(65, 75));
        }
        
        void getGenbankOriginSequencePartial(StringBuilder accume, String line) {
            int lineLength = line.length();
            
            for (int start: GENBANK_INT_ARRAY) {
                if (lineLength < start + 10) {
                    accume.append(line.substring(start).trim());
                    break;
                }
                
                accume.append(line.substring(start, start + 10));
            }
        }
    }

    public static class Builder {
        private final StringBuilder sequenceBuilder;

        public Builder(StringBuilder sequenceBuilder) {
            this.sequenceBuilder = sequenceBuilder;
        }
        
        public Builder() {
            this(new StringBuilder());
        }
        
        public Origin build() {
            return new Origin(sequenceBuilder.toString());
        }

        public Builder append(String sequence) {
            sequenceBuilder.append(sequence);
            
            return this;
        }
    }
}
