package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class AccessionFormatTest {
    AccessionFormat format;
    
    @Before
    public void setUp() throws Exception {
        format = new AccessionFormat();
    }

    @Test
    public void parse() throws ParseException {
        assertEquals(Accession.newAccession("NC_013419"),
                format.parse(Arrays.<String>asList("ACCESSION   NC_013419")));
        
        assertEquals(Accession.newAccession("NC_013419", "NC_001773"),
                format.parse(Arrays.<String>asList("ACCESSION   NC_013419 NC_001773")));
    }
}
