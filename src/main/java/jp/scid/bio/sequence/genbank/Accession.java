package jp.scid.bio.sequence.genbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Accession extends AbstractGenBankAttribute {
    public final static Accession EMPTY = new Accession("", Collections.<String>emptyList());

    private final String primary;
    private final List<String> secondary;

    Accession(String primary, List<String> secondary) {
        if (primary == null) throw new IllegalArgumentException("primary must not be null");
        
        this.primary = primary;

        if (secondary == null || secondary.isEmpty()) {
            this.secondary = Collections.emptyList();
        }
        else {
            this.secondary = Collections.unmodifiableList(new ArrayList<String>(secondary));
        }
    }

    public static Accession newAccession(String primary, String... secondary) {
        Builder builder = new Accession.Builder();
        builder.append(primary).append(secondary);
        return builder.build();
    }
    
    public String primary() {
        return primary;
    }

    public List<String> secondary() {
        return secondary;
    }
    
    @Override
    void setMeToBuilder(jp.scid.bio.sequence.genbank.GenBank.Builder builder) {
        builder.accession(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((primary == null) ? 0 : primary.hashCode());
        result = prime * result + ((secondary == null) ? 0 : secondary.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Accession other = (Accession) obj;
        if (primary == null) {
            if (other.primary != null) return false;
        }
        else if (!primary.equals(other.primary)) return false;
        if (secondary == null) {
            if (other.secondary != null) return false;
        }
        else if (!secondary.equals(other.secondary)) return false;
        return true;
    }

    public static class Builder {
        List<String> accessions = new LinkedList<String>();

        public Builder append(String accession) {
            if (accession == null)
                throw new IllegalArgumentException("primary accession must not be null");
            accessions.add(accession);
            
            return this;
        }

        public Builder append(List<String> accessions) {
            accessions.addAll(accessions);
            
            return this;
        }

        public Builder append(String... accessions) {
            Collections.addAll(this.accessions, accessions);
            
            return this;
        }

        public Accession build() {
            final String primary = accessions.isEmpty() ? "" : accessions.get(0);
            final List<String> secondary;

            if (accessions.size() <= 1) {
                secondary = Collections.emptyList();
            }
            else {
                secondary = accessions.subList(1, accessions.size());
            }

            Accession accesstion = new Accession(primary, secondary);
            return accesstion;
        }
    }
}
