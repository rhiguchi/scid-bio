package jp.scid.bio.sequence.genbank;


abstract class AbstractGenBankAttribute implements GenBankAttribute {
    abstract void setMeToBuilder(GenBank.Builder builder);
}