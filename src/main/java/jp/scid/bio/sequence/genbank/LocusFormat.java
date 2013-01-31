package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.scid.bio.GenBankAttribute;
import jp.scid.bio.sequence.genbank.Locus.Builder;

/**
 * The format class for {@link Locus}.
 * 
 * @author HIGUCHI Ryusuke
 *
 */
public class LocusFormat extends AbstractAttributeFormat {
    private final static Logger logger = Logger.getLogger(LocusFormat.class.getName());

    public static final String DEFAULT_LOCUS_FORMAT_PATTERN = "(?x)" + "(\\S+)\\s+" + // Locus
                                                                                      // Name
        "(?:(\\d+)\\s+(bp|aa)\\s{1,4})?" + // Sequence Length & Unit
        "(\\S+)?\\s+" + // Molecule Type
        "(circular|linear)?\\s*" + // Topology
        "(\\S+)?\\s*" + // Division
        "(\\S+)?"; // Date

    public LocusFormat() {
        super("LOCUS");
    }

    protected String getLocusFormatPattern() {
        return DEFAULT_LOCUS_FORMAT_PATTERN;
    }

    public Locus parse(String line) throws ParseException {
        String pattern = getLocusFormatPattern();
        String locusData = getValueString(line);

        Matcher m = Pattern.compile(pattern).matcher(locusData);

        if (!m.matches()) throw new ParseException("need a name: " + line, identifierDigits);

        Builder builder = new Builder();

        // name
        String name = m.group(1);
        builder.name(name);

        // sequenceLength
        int sequenceLength = 0;
        String seqLengthText = m.group(2);
        if (seqLengthText != null) {
            try {
                sequenceLength = parseSequenceLength(seqLengthText);
            }
            catch (ParseException e) {
                logger.log(Level.INFO, "Cannot parse sequence length from '{0}'", line);
            }
        }
        builder.sequenceLength(sequenceLength);

        // seqUnit
        String seqUnit = m.group(3);
        builder.sequenceUnit(seqUnit);

        // molType
        String molType = m.group(4);
        builder.molculeType(molType);

        // topology
        String topology = m.group(5);
        builder.topology(topology);

        // division
        String division = m.group(6);
        builder.division(division);

        // date
        String dateText = m.group(7);
        if (dateText != null) {
            try {
                Date date = parseDate(dateText);
                builder.date(date);
            }
            catch (ParseException e) {
                logger.log(Level.INFO, "Cannot parse date from '{0}'", line);
            }
        }

        Locus locus = builder.build();
        return locus;
    }

    @Override
    public GenBankAttribute parse(Iterable<String> lines) throws ParseException {
        String firstLine = ensureHeadLineExistence(lines.iterator());
        return parse(firstLine);
    }

    Date parseDate(String dateText) throws ParseException {
        return new SimpleDateFormat("dd-MMM-yyyy", java.util.Locale.US).parse(dateText);
    }
}