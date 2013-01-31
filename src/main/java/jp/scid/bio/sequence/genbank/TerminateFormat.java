package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

public class TerminateFormat extends AbstractAttributeFormat {
    public TerminateFormat() {
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