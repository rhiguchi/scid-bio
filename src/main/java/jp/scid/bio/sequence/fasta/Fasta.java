package jp.scid.bio.sequence.fasta;

import jp.scid.bio.sequence.SequenceBioData;

/**
 * FASTA 形式ファイルに含まれている情報です
 * 
 * @author HIGUCHI Ryusuke
 */
public class Fasta implements SequenceBioData {
    private final String identifier;
    private final String namespace;
    private final String accession;
    private final int version;
    private final String name;
    private final String description;
    private final String sequence;
    
    Fasta(Builder builder) {
        this.identifier = builder.identifier;
        this.namespace = builder.namespace;
        this.accession = builder.accession;
        this.version = builder.version;
        this.name = builder.name;
        this.description = builder.description;
        this.sequence = builder.sequence();
    }
    
    public String identifier() {
        return identifier;
    }
    
    @Override
    public String namespace() {
        return namespace;
    }
    
    @Override
    public String accessionNumber() {
        return accession;
    }
    
    @Override
    public int accessionVersion() {
        return version;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    public String description() {
        return description;
    }
    
    @Override
    public String sequence() {
        return sequence;
    }
    
    @Override
    public int sequenceLength() {
        return sequence.length();
    }
    
    /**
     * Fasta オブジェクトの作成用クラスです。
     * 
     * @author HIGUCHI Ryusuke
     */
    public static class Builder {
        String identifier = "";
        String namespace = "";
        String accession = "";
        int version = 0;
        String name = "";
        String description = "";
        StringBuilder sequence = new StringBuilder();

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
            this.sequence = new StringBuilder(sequence);
        }

        public void appendSequence(String line) {
            sequence.append(line);
        }
        
        String sequence() {
            return this.sequence.toString();
        }
    }
}
