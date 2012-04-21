package jp.scid.bio;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Version implements GenBankAttribute {
    private final String accession;
    private final int number;
    private final String identifier;

    public Version(String accession, int number, String identifier) {
        super();
        if (accession == null) throw new IllegalArgumentException("accession must not be null");
        if (identifier == null) throw new IllegalArgumentException("identifier must not be null");
        
        this.accession = accession;
        this.number = number;
        this.identifier = identifier;
    }

    public String getAccession() {
        return accession;
    }

    public int getNumber() {
        return number;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static class Format extends AbstractAttributeFormat {
        private final static String DEFAULT_IDENTIFIER = "VERSION";

        public static final String DEFAULT_VERSION_FORMAT_PATTERN = "(?x)"
            + "([^.\\s]+)  (?: \\. (\\d+) )? \\s*" + // Accession.VersionNum
            "(?: GI: (\\S+) )?"; // Identifier

        public Format() {
            super(DEFAULT_IDENTIFIER);
        }

        protected String getVersionFormatPattern() {
            return DEFAULT_VERSION_FORMAT_PATTERN;
        }

        public GenBankAttribute parse(String line) throws ParseException {
            String pattern = getVersionFormatPattern();
            String versionData = line.substring(getIdentifierDigits());

            Matcher m = Pattern.compile(pattern).matcher(versionData);

            if (!m.matches()) throw new ParseException(line, identifierDigits);

            // accession
            final String accession = m.group(1) == null ? "" : m.group(1);

            final int number = m.group(2) == null ? 0 : parseSequenceLength(m.group(2));

            String identifier = m.group(3) == null ? "" : m.group(3);

            Version version = new Version(accession, number, identifier);

            return version;
        }

        @Override
        public GenBankAttribute parse(Iterable<String> lines) throws ParseException {
            String firstLine = ensureHeadLineExistence(lines.iterator());
            return parse(firstLine);
        }
    }
}
