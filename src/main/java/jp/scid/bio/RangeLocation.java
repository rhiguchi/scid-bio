package jp.scid.bio;

import static java.lang.String.*;

public abstract class RangeLocation extends FeatureLocation {
    
    RangeLocation(int start, int end, boolean complement) {
        super(start, end, complement);
    }

    public static RangeLocation.PointLocation newPointLocation(int start, int end, boolean complement) {
        if (end <= start) {
            throw new IllegalArgumentException(format(
                    "start must be lesser than end, but %d > %d", start, end));
        }
        
        return new PointLocation(start, end, complement);
    }
    
    public static RangeLocation.ExactPointLocation newExactPointLocation(int position, boolean complement) {
        if (position < 0) {
            throw new IllegalArgumentException(format(
                    "position must be greater than 0, but %d", position));
        }
        
        return new ExactPointLocation(position, complement);
    }
    
    public static RangeLocation.SiteLocation newSiteLocation(int start, boolean complement) {
        if (start < 0) {
            throw new IllegalArgumentException(format(
                    "start must be greater than 0, but %d", start));
        }
        return new SiteLocation(start, complement);
    }

    /**
     * 
     * ex. {@code 467}
     * @author ryusuke
     *
     */
    public static class PointLocation extends RangeLocation {
        public PointLocation(int start, int end, boolean complement) {
            super(start, end, complement);
        }
        
        @Override
        public String toString() {
            return start + "." + end;
        }
    }
    
    /**
     * 
     * ex. {@code 467}
     * @author ryusuke
     *
     */
    public static class ExactPointLocation extends RangeLocation {
        ExactPointLocation(int position, boolean complement) {
            super(position, position, complement);
        }
        
        @Override
        public String toString() {
            return String.valueOf(start);
        }
    }
    
    /**
     * 
     * ex. {@code 123^124}
     * @author ryusuke
     *
     */
    public static class SiteLocation extends RangeLocation {
        SiteLocation(int siteStart, boolean complement) {
            super(siteStart, siteStart + 1, complement);
        }
        
        @Override
        public String toString() {
            return start + "^" + end;
        }
    }
}
