package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VersionFormat extends AbstractAttributeFormat {
    private final static String DEFAULT_IDENTIFIER = "VERSION";

    public static final String DEFAULT_VERSION_FORMAT_PATTERN = "(?x)"
        + "([^.\\s]+)  (?: \\. (\\d+) )? \\s*" + // Accession.VersionNum
        "(?: GI: (\\S+) )?"; // Identifier

    public VersionFormat() {
        super(DEFAULT_IDENTIFIER);
    }

    protected String getVersionFormatPattern() {
        return DEFAULT_VERSION_FORMAT_PATTERN;
    }

    public Version parse(String line) throws ParseException {
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
    public Version parse(Iterable<String> lines) throws ParseException {
        String firstLine = ensureHeadLineExistence(lines.iterator());
        return parse(firstLine);
    }
}