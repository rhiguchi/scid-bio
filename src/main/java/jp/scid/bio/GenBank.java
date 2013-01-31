package jp.scid.bio;

import jp.scid.bio.sequence.genbank.Accession;
import jp.scid.bio.sequence.genbank.Comment;
import jp.scid.bio.sequence.genbank.Definition;
import jp.scid.bio.sequence.genbank.Features;
import jp.scid.bio.sequence.genbank.Keywords;
import jp.scid.bio.sequence.genbank.Locus;
import jp.scid.bio.sequence.genbank.Origin;
import jp.scid.bio.sequence.genbank.Reference;
import jp.scid.bio.sequence.genbank.Source;
import jp.scid.bio.sequence.genbank.Version;

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
    
    public Definition definition() {
        return definition;
    }

    public Accession accession() {
        return accession;
    }

    public Version version() {
        return version;
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
    public String getSequence() {
        return origin.sequence();
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

        @Deprecated
        public Builder setAttribute(GenBankAttribute attribute) {
            if (attribute instanceof Locus) {
                locus((Locus) attribute);
            }
            else if (attribute instanceof Definition) {
                definition((Definition) attribute);
            }
            else if (attribute instanceof Accession) {
                accession((Accession) attribute);
            }
            else if (attribute instanceof Version) {
                version((Version) attribute);
            }
            else if (attribute instanceof Keywords) {
                keywords((Keywords) attribute);
            }
            else if (attribute instanceof Source) {
                source((Source) attribute);
            }
            else if (attribute instanceof Reference) {
                reference((Reference) attribute);
            }
            else if (attribute instanceof Comment) {
                comment((Comment) attribute);
            }
            else if (attribute instanceof Features) {
                features((Features) attribute);
            }
            else if (attribute instanceof Origin) {
                origin((Origin) attribute);
            }
            
            return this;
        }

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
