package jp.scid.bio.sequence.genbank;

import java.util.List;

public class OrderedLocation extends AbstractJoinedLocation {
    OrderedLocation(int min, int max, boolean complement, List<FeatureLocation> locations) {
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
        public OrderedLocation build() {
            return new OrderedLocation(getMin(), getMax(), complement, list);
        }
    }
}
