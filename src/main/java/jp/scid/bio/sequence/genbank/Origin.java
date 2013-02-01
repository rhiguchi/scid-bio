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
    void setMeToBuilder(jp.scid.bio.sequence.genbank.GenBank.Builder builder) {
        builder.origin(this);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Origin other = (Origin) obj;
        if (sequence == null) {
            if (other.sequence != null) return false;
        }
        else if (!sequence.equals(other.sequence)) return false;
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2.append(sequence);
        return builder2.toString();
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
