package jp.scid.bio.sequence.genbank;


import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class Version extends AbstractGenBankAttribute {
    private final String accession;
    private final int number;
    private final String identifier;

    Version(String accession, int number, String identifier) {
        super();
        if (accession == null) throw new IllegalArgumentException("accession must not be null");
        if (identifier == null) throw new IllegalArgumentException("identifier must not be null");
        
        this.accession = accession;
        this.number = number;
        this.identifier = identifier;
    }

    public static Version newVersion(String accession, int number, String identifier) {
        return new Version(accession, number, identifier);
    }
    
    public String accession() {
        return accession;
    }

    public int number() {
        return number;
    }

    public String identifier() {
        return identifier;
    }
    
    @Override
    void setMeToBuilder(Builder builder) {
        builder.version(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accession == null) ? 0 : accession.hashCode());
        result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Version other = (Version) obj;
        if (accession == null) {
            if (other.accession != null) return false;
        }
        else if (!accession.equals(other.accession)) return false;
        if (identifier == null) {
            if (other.identifier != null) return false;
        }
        else if (!identifier.equals(other.identifier)) return false;
        if (number != other.number) return false;
        return true;
    }
}
