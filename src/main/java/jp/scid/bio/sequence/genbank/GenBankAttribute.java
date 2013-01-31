package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

import jp.scid.bio.BioDataAttribute;

public interface GenBankAttribute extends BioDataAttribute {
    
    public static interface Format {
        boolean isHeadLine(String line);
        
        GenBankAttribute parse(Iterable<String> lines) throws ParseException;
    }
}