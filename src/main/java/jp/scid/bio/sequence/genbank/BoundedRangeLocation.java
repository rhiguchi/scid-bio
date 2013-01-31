package jp.scid.bio.sequence.genbank;


public class BoundedRangeLocation extends RangeLocation {
    protected final boolean lesserStart;
    protected final boolean greaterEnd;
    
    BoundedRangeLocation(int start, int end, boolean complement, boolean lesserStart, boolean greaterEnd) {
        super(start, end, complement);
        
        this.lesserStart = lesserStart;
        this.greaterEnd = greaterEnd;
    }

    BoundedRangeLocation(Builder builder) {
        this(builder.start, builder.end, builder.complement,
                builder.lesserStart, builder.greaterEnd);
        
    }
    
    public boolean isLesserStart() {
        return lesserStart;
    }
    
    public boolean isGreaterEnd() {
        return greaterEnd;
    }
    
    @Override
    public String toString() {
        return start + ".." + end;
    }
    
    public static class Builder {
        protected int start = 0;
        protected int end = 0;
        protected boolean complement = false;
        protected boolean lesserStart = false;
        protected boolean greaterEnd = false;
        
        public RangeLocation build() {
            return new BoundedRangeLocation(this);
        }
        
        public void setStart(int start) {
            this.start = start;
        }
        
        public void setEnd(int end) {
            this.end = end;
        }
        
        public void setComplement(boolean complement) {
            this.complement = complement;
        }
        
        public void setLesserStart(boolean lesserStart) {
            this.lesserStart = lesserStart;
        }
        
        public void setGreaterEnd(boolean greaterEnd) {
            this.greaterEnd = greaterEnd;
        }
    }
}