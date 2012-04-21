package jp.scid.bio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Feature {
    private final String key;
    private final FeatureLocation location;
    private final List<FeatureQualifier> qualifiers;

    Feature(String key, FeatureLocation location, List<FeatureQualifier> qualifiers) {
        this.key = key;
        this.location = location;
        this.qualifiers = qualifiers;
    }

    public static Feature newFeature(String key, FeatureLocation location) {
        return new Feature(key, location, Collections.<FeatureQualifier>emptyList());
    }
    
    public static Feature newFeature(
            String key, FeatureLocation location, List<FeatureQualifier> qualifiers) {
        List<FeatureQualifier> fixedQualifiers =
                Collections.unmodifiableList(new ArrayList<FeatureQualifier>(qualifiers));
        return new Feature(key, location, fixedQualifiers);
    }
    
    public String getKey() {
        return key;
    }

    public FeatureLocation getLocation() {
        return location;
    }

    public List<FeatureQualifier> getQualifiers() {
        return qualifiers;
    }
}