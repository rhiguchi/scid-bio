package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

public class CommentFormat extends AbstractAttributeFormat {
    private final static String DEFAULT_IDENTIFIER = "COMMENT";

    public CommentFormat() {
        super(DEFAULT_IDENTIFIER);
    }

    @Override
    public Comment parse(Iterable<String> lines) throws ParseException {
        String value = getValueString(lines, "\n");

        return Comment.newComment(value);
    }
}