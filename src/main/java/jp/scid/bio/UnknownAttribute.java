package jp.scid.bio;

import java.text.ParseException;

import jp.scid.bio.sequence.genbank.GenBankAttribute;

public class UnknownAttribute implements GenBankAttribute {
    public static UnknownAttribute INSTANCE = new UnknownAttribute();
    
    public static class Format implements GenBankAttribute.Format {
        
        /**
         * @return always {@code true}
         */
        @Override
        public boolean isHeadLine(String line) {
            return true;
        }
        
        /**
         * @return always {@link UnknownAttribute#getInstance()}
         */
        @Override
        public UnknownAttribute parse(Iterable<String> line) throws ParseException {
            return getInstance();
        }
    }

    private UnknownAttribute() {
    }
    
    public static UnknownAttribute getInstance() {
        return INSTANCE;
    }
}
