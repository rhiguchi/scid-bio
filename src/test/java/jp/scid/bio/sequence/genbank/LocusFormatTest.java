package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class LocusFormatTest {
    private LocusFormat format;
    
    @Before
    public void setUp() throws Exception {
        format = new LocusFormat();
    }

    // Parse
    @Test
    public void parse_1() throws ParseException {
        String line =
            "LOCUS       NC_001773               3444 bp    DNA     circular BCT 30-MAR-2006";
        Locus locus = format.parse(line);

        assertEquals("NC_001773", locus.name());
        assertEquals(3444, locus.sequenceLength());
        assertEquals("bp", locus.sequenceUnit());
        assertEquals("DNA", locus.molculeType());
        assertEquals("circular", locus.topology());
        assertEquals("BCT", locus.division());
        assertEquals(new GregorianCalendar(2006, 2, 30).getTime(), locus.date());
    }
}
