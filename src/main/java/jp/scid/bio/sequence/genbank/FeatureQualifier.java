package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeatureQualifier {
    private final String key;
    private final List<String> valueLines;

    FeatureQualifier(String key, List<String> valueLines) {
        this.key = key;
        this.valueLines = Collections.unmodifiableList(valueLines);
    }

    public String getKey() {
        return key;
    }
    
    public List<String> getValueLines() {
        return valueLines;
    }
    
    public static class Format {
        public FeatureQualifier parse(List<String> lines)  throws ParseException {
            String head = lines.get(0);
            
            if (head.charAt(0) != '/') {
                throw new ParseException("invalid qualifier head" + lines.toString(), 0);
            }
            
            int equalIndex = head.indexOf('=', 2);
            
            final String key;
            final List<String> valueLines;
            
            if (equalIndex < 0) {
                key = head.substring(1);
                valueLines = Collections.emptyList();
            }
            else {
                key = head.substring(1, equalIndex);
                valueLines = new ArrayList<String>(lines.size());
                String valueHead = head.substring(key.length() + 1);
                valueLines.add(valueHead);
                
                valueLines.addAll(lines.subList(1, lines.size()));
            }
            
            FeatureQualifier q = new FeatureQualifier(key, valueLines);
            return q;
        }
    }
}