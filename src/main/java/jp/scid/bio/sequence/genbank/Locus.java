package jp.scid.bio.sequence.genbank;

import java.util.Date;
import java.util.GregorianCalendar;


public class Locus extends GenBankAttribute {
    public final static Locus EMPTY = new Locus();

    private final String name;
    private final int sequenceLength;
    private final String sequenceUnit;
    private final String molculeType;
    private final String topology;
    private final String division;
    private final Date date;

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

    public String name() {
        return name;
    }

    public int sequenceLength() {
        return sequenceLength;
    }

    public String sequenceUnit() {
        return sequenceUnit;
    }

    public String molculeType() {
        return molculeType;
    }

    /**
     * ex. liner or circular
     * @return topology
     */
    public String topology() {
        return topology;
    }

    /**
     * ex. BCT or PRI
     * @return division
     */
    public String division() {
        return division;
    }

    public Date date() {
        return date;
    }

    @Override
    void setMeToBuilder(GenBank.Builder builder) {
        builder.locus(this);
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
            return new Locus(this);
        }

        public Builder name(String name) {
            if (name == null)
                throw new IllegalArgumentException("name must not be null");
            
            this.name = name;
            return this;
        }

        public Builder sequenceLength(int sequenceLength) {
            if (sequenceLength < 0)
                throw new IllegalArgumentException("sequenceLength must not be negative");
            
            this.sequenceLength = sequenceLength;
            return this;
        }

        public Builder sequenceUnit(String sequenceUnit) {
            if (sequenceUnit == null)
                throw new IllegalArgumentException("sequenceUnit must not be null");
            
            this.sequenceUnit = sequenceUnit;
            return this;
        }

        public Builder molculeType(String molculeType) {
            if (molculeType == null)
                throw new IllegalArgumentException("molculeType must not be null");
            
            this.molculeType = molculeType;
            return this;
        }

        public Builder topology(String topology) {
            if (topology == null) throw new IllegalArgumentException("topology must not be null");
            
            this.topology = topology;
            return this;
        }

        public Builder division(String division) {
            if (division == null) throw new IllegalArgumentException("division must not be null");
            
            this.division = division;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }
        
        public Builder date(int year, int mon, int day) {
            this.date = new GregorianCalendar(year, mon - 1, day).getTime();
            return this;
        }
    }
}