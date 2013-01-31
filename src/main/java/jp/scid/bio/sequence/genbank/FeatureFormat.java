package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jp.scid.bio.sequence.genbank.FeatureQualifier.Format;


public class FeatureFormat {
    private int keyDigits = 21;
    private int keyIndent = 5;
    
    protected FeatureLocationFormat locationFormat;
    protected FeatureQualifier.Format qualifierFormat;
    
    public FeatureFormat(FeatureLocationFormat locationFormat, FeatureQualifier.Format qualifierFormat) {
        this.locationFormat = locationFormat;
        this.qualifierFormat = qualifierFormat;
    }
    
    public FeatureFormat() {
        this(new FeatureLocationFormat(), new FeatureQualifier.Format());
    }
    
    public int getKeyIndent() {
        return keyIndent;
    }
    
    public int getKeyDigits() {
        return keyDigits;
    }

    String collectQualifireLines(List<String> accume, Iterator<String> source) {
        while (source.hasNext()) {
            String line = source.next();
            
            if (isQualifierStart(line)) {
                return line;
            }
            
            accume.add(line);
        }
        
        return null;
    }
    
    public Feature parse(List<String> lines) throws ParseException {
        String head = lines.get(0);

        String key = parseKey(head);

        final Iterator<String> linesIte = lines.iterator();
        List<String> qlines = new ArrayList<String>();
        
        // location
        String last = collectQualifireLines(qlines, linesIte);
        
        if (qlines.isEmpty())
            throw new IllegalArgumentException("location line is not found");
        
        final FeatureLocation location = parseLocation(qlines);
        
        final List<FeatureQualifier> qualifiers = new LinkedList<FeatureQualifier>();
        
        // qualifiers
        while (linesIte.hasNext() || last != null) {
            qlines.clear();
            qlines.add(last);
            
            last = collectQualifireLines(qlines, linesIte);
            FeatureQualifier qualifier = parseQualifier(qlines);
            qualifiers.add(qualifier);
        }
        
        // build
        Feature feature = new Feature(key, location, Collections.unmodifiableList(qualifiers));
        
        return feature;
    }
    
    protected boolean isQualifierStart(String line) {
        return line.length() >= keyDigits && line.charAt(keyDigits) == '/';
    }

    protected String parseKey(String line) throws ParseException {
        int keyEnd = line.indexOf(' ', keyIndent);

        if (keyEnd < 0) throw new ParseException("missing feature key" + line, 0);

        String key = line.substring(keyIndent, keyEnd);

        if (key.isEmpty()) throw new ParseException("missing feature key" + line, 0);

        return key;
    }
    
    protected FeatureLocation parseLocation(List<String> lines) throws ParseException {
        StringBuilder locationString = new StringBuilder();
        
        for (String line: lines) {
            String s = line.substring(keyDigits);
            locationString.append(s);
        }
        
        return locationFormat.parse(locationString.toString());
    }
    
    protected FeatureQualifier parseQualifier(List<String> lines) throws ParseException {
        List<String> qlines = new ArrayList<String>(lines.size());
        
        for (String line: lines) {
            String s = line.substring(keyDigits);
            qlines.add(s);
        }
        
        return qualifierFormat.parse(qlines);
    }
}
