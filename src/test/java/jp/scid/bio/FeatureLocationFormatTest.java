package jp.scid.bio;

import static org.junit.Assert.*;

import java.text.ParseException;

import jp.scid.bio.RangeLocation.ExactPointLocation;
import jp.scid.bio.RangeLocation.PointLocation;
import jp.scid.bio.RangeLocation.SiteLocation;

import org.junit.Before;
import org.junit.Test;

public class FeatureLocationFormatTest {
    FeatureLocationFormat format;
    
    @Before
    public void setUp() throws Exception {
        format = new FeatureLocationFormat();
    }

    @Test
    public void parseExactPointLocation() throws ParseException {
        String text = "467";
        
        ExactPointLocation location = (ExactPointLocation) format.parse(text);
        assertEquals("start", 467, location.getStart());
        assertEquals("end", 467, location.getEnd());
        assertFalse("not complement", location.isComplement());
    }
    
    @Test
    public void parsePointLocation() throws ParseException {
        String text = "102.110";
        
        PointLocation location = (PointLocation) format.parse(text);
        assertEquals("start", 102, location.getStart());
        assertEquals("end", 110, location.getEnd());
        assertFalse("not complement", location.isComplement());
    }
    
    
    @Test
    public void parseSiteLocation() throws ParseException {
        String text = "123^124";
        
        SiteLocation location = (SiteLocation) format.parse(text);
        assertEquals("start", 123, location.getStart());
        assertEquals("endnd", 124, location.getEnd());
        assertFalse("not complement", location.isComplement());
    }
    
    @Test
    public void parseBoundedRangeLocation() throws ParseException {
        String text = "340..565";
        
        BoundedRangeLocation location = (BoundedRangeLocation) format.parse(text);
        assertEquals("start", 340, location.getStart());
        assertEquals("end", 565, location.getEnd());
        assertFalse("not complement", location.isComplement());
        assertFalse("not lesser start", location.isLesserStart());
        assertFalse("not greater end", location.isGreaterEnd());
    }
    
    @Test
    public void parseBoundedRangeLocation_lesserStart() throws ParseException {
        String text = "<345..500";
        
        BoundedRangeLocation location = (BoundedRangeLocation) format.parse(text);
        assertEquals("start", 345, location.getStart());
        assertEquals("end", 500, location.getEnd());
        assertFalse("not complement", location.isComplement());
        assertTrue("lesser start", location.isLesserStart());
        assertFalse("not greater end", location.isGreaterEnd());
    }
    
    @Test
    public void parseBoundedRangeLocation_greaterStart() throws ParseException {
        String text = "1..>888";
        
        BoundedRangeLocation location = (BoundedRangeLocation) format.parse(text);
        assertEqualsStartEnd(1, 888, false, location);
        assertFalse("not lesser start", location.isLesserStart());
        assertTrue("greater end", location.isGreaterEnd());
    }

    @Test
    public void parseComplementLocation() throws ParseException {
        String text = "complement(34..126)";
        
        BoundedRangeLocation location = (BoundedRangeLocation) format.parse(text);
        assertEqualsStartEnd(34, 126, true, location);
        assertFalse("not lesser start", location.isLesserStart());
        assertFalse("not greater end", location.isGreaterEnd());
    }
    
    @Test
    public void parseJoinLocation() throws ParseException {
        String text = "join(12..78,134..202)";
        
        JoinedLocation location = (JoinedLocation) format.parse(text);
        assertEquals("start", 12, location.getStart());
        assertEquals("end", 202, location.getEnd());
        assertFalse("not complement", location.isComplement());
        assertEquals("two locations", 2, location.getLocations().size());
        
        assertEqualsStartEnd(12, 78, false, location.getLocations().get(0));
        assertEqualsStartEnd(134, 202, false, location.getLocations().get(1));
    }
    
    @Test
    public void parseJoinComplementLocation() throws ParseException {
        String text = "join(complement(4918..5163),complement(2691..4571))";
        
        JoinedLocation location = (JoinedLocation) format.parse(text);
        assertEquals("start", 2691, location.getStart());
        assertEquals("end", 5163, location.getEnd());
        assertFalse("not complement", location.isComplement());
        assertEquals("two locations", 2, location.getLocations().size());
        
        assertEqualsStartEnd(4918, 5163, true, location.getLocations().get(0));
        assertEqualsStartEnd(2691, 4571, true, location.getLocations().get(1));
    }
    
    @Test
    public void parseComplementJoinLocation() throws ParseException {
        String text = "complement(join(2691..4571,4918..5163))";
        
        JoinedLocation location = (JoinedLocation) format.parse(text);
        assertEquals("start", 2691, location.getStart());
        assertEquals("end", 5163, location.getEnd());
        assertTrue("complement", location.isComplement());
        assertEquals("two locations", 2, location.getLocations().size());
        
        assertEqualsStartEnd(2691, 4571, false, location.getLocations().get(0));
        assertEqualsStartEnd(4918, 5163, false, location.getLocations().get(1));
    }
    
    public static void assertEqualsStartEnd(int start, int end, boolean complement, FeatureLocation actual) {
        assertEquals("start", start, actual.getStart());
        assertEquals("end", end, actual.getEnd());
        assertEquals("complement", complement, actual.isComplement());
    }
}
