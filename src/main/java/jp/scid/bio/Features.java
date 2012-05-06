package jp.scid.bio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class Features implements GenBankAttribute, List<Feature>, RandomAccess {
    private final List<Feature> features;

    public Features(Builder builder) {
        features = Collections.unmodifiableList(new ArrayList<Feature>(builder.features));
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public int size() {
        return features.size();
    }

    public boolean isEmpty() {
        return features.isEmpty();
    }

    public boolean contains(Object o) {
        return features.contains(o);
    }

    public Iterator<Feature> iterator() {
        return features.iterator();
    }

    public Object[] toArray() {
        return features.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return features.toArray(a);
    }

    public boolean add(Feature e) {
        throw new UnsupportedOperationException("add");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    public boolean containsAll(Collection<?> c) {
        return features.containsAll(c);
    }

    public boolean addAll(Collection<? extends Feature> c) {
        throw new UnsupportedOperationException("addAll");
    }

    public boolean addAll(int index, Collection<? extends Feature> c) {
        throw new UnsupportedOperationException("addAll");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    public boolean equals(Object o) {
        return features.equals(o);
    }

    public int hashCode() {
        return features.hashCode();
    }

    public Feature get(int index) {
        return features.get(index);
    }

    public Feature set(int index, Feature element) {
        throw new UnsupportedOperationException("set");
    }

    public void add(int index, Feature element) {
        throw new UnsupportedOperationException("add");
    }

    public Feature remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    public int indexOf(Object o) {
        return features.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return features.lastIndexOf(o);
    }

    public ListIterator<Feature> listIterator() {
        return features.listIterator();
    }

    public ListIterator<Feature> listIterator(int index) {
        return features.listIterator(index);
    }

    public List<Feature> subList(int fromIndex, int toIndex) {
        return features.subList(fromIndex, toIndex);
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
