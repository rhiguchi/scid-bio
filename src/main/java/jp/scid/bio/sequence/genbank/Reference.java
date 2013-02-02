package jp.scid.bio.sequence.genbank;

import java.util.HashMap;
import java.util.Map;

public class Reference extends GenBankAttribute {
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
    
    public int basesStart() {
        return basesStart;
    }

    public int basesEnd() {
        return basesEnd;
    }

    public String authors() {
        return authors;
    }

    public String title() {
        return title;
    }
    
    public String journal() {
        return journal;
    }

    public String pubmed() {
        return pubmed;
    }

    public String remark() {
        return remark;
    }
    
    public String others(String key) {
        return others.get(key);
    }
    
    @Override
    void setMeToBuilder(jp.scid.bio.sequence.genbank.GenBank.Builder builder) {
        builder.reference(this);
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
}
