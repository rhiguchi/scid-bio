package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

import jp.scid.bio.sequence.genbank.GenBank.Builder;


public class UnknownAttribute extends AbstractGenBankAttribute {
    public static UnknownAttribute INSTANCE = new UnknownAttribute();
    
    public static class Format extends AbstractAttributeFormat {
        public Format() {
            super("?");
        }
        
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

    /**
     * do nothing
     */
    @Override
    void setMeToBuilder(Builder builder) {
    }
}
