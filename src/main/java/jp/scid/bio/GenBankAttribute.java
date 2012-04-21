package jp.scid.bio;

import java.text.ParseException;

public interface GenBankAttribute extends BioDataAttribute {
    
    public static interface Format {
        boolean isHeadLine(String line);
        
        GenBankAttribute parse(Iterable<String> lines) throws ParseException;
    }
}