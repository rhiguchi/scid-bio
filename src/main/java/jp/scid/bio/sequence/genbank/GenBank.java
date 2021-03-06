package jp.scid.bio.sequence.genbank;

import jp.scid.bio.sequence.SequenceBioData;

public class GenBank implements SequenceBioData {
    public final static GenBank EMPTY = new GenBank(new Builder());

    private final Locus locus;
    private final Definition definition;
    private final Accession accession;
    private final Version version;
    private final Keywords keywords;
    private final Source source;
    private final Reference reference;
    private final Comment comment;
    private final Features features;
    private final Origin origin;

    GenBank(Builder builder) {
        this.locus = builder.locus;
        this.definition = builder.definition;
        this.accession = builder.accession;
        this.version = builder.version;
        this.keywords = builder.keywords;
        this.source = builder.source;
        this.reference = builder.reference;
        this.comment = builder.comment;
        this.features = builder.features;
        this.origin = builder.origin;
    }

    public Locus locus() {
        return locus;
    }
    
    /**
     * @see Locus#topology()
     */
    @Override
    public String namespace() {
        return locus.division();
    }
    
    public Definition definition() {
        return definition;
    }

    /**
     * @see Definition#value(String)
     */
    @Override
    public String description() {
        return definition.value(" ");
    }
    
    public Accession accession() {
        return accession;
    }
    
    /**
     * @see Accession#primary()
     */
    @Override
    public String accessionNumber() {
        return accession.primary();
    }

    public Version version() {
        return version;
    }
    
    /**
     * @see Version#number()
     */
    @Override
    public int accessionVersion() {
        return version.number();
    }
    

    public Keywords keywords() {
        return keywords;
    }

    public Source source() {
        return source;
    }

    public Reference reference() {
        return reference;
    }

    public Comment comment() {
        return comment;
    }

    public Features features() {
        return features;
    }

    public Origin origin() {
        return origin;
    }

    @Override
    public String name() {
        return locus.name();
    }
    
    @Override
    public String sequence() {
        return origin.sequence();
    }
    
    @Override
    public int sequenceLength() {
        return locus.sequenceLength();
    }

    public static class Builder {
        protected Locus locus = Locus.EMPTY;
        protected Definition definition = null;
        protected Accession accession = null;
        protected Version version = null;
        protected Keywords keywords = null;
        protected Source source = null;
        protected Reference reference = null;
        protected Comment comment = null;
        protected Features features = null;
        protected Origin origin = null;

        public GenBank build() {
            return new GenBank(this);
        }
        
        public void locus(Locus locus) {
            if (locus == null) throw new IllegalArgumentException("locus must not be null");
            
            this.locus = locus;
        }
        
        public void definition(Definition definition) {
            this.definition = definition;
        }

        public void accession(Accession accession) {
            this.accession = accession;
        }
        
        public void version(Version version) {
            this.version = version;
        }
        
        public void keywords(Keywords keywords) {
            this.keywords = keywords;
        }
        
        public void source(Source source) {
            this.source = source;
        }
        
        public void reference(Reference reference) {
            this.reference = reference;
        }
        
        public void comment(Comment comment) {
            this.comment = comment;
        }
        
        public void features(Features features) {
            this.features = features;
        }
        
        public void origin(Origin origin) {
            this.origin = origin;
        }
    }
}
