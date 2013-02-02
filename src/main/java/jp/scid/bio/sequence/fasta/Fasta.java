package jp.scid.bio.sequence.fasta;

import jp.scid.bio.sequence.SequenceBioData;

public class Fasta implements SequenceBioData {
    private final String identifier;
    private final String namespace;
    private final String accession;
    private final int version;
    private final String name;
    private final String description;
    private final String sequence;
    
    public Fasta(Builder builder) {
        this.identifier = builder.identifier;
        this.namespace = builder.namespace;
        this.accession = builder.accession;
        this.version = builder.version;
        this.name = builder.name;
        this.description = builder.description;
        this.sequence = builder.sequence;
    }
    
    public String identifier() {
        return identifier;
    }
    
    public String namespace() {
        return namespace;
    }
    
    public String accession() {
        return accession;
    }
    
    public int version() {
        return version;
    }
    
    public String name() {
        return name;
    }
    
    public String description() {
        return description;
    }
    
    public String sequence() {
        return sequence;
    }
    
    public String getSequence() {
        return sequence;
    }
    
    public static class Builder {
        String identifier = "";
        String namespace = "";
        String accession = "";
        int version = 0;
        String name = "";
        String description = "";
        String sequence = "";

        public Fasta build() {
            return new Fasta(this);
        }
        
        public void identifier(String identifier) {
            this.identifier = identifier;
        }
        
        public void namespace(String namespace) {
            this.namespace = namespace;
        }
        
        public void accession(String accession) {
            this.accession = accession;
        }
        
        public void version(int version) {
            this.version = version;
        }
        
        public void name(String name) {
            this.name = name;
        }
        
        public void description(String description) {
            this.description = description;
        }
        
        public void sequence(String sequence) {
            this.sequence = sequence;
        }
    }
}
