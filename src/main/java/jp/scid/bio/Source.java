package jp.scid.bio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Source implements GenBankAttribute {
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
    
    public String getValue() {
        return value;
    }
    
    public String getOrganism() {
        return organism;
    }
    
    public List<String> getTaxonomy() {
        return taxonomy;
    }
    
    public static class Format extends AbstractAttributeFormat {
        public Format() {
            super("SOURCE");
        }
        
        @Override
        public Source parse(Iterable<String> lines) throws ParseException {
            Iterator<String> ite = lines.iterator();
            String line = ensureHeadLineExistence(ite);
            
            // source value
            StringBuilder sourceValue = new StringBuilder();
            sourceValue.append(getValueString(line));
            
            while (ite.hasNext() && !isOrganismLine(line = ite.next())) {
                sourceValue.append(" ").append(getValueString(line));
            }
            
            // organism
            StringBuilder organism = new StringBuilder();
            organism.append(getValueString(line));
            
            while (ite.hasNext() && !isTaxonomyLine(line = ite.next())) {
                organism.append(" ").append(getValueString(line));
            }
            
            // taxonomy
            StringBuilder taxonomyText = new StringBuilder();
            taxonomyText.append(getValueString(line));
            
            while (ite.hasNext()) {
                line = ite.next();
                taxonomyText.append(" ").append(getValueString(line));
            }
            
            if ('.' == taxonomyText.charAt(taxonomyText.length() - 1)) {
                taxonomyText.deleteCharAt(taxonomyText.length() - 1);
            }
            
            String[] taxonomy = taxonomyText.toString().split(";\\s+");
            
            Source source =
                new Source(sourceValue.toString(), organism.toString(), Arrays.asList(taxonomy));
            return source;
        }
        
        protected boolean isOrganismLine(String line) {
            return line.startsWith("  ORGANISM");
        }
        
        boolean isTaxonomyLine(String line) {
            return line.indexOf(';') > 0;
        }
    }
}
