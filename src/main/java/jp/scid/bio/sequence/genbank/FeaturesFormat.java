package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FeaturesFormat extends AbstractAttributeFormat {
    protected FeatureFormat featureFormat;

    public FeaturesFormat(FeatureFormat featureFormat) {
        super("FEATURES");
        this.featureFormat = featureFormat;
    }
    
    public FeaturesFormat() {
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