package jp.scid.bio.sequence.genbank;

import java.text.ParseException;

public class KeywordsFormat extends AbstractAttributeFormat {
    public KeywordsFormat() {
        super("KEYWORDS");
    }
    
    @Override
    public Keywords parse(Iterable<String> lines) throws ParseException {
        String valueString = getValueString(lines, " ");
        String[] values = valueString.split("\\s+");
        
        return Keywords.from(values);
    }
}