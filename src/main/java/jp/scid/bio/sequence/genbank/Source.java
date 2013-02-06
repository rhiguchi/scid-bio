package jp.scid.bio.sequence.genbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.scid.bio.sequence.genbank.GenBank.Builder;

public class Source extends GenBankAttribute {
    private final String value;
    private final String organism;
    private final List<String> taxonomy;
    
    public Source(String value, String organism, List<String> taxonomy) {
        this.value = value;
        this.organism = organism;
        

        if (taxonomy.isEmpty()) {
            this.taxonomy = Collections.emptyList();
        }
        else {
            this.taxonomy = Collections.unmodifiableList(new ArrayList<String>(taxonomy));
        }
    }
    
    public String value() {
        return value;
    }
    
    public String organism() {
        return organism;
    }
    
    public List<String> taxonomy() {
        return taxonomy;
    }
    
    @Override
    void setMeToBuilder(Builder builder) {
        builder.source(this);
    }
}
