package jp.scid.bio;

import java.text.ParseException;

public class Terminate implements GenBankAttribute {
    private final static Terminate INSTANCE = new Terminate();
    
    public static Terminate getInstarnce() {
        return INSTANCE;
    }
    
    public static class Format extends AbstractAttributeFormat {
        public Format() {
            super("//");
        }

        public boolean isTerminateLine(String line) {
            return line.startsWith("//");
        }
        
        @Override
        public boolean isHeadLine(String line) {
            return isTerminateLine(line);
        }
        
        public Terminate parse(String line) throws ParseException {
            if (!isTerminateLine(line))
                throw new ParseException("not termination", 0);
            
            return Terminate.getInstarnce();
        }
        
        @Override
        public Terminate parse(Iterable<String> lines) throws ParseException {
            String first = ensureHeadLineExistence(lines.iterator());
            return parse(first);
        }
    }
}
