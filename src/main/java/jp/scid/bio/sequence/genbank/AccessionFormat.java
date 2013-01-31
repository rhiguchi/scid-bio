package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

import jp.scid.bio.sequence.genbank.Accession.Builder;

public class AccessionFormat extends AbstractAttributeFormat {
    private final static String DEFAULT_IDENTIFIER = "ACCESSION";

    public AccessionFormat() {
        super(DEFAULT_IDENTIFIER);
    }

    @Override
    public Accession parse(Iterable<String> lines) throws ParseException {
        String valueString = getValueString(lines, " ");
        String[] values = valueString.split("\\s+");

        Builder builder = new Builder();
        builder.addAll(values);

        Accession accesstion = builder.build();
        return accesstion;
    }
}