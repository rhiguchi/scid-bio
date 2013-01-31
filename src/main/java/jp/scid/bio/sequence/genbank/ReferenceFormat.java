package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.scid.bio.sequence.genbank.Reference.Builder;

public class ReferenceFormat extends AbstractAttributeFormat {
    public static final String DEFAULT_HEAD_FORMAT_PATTERN = "(?x)"
            + "(\\d+) (?: \\s* \\(bases \\s+ (\\d+) \\s+ to \\s+ (\\d+) \\) )?";

    public ReferenceFormat() {
        super("REFERENCE");
    }
    
    @Override
    public Reference parse(Iterable<String> lines) throws ParseException {
        Iterator<String> ite = lines.iterator();
        
        String line = ensureHeadLineExistence(ite);
        
        Builder builder = new Builder();
        
        // bases
        Matcher headMatcher = Pattern.compile(DEFAULT_HEAD_FORMAT_PATTERN).matcher(line);
        
        if (!headMatcher.find())
            throw new ParseException("invalid format pattern: " + line, 0);
        
        @SuppressWarnings("unused")
        String indexText = headMatcher.group(1);
        String startText = headMatcher.group(2);
        String endText = headMatcher.group(3);
        
        if (startText != null) {
            int basesStart = Integer.parseInt(startText);
            builder.setBasesStart(basesStart);
        }
        
        if (endText != null) {
            int basesEnd = Integer.parseInt(endText);
            builder.setBasesEnd(basesEnd);
        }
        
        // elements
        Map<String, String> subattributes = new HashMap<String, String>();
        List<String> subattributeLines = new ArrayList<String>();
        String subattributeKey = null;
        
        while (ite.hasNext()) {
            line = ite.next();
            
            if (isSubattributeLine(line) && subattributeKey != null) {
                String value = join(subattributeLines);
                subattributes.put(subattributeKey, value);
                
                subattributeKey = null;
                subattributeLines.clear();
            }
            
            if (subattributeKey == null) {
                subattributeKey = getSubattributeKey(line);
            }
            
            String subattributeValue = line.substring(identifierDigits);
            subattributeLines.add(subattributeValue);
        }
        
        if (subattributeKey != null) {
            String value = join(subattributeLines);
            subattributes.put(subattributeKey, value);
        }
        
        builder.setAuthors(getOrEmpty(subattributes, "AUTHORS"));  
        builder.setTitle(getOrEmpty(subattributes, "TITLE"));  
        builder.setJournal(getOrEmpty(subattributes, "JOURNAL"));  
        builder.setPubmed(getOrEmpty(subattributes, "PUBMED"));  
        builder.setRemark(getOrEmpty(subattributes, "REMARK"));  
        builder.setOthersMap(subattributes);
        
        return builder.build();
    }
    
    String getOrEmpty(Map<String, String> map, String key) {
        String value = map.remove(key);
        return value == null ? "" : value;
    }
    
    public boolean isSubattributeLine(String line) {
        return !line.substring(2, identifierDigits).trim().isEmpty();
    }
    
    public String getSubattributeKey(String line) {
        String key = line.substring(2, identifierDigits).trim();
        return key;
    }
    
    String join(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        
        for (String line: lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}