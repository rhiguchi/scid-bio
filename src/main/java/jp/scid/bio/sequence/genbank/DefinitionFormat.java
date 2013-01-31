package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

public class DefinitionFormat extends AbstractAttributeFormat {
    private final static String DEFAULT_IDENTIFIER = "DEFINITION";

    public DefinitionFormat() {
        super(DEFAULT_IDENTIFIER);
    }

    @Override
    public Definition parse(Iterable<String> lines) throws ParseException {
        String value = getValueString(lines, "\n");

        return Definition.newDefinition(value);
    }
}