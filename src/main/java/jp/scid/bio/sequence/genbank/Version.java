package jp.scid.bio.sequence.genbank;


import jp.scid.bio.GenBank.Builder;

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
}
