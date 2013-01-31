package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.scid.bio.GenBankAttribute;


public class Locus implements GenBankAttribute {
    public final static Locus EMPTY = new Locus();

    final String name;
    final int sequenceLength;
    final String sequenceUnit;
    final String molculeType;
    final String topology;
    final String division;
    final Date date;

    Locus(Locus.Builder builder) {
        this.name = builder.name;
        this.sequenceLength = builder.sequenceLength;
        this.sequenceUnit = builder.sequenceUnit;
        this.molculeType = builder.molculeType;
        this.topology = builder.topology;
        this.division = builder.division;
        this.date = builder.date;
    }

    private Locus() {
        this(new Locus.Builder());
    }

    public String getName() {
        return name;
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

    public String getSequenceUnit() {
        return sequenceUnit;
    }

    public String getMolculeType() {
        return molculeType;
    }

    public String getTopology() {
        return topology;
    }

    public String getDivision() {
        return division;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        StringBuilder builder2 = new StringBuilder();
        builder2
                .append("Locus [name=").append(name).append(", sequenceLength=")
                .append(sequenceLength).append(", sequenceUnit=").append(sequenceUnit)
                .append(", molculeType=").append(molculeType).append(", topology=")
                .append(topology).append(", division=").append(division).append(", date=")
                .append(date).append("]");
        return builder2.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((division == null) ? 0 : division.hashCode());
        result = prime * result + ((molculeType == null) ? 0 : molculeType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + sequenceLength;
        result = prime * result + ((sequenceUnit == null) ? 0 : sequenceUnit.hashCode());
        result = prime * result + ((topology == null) ? 0 : topology.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Locus other = (Locus) obj;
        if (date == null) {
            if (other.date != null) return false;
        }
        else if (!date.equals(other.date)) return false;
        if (division == null) {
            if (other.division != null) return false;
        }
        else if (!division.equals(other.division)) return false;
        if (molculeType == null) {
            if (other.molculeType != null) return false;
        }
        else if (!molculeType.equals(other.molculeType)) return false;
        if (name == null) {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        if (sequenceLength != other.sequenceLength) return false;
        if (sequenceUnit == null) {
            if (other.sequenceUnit != null) return false;
        }
        else if (!sequenceUnit.equals(other.sequenceUnit)) return false;
        if (topology == null) {
            if (other.topology != null) return false;
        }
        else if (!topology.equals(other.topology)) return false;
        return true;
    }

    public static class Builder {
        String name = "";
        int sequenceLength = 0;
        String sequenceUnit = "";
        String molculeType = "";
        String topology = "";
        String division = "";
        Date date = null;

        public Locus build() {
            if (name == null) throw new IllegalArgumentException("name must not be null");

            if (sequenceUnit == null)
                throw new IllegalArgumentException("sequenceUnit must not be null");

            if (molculeType == null)
                throw new IllegalArgumentException("molculeType must not be null");

            if (topology == null) throw new IllegalArgumentException("topology must not be null");

            if (division == null) throw new IllegalArgumentException("division must not be null");

            return new Locus(this);
        }

        public void name(String name) {
            this.name = name;
        }

        public void sequenceLength(int sequenceLength) {
            this.sequenceLength = sequenceLength;
        }

        public void sequenceUnit(String sequenceUnit) {
            this.sequenceUnit = sequenceUnit;
        }

        public void molculeType(String molculeType) {
            this.molculeType = molculeType;
        }

        public void topology(String topology) {
            this.topology = topology;
        }

        public void division(String division) {
            this.division = division;
        }

        public void date(Date date) {
            this.date = date;
        }
    }

    public static class Format extends AbstractAttributeFormat {
        private final static Logger logger = Logger.getLogger(Format.class.getName());

        public static final String DEFAULT_LOCUS_FORMAT_PATTERN = "(?x)" + "(\\S+)\\s+" + // Locus
                                                                                          // Name
            "(?:(\\d+)\\s+(bp|aa)\\s{1,4})?" + // Sequence Length & Unit
            "(\\S+)?\\s+" + // Molecule Type
            "(circular|linear)?\\s*" + // Topology
            "(\\S+)?\\s*" + // Division
            "(\\S+)?"; // Date

        public Format() {
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
}