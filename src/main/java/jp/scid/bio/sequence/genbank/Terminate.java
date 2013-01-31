package jp.scid.bio.sequence.genbank;


import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class Terminate extends AbstractGenBankAttribute {
    private final static Terminate INSTANCE = new Terminate();
    
    private Terminate() {
    }
    
    public static Terminate getInstarnce() {
        return INSTANCE;
    }
    
    @Override
    void setMeToBuilder(Builder builder) {
        // nothing to do
    }
}
