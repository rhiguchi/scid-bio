package jp.scid.bio.sequence.genbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class JoinedLocation extends FeatureLocation {
    private final List<FeatureLocation> locations;
    
    JoinedLocation(int min, int max, boolean complement, List<FeatureLocation> locations) {
        super(min, max, complement);
        
        this.locations = Collections.unmodifiableList(new ArrayList<FeatureLocation>(locations));
    }
    
    public List<FeatureLocation> getLocations() {
        return locations;
    }
    
    public static class Builder {
        private final List<FeatureLocation> list = new LinkedList<FeatureLocation>();
        private boolean complement = false;
        
        public void add(FeatureLocation location) {
            list.add(location);
        }
        
        public void setComplement(boolean complement) {
            this.complement = complement;
        }
        
        public JoinedLocation build() {
            int min = Collections.min(list, FeatureLocation.START_PRIORITIZED_COMPARATOR).getStart();
            int max = Collections.max(list, FeatureLocation.END_PRIORITIZED_COMPARATOR).getEnd();
            
            return new JoinedLocation(min, max, complement, list);
        }
    }
}
