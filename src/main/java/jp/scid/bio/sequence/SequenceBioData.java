package jp.scid.bio.sequence;

public interface SequenceBioData {
    String name();
    
    String sequence();
    
    int sequenceLength();
    
    String accessionNumber();
    
    int accessionVersion();
    
    String namespace();
    
    String description();
}
