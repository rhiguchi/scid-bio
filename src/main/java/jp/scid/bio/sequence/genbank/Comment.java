package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

import jp.scid.bio.GenBankAttribute;

public class Comment implements GenBankAttribute {
    private final String value;

    Comment(String value) {
        if (value == null) throw new IllegalArgumentException("value must not be null");

        this.value = value;
    }

    Comment() {
        this("");
    }

    public static Comment newComment(String value) {
        return new Comment(value);
    }

    public String getValue() {
        return value;
    }

    public static class Format extends AbstractAttributeFormat {
        private final static String DEFAULT_IDENTIFIER = "COMMENT";

        public Format() {
            super(DEFAULT_IDENTIFIER);
        }

        @Override
        public Comment parse(Iterable<String> lines) throws ParseException {
            String value = getValueString(lines, "\n");

            return Comment.newComment(value);
        }
    }
}
