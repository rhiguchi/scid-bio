package jp.scid.bio;

import java.text.ParseException;

public class Definition implements GenBankAttribute {
    public final static Definition EMPTY = new Definition();

    private final String value;

    Definition(String value) {
        if (value == null) throw new IllegalArgumentException("value must not be null");

        this.value = value;
    }

    Definition() {
        this("");
    }

    public static Definition newDefinition(String value) {
        return new Definition(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Definition [value=").append(value).append("]");
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

    public static class Format extends AbstractAttributeFormat {
        private final static String DEFAULT_IDENTIFIER = "DEFINITION";

        public Format() {
            super(DEFAULT_IDENTIFIER);
        }

        @Override
        public Definition parse(Iterable<String> lines) throws ParseException {
            String value = getValueString(lines, "\n");

            return Definition.newDefinition(value);
        }
    }
}
