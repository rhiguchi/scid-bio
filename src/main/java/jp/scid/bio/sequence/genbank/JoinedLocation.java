package jp.scid.bio.sequence.genbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class JoinedLocation extends AbstractJoinedLocation {
    
    JoinedLocation(int min, int max, boolean complement, List<FeatureLocation> locations) {
        super(min, max, complement, locations);
    }

    public static class Builder extends AbstractJoinedLocation.Builder {
        @Override
        public void add(FeatureLocation location) {
            super.add(location);
        }
        
        @Override
        public void setComplement(boolean complement) {
            super.setComplement(complement);
        }
        
        @Override
        public JoinedLocation build() {
            return new JoinedLocation(getMin(), getMax(), complement, list);
        }
    }
}

abstract class AbstractJoinedLocation extends FeatureLocation {
    private final List<FeatureLocation> locations;
    
    AbstractJoinedLocation(int min, int max, boolean complement, List<FeatureLocation> locations) {
        super(min, max, complement);
        
        this.locations = Collections.unmodifiableList(new ArrayList<FeatureLocation>(locations));
    }

    public List<FeatureLocation> getLocations() {
        return locations;
    }
    
    abstract static class Builder {
        final List<FeatureLocation> list = new LinkedList<FeatureLocation>();
        boolean complement = false;

        public void add(FeatureLocation location) {
            list.add(location);
        }
        
        public void setComplement(boolean complement) {
            this.complement = complement;
        }
        
        int getMin() {
            return Collections.min(list, FeatureLocation.START_PRIORITIZED_COMPARATOR).getStart();
        }
        
        int getMax() {
            return Collections.max(list, FeatureLocation.END_PRIORITIZED_COMPARATOR).getEnd();
        }
        
        public abstract AbstractJoinedLocation build();
    }
}