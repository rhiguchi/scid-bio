package jp.scid.bio;

import static java.lang.String.*;

import java.util.Comparator;

public abstract class FeatureLocation implements Comparable<FeatureLocation> {
    public static final Comparator<FeatureLocation> START_PRIORITIZED_COMPARATOR = new Comparator<FeatureLocation>() {
        @Override
        public int compare(FeatureLocation o1, FeatureLocation o2) {
            if (o1.start == o2.start) {
                if (o1.end == o2.end) {
                    if (o1.complement == o2.complement) {
                        return 0;
                    }
                    else {
                        return o1.complement ? -1 : 1;
                    }
                }
                else {
                    return o1.end < o2.end ? -1 : 1;
                }
            }
            else {
                return o1.start < o2.start ? -1 : 1;
            }
        }
    };
    
    public static final Comparator<FeatureLocation> END_PRIORITIZED_COMPARATOR = new Comparator<FeatureLocation>() {
        @Override
        public int compare(FeatureLocation o1, FeatureLocation o2) {
            if (o1.end == o2.end) {
                if (o1.start == o2.start) {
                    if (o1.complement == o2.complement) {
                        return 0;
                    }
                    else {
                        return o1.complement ? -1 : 1;
                    }
                }
                else {
                    return o1.start < o2.start ? -1 : 1;
                }
            }
            else {
                return o1.end < o2.end ? -1 : 1;
            }
        }
    };
    
    protected final int start;
    protected final int end;
    protected final boolean complement;
    
    FeatureLocation(int start, int end, boolean complement) {
        if (end < start)
            throw new IllegalArgumentException(format(
                    "start %d must be less than or equal end %d", start, end));
        
        this.start = start;
        this.end = end;
        this.complement = complement;
    }
    
    public int getStart() {
        return start;
    }
    
    public int getEnd() {
        return end;
    }
    
    public boolean isComplement() {
        return complement;
    }
    
    @Override
    public int compareTo(FeatureLocation o) {
        if (this.start == o.start) {
            if (this.end == o.end) {
                if (this.complement == o.complement) {
                    return 0;
                }
                else {
                    return this.complement ? -1 : 1;
                }
            }
            else {
                return this.end < o.end ? -1 : 1;
            }
        }
        else {
            return this.start < o.start ? -1 : 1;
        }
    }
}
