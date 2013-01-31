package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jp.scid.bio.GenBankAttribute;

public class Accession implements GenBankAttribute {
    public final static Accession EMPTY = new Accession("", Collections.<String>emptyList());

    private final String primary;
    private final List<String> secondary;

    Accession(String primary, List<String> secondary) {
        this.primary = primary;

        if (secondary.isEmpty()) {
            this.secondary = Collections.emptyList();
        }
        else {
            this.secondary = Collections.unmodifiableList(new ArrayList<String>(secondary));
        }
    }

    public String primary() {
        return primary;
    }

    public List<String> secondary() {
        return secondary;
    }

    public static class Builder {
        List<String> valueList = new LinkedList<String>();

        public void add(String accession) {
            valueList.add(accession);
        }

        public void addAll(List<String> accessions) {
            valueList.addAll(accessions);
        }

        public void addAll(String... accessions) {
            addAll(Arrays.asList(accessions));
        }

        public Accession build() {
            final String primary = valueList.size() == 0 ? "" : valueList.get(0);
            final List<String> secondary;

            if (valueList.size() <= 1) {
                secondary = Collections.emptyList();
            }
            else {
                secondary = valueList.subList(1, valueList.size());
            }

            Accession accesstion = new Accession(primary, secondary);
            return accesstion;
        }
    }

    public static class Format extends AbstractAttributeFormat {
        private final static String DEFAULT_IDENTIFIER = "ACCESSION";

        public Format() {
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
}
