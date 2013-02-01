package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VersionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void equals() {
        assertTrue(new Version("", 0, "").equals(Version.newVersion("", 0, "")));
        assertFalse(new Version("NC_013419", 0, "").equals(Version.newVersion("", 0, "")));
        assertFalse(new Version("", 1, "").equals(Version.newVersion("", 0, "")));
        assertFalse(new Version("", 0, "261599111").equals(Version.newVersion("", 0, "")));
        assertTrue(new Version("NC_013419", 1, "261599111").equals(Version.newVersion("NC_013419", 1, "261599111")));
    }
}
