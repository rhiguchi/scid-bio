package jp.scid.bio.sequence.genbank;



public class Origin extends AbstractGenBankAttribute {
    private final String sequence;

    Origin(String sequence) {
        super();
        this.sequence = sequence;
    } 
    
    public String sequence() {
        return sequence;
    }
    
    @Override
    void setMeToBuilder(jp.scid.bio.GenBank.Builder builder) {
        builder.origin(this);
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
