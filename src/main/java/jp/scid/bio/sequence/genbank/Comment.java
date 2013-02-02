package jp.scid.bio.sequence.genbank;


import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class Comment extends GenBankAttribute {
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
    
    @Override
    void setMeToBuilder(Builder builder) {
        builder.comment(this);
    }
}
