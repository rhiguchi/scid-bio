package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class VersionFormatTest {
    VersionFormat format;
    
    @Before
    public void setUp() throws Exception {
        format = new VersionFormat();
    }

    @Test
    public void parse() throws ParseException {
        assertEquals(
                Version.newVersion("NC_001773", 1, "10954552"),
                format.parse("VERSION     NC_001773.1  GI:10954552"));
        assertEquals(
                Version.newVersion("NC_013419", 2, "261599111"),
                format.parse("VERSION     NC_013419.2  GI:261599111"));
    }
}
