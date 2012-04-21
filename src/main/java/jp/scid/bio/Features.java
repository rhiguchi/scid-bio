package jp.scid.bio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Features implements GenBankAttribute {
    private final List<Feature> features;

    public Features(Builder builder) {
        features = Collections.unmodifiableList(new ArrayList<Feature>(builder.features));
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public static class Format extends AbstractAttributeFormat {
        protected FeatureFormat featureFormat;

        public Format(FeatureFormat featureFormat) {
            super("FEATURES");
            this.featureFormat = featureFormat;
        }
        
        public Format() {
            this(new FeatureFormat());
        }

        public int getKeyIndent() {
            return featureFormat.getKeyIndent();
        }

        public int getKeyDigits() {
            return featureFormat.getKeyDigits();
        }
        
        public Features parse(Iterable<String> source) throws ParseException {
            Iterator<String> sourceIte = source.iterator();
            ensureHeadLineExistence(sourceIte);
            
            Features.Builder builder = new Features.Builder();

            final List<String> featureLines = new ArrayList<String>();

            while (sourceIte.hasNext()) {
                String line = sourceIte.next();
                
                if (!featureLines.isEmpty() && isFeatureHead(line)) {
                    Feature feature = featureFormat.parse(featureLines);
                    builder.addFeature(feature);

                    featureLines.clear();
                }

                featureLines.add(line);
            }
            
            if (!featureLines.isEmpty()) {
                Feature feature = featureFormat.parse(featureLines);
                builder.addFeature(feature);
            }

            return builder.build();
        }

        public boolean isFeatureHead(String line) {
            return line.length() >= getKeyDigits() && line.charAt(getKeyIndent()) != ' ';
        }

    }

    public static class Builder {
        private final List<Feature> features = new LinkedList<Feature>();

        public Features build() {
            return new Features(this);
        }

        public void addFeature(Feature feature) {
            features.add(feature);
        }
    }
}
