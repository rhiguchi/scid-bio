package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.scid.bio.sequence.genbank.BoundedRangeLocation.Builder;

public class FeatureLocationFormat {
    private static final String COMPLEMENT_VALUE_PREFIX = "complement(";
    private static final String JOIN_VALUE_PREFIX = "join(";
    private static final String COMPOUND_VALUE_SUFFIX = ")";
    private final static String accessionPattern = "^\\w+(:?\\.\\d+)\\:";
    private final static Pattern numberMatchPattern = Pattern.compile("\\d+");
    
    public FeatureLocation parse(String text) throws ParseException {
        boolean complement = false;
        
        String value = null;
        
        if ((value = getComplementValue(text)) != null) {
            text = value;
            complement = true;
        }
        
        if ((value = getJoinValue(text)) != null) {
            String[] ranges = split(value);
            
            JoinedLocation.Builder builder = new JoinedLocation.Builder();
            builder.setComplement(complement);
            
            for (String rangeText: ranges) {
                FeatureLocation location = parse(rangeText);
                builder.add(location);
            }
            
            
            return builder.build();
        }
        else {
            FeatureLocation location = parseSpanLocation(text, complement);
            return location;
        }
    }
    
    protected String getComplementValue(String text) {
        if (text.startsWith(COMPLEMENT_VALUE_PREFIX) && text.endsWith(COMPOUND_VALUE_SUFFIX)) {
            return text.substring(COMPLEMENT_VALUE_PREFIX.length(), text.length() - 1);
        }
        else {
            return null;
        }
    }
    
    protected String getJoinValue(String text) {
        if (text.startsWith(JOIN_VALUE_PREFIX) && text.endsWith(COMPOUND_VALUE_SUFFIX)) {
            return text.substring(JOIN_VALUE_PREFIX.length(), text.length() - 1);
        }
        else {
            return null;
        }
    }
    
    private int parseInt(String text) throws ParseException {
        try {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            throw new ParseException(e.getLocalizedMessage(), 0);
        }
    }
    
    protected RangeLocation parseSpanLocation(String text, boolean complement) throws ParseException {
        BoundedRangeLocation.Builder builder = new BoundedRangeLocation.Builder();
        builder.setComplement(complement);
        
        if (text.charAt(0) == '<') {
            text = text.substring(1);
            builder.setLesserStart(true);
        }
        
        // remove accession
        Matcher accessionMatcher = Pattern.compile(accessionPattern).matcher(text);
        if (accessionMatcher.find()) {
            text = text.substring(accessionMatcher.group(0).length());
        }
        
        // start position
        Matcher startNumMatcher = numberMatchPattern.matcher(text);
        if (!startNumMatcher.find()) {
            throw new ParseException("start position not found: " + text, 0);
        }
        String startText = startNumMatcher.group(0);
        final int start = parseInt(startText);
        builder.setStart(Integer.parseInt(startText));
        
        text = text.substring(startText.length());
        
        // single point
        if (text.isEmpty()) {
            return RangeLocation.newExactPointLocation(start, complement);
        }
        
        // range type
        if (text.startsWith("..")) {
            text = text.substring(2);
        }
        else if (text.startsWith(".")) {
            int end = parseInt(text.substring(1));
            return RangeLocation.newPointLocation(start, end, complement);
        }
        else if (text.startsWith("^")) {
            int end = parseInt(text.substring(1));
            if (end - start != 1)
                throw new ParseException("invalid site position: " + text, 0); 
            return RangeLocation.newSiteLocation(start, complement);
        }
        else throw new ParseException("unknown bounds symbol: " + text, 0);

        if (text.charAt(0) == '>') {
            text = text.substring(1);
            builder.setGreaterEnd(true);
        }

        int end = parseInt(text);
        builder.setEnd(end);
        
        return builder.build();
    }
    
    String[] split(String text) {
        return text.split(",");
        
    }
}
