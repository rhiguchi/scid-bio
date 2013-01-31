package jp.scid.bio.sequence.genbank;

import jp.scid.bio.GenBank;
import jp.scid.bio.GenBankAttribute;

abstract class AbstractGenBankAttribute implements GenBankAttribute {
    abstract void setMeToBuilder(GenBank.Builder builder);
}