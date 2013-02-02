package jp.scid.bio.sequence.genbank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class Keywords extends GenBankAttribute {
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
    
    @Override
    void setMeToBuilder(Builder builder) {
        builder.keywords(this);
    }
}
