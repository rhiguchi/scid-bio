package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.scid.bio.GenBankAttribute;


public class Keywords implements GenBankAttribute {
    private final static Keywords SINGLETON = new Keywords(Collections.singletonList("."));
    
    private final List<String> valueList;

    Keywords(List<String> keywords) {
        if (keywords == null) throw new IllegalArgumentException("keywords must not be null");
        
        this.valueList = Collections.unmodifiableList(new ArrayList<String>(keywords));
    }
    
    public static Keywords getDefaultKeywords() {
        return SINGLETON;
    }
    
    public static Keywords from(String... keywords) {
        if (keywords == null) throw new IllegalArgumentException("keywords must not be null");
        
        if (keywords.length == 1 && ".".equals(keywords[0])) {
            return getDefaultKeywords();
        }
        
        List<String> keywordList = Arrays.asList(keywords);
        
        return new Keywords(keywordList);
    }

    public List<String> getValueList() {
        return valueList;
    }
    
    public static class Format extends AbstractAttributeFormat {
        public Format() {
            super("KEYWORDS");
        }
        
        @Override
        public Keywords parse(Iterable<String> lines) throws ParseException {
            String valueString = getValueString(lines, " ");
            String[] values = valueString.split("\\s+");
            
            return Keywords.from(values);
        }
    }
}
