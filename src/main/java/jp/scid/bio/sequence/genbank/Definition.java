package jp.scid.bio.sequence.genbank;


import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class Definition extends AbstractGenBankAttribute {
    public final static Definition EMPTY = new Definition();

    private final String value;

    private Definition(String value) {
        if (value == null) throw new IllegalArgumentException("value must not be null");

        this.value = value;
    }

    private Definition() {
        this("");
    }

    public static Definition newDefinition(String value) {
        return new Definition(value);
    }

    public String value() {
        return value;
    }
    
    @Override
    void setMeToBuilder(Builder builder) {
        builder.definition(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Definition  ").append(value);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Definition other = (Definition) obj;
        if (value == null) {
            if (other.value != null) return false;
        }
        else if (!value.equals(other.value)) return false;
        return true;
    }
}
