package jp.scid.bio.sequence.genbank;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureLocationFormat {
    private static final String COMPLEMENT_VALUE_PREFIX = "complement(";
    private static final String JOIN_VALUE_PREFIX = "join(";
    private static final String ORDER_VALUE_PREFIX = "order(";
    private static final String COMPOUND_VALUE_SUFFIX = ")";
    private final static String accessionPattern = "^\\w+(:?\\.\\d+)\\:";
    private final static Pattern numberMatchPattern = Pattern.compile("\\d+");
    
    public FeatureLocation parse(String baseText) throws ParseException {
        String text = baseText;
        boolean complement = false;
        
        String value = null;
        
        if ((value = getComplementValue(text)) != null) {
            text = value;
            complement = true;
        }
        
        
        if ((value = getJoinValue(text)) != null) {
            JoinedLocation.Builder builder = new JoinedLocation.Builder();
            makeBuilder(builder, complement, value);
            
            return builder.build();
        }
        else if ((value = getOrderValue(text)) != null) {
            OrderedLocation.Builder builder = new OrderedLocation.Builder();
            makeBuilder(builder, complement, value);
            
            return builder.build();
        }
        else {
            FeatureLocation location = parseSpanLocation(text, complement);
            return location;
        }
    }

    protected void makeBuilder(AbstractJoinedLocation.Builder builder, boolean complement, String value)
            throws ParseException {
        builder.setComplement(complement);
        
        String[] ranges = split(value);
        for (String rangeText: ranges) {
            FeatureLocation location = parse(rangeText);
            builder.add(location);
        }
    }
    
    protected String getComplementValue(String text) {
        if (text.startsWith(COMPLEMENT_VALUE_PREFIX) && text.endsWith(COMPOUND_VALUE_SUFFIX)) {
            return text.substring(COMPLEMENT_VALUE_PREFIX.length(), text.length() - 1);
        }
        return null;
    }
    
    protected String getJoinValue(String text) {
        if (text.startsWith(JOIN_VALUE_PREFIX) && text.endsWith(COMPOUND_VALUE_SUFFIX)) {
            return text.substring(JOIN_VALUE_PREFIX.length(), text.length() - 1);
        }
        return null;
    }
    
    protected String getOrderValue(String text) {
        if (text.startsWith(ORDER_VALUE_PREFIX) && text.endsWith(COMPOUND_VALUE_SUFFIX)) {
            return text.substring(ORDER_VALUE_PREFIX.length(), text.length() - 1);
        }
        return null;
    }
    
    private int parseInt(String text) throws ParseException {
        try {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            throw new ParseException(e.getLocalizedMessage(), 0);
        }
    }
    
    protected RangeLocation parseSpanLocation(String baseText, boolean complement) throws ParseException {
        String text = baseText;
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
