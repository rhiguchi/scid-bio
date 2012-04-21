package jp.scid.bio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reference implements GenBankAttribute {
    private final int basesStart;
    private final int basesEnd;
    private final String authors;
    private final String title;
    private final String journal;
    private final String pubmed;
    private final String remark;
    private final Map<String, String> others;
    
    public Reference(Builder builder) {
        this.basesStart = builder.basesStart;
        this.basesEnd = builder.basesEnd;
        this.authors = builder.authors;
        this.title = builder.title;
        this.journal = builder.journal;
        this.pubmed = builder.pubmed;
        this.remark = builder.remark;
        this.others = new HashMap<String, String>(builder.others);
    }
    
    public int getBasesStart() {
        return basesStart;
    }

    public int getBasesEnd() {
        return basesEnd;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }
    
    public String getJournal() {
        return journal;
    }

    public String getPubmed() {
        return pubmed;
    }

    public String getRemark() {
        return remark;
    }
    
    public String getOthers(String key) {
        return others.get(key);
    }

    public static class Builder {
        int basesStart = 0;
        int basesEnd = 0;
        String authors = "";
        String title = "";
        String journal = "";
        String pubmed = "";
        String remark = "";
        Map<String, String> others;
        
        public Builder() {
            others = new HashMap<String, String>();
        }
        
        public Reference build() {
            return new Reference(this);
        }
        
        // basesStart
        public int getBasesStart() {
            return basesStart;
        }
        
        public void setBasesStart(int basesStart) {
            this.basesStart = basesStart;
        }
        
        // basesEnd
        public int getBasesEnd() {
            return basesEnd;
        }
        
        public void setBasesEnd(int basesEnd) {
            this.basesEnd = basesEnd;
        }
        
        // authors
        public String getAuthors() {
            return authors;
        }
        
        public void setAuthors(String authors) {
            if (authors == null) throw new IllegalArgumentException("authors must not be null");
            this.authors = authors;
        }
        
        // title
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            if (title == null) throw new IllegalArgumentException("title must not be null");
            this.title = title;
        }
        
        // journal
        public String getJournal() {
            return journal;
        }
        
        public void setJournal(String journal) {
            if (journal == null) throw new IllegalArgumentException("journal must not be null");
            this.journal = journal;
        }
        
        // pubmed
        public String getPubmed() {
            return pubmed;
        }
        
        public void setPubmed(String pubmed) {
            if (pubmed == null) throw new IllegalArgumentException("pubmed must not be null");
            this.pubmed = pubmed;
        }
        
        // remark
        public String getRemark() {
            return remark;
        }
        
        public void setRemark(String remark) {
            if (remark == null) throw new IllegalArgumentException("remark must not be null");
            this.remark = remark;
        }
        
        public void setOthersMap(Map<String, String> map) {
            others.clear();
            others.putAll(map);
        }
        
        // others
        public void putOthers(String key, String value) {
            if (value == null) {
                others.remove(key);
            }
            else {
                others.put(key, value);
            }
        }
    }
    public static class Format extends AbstractAttributeFormat {
        public static final String DEFAULT_HEAD_FORMAT_PATTERN = "(?x)"
                + "(\\d+) (?: \\s* \\(bases \\s+ (\\d+) \\s+ to \\s+ (\\d+) \\) )?";

        public Format() {
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
}
