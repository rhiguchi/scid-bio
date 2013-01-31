package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;
import jp.scid.bio.sequence.genbank.Locus.Builder;

import org.junit.Before;
import org.junit.Test;

public class LocusTest {
    Builder builder;

    @Before
    public void setUp() throws Exception {
        builder = new Builder();
    }

    // name
    @Test
    public void Builder_name() {
        assertEquals("empty initial", "", builder.build().name());

        builder.name("set name");
        assertEquals("set name", builder.build().name());

        builder.name("update name");
        assertEquals("update name", builder.build().name());
        
        try {
            builder.name(null);
            fail("set name null was not thrown.");
        }
        catch (Exception expected) {
            assertTrue(true);
        }
    }

    // sequenceLength
    @Test
    public void Builder_sequenceLength() {
        assertEquals("zero initial", 0, builder.build().sequenceLength());

        builder.sequenceLength(1);
        assertEquals(1, builder.build().sequenceLength());

        builder.sequenceLength(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, builder.build().sequenceLength());
    }

    // sequenceUnit
    @Test
    public void Builder_sequenceUnit() {
        assertEquals("empty initial", "", builder.build().sequenceUnit());

        builder.sequenceUnit("bp");
        assertEquals("bp", builder.build().sequenceUnit());

        builder.sequenceUnit("aa");
        assertEquals("aa", builder.build().sequenceUnit());
    }

    // molculeType
    @Test
    public void Builder_molculeType() {
        assertEquals("empty initial", "", builder.build().molculeType());

        builder.molculeType("RNA");
        assertEquals("RNA", builder.build().molculeType());

        builder.molculeType("DNA");
        assertEquals("DNA", builder.build().molculeType());
    }
    
    // topology
    @Test
    public void Builder_topology() {
        assertEquals("empty initial", "", builder.build().topology());
        
        builder.topology("tp");
        assertEquals("tp", builder.build().topology());
        
        builder.topology("other tp");
        assertEquals("other tp", builder.build().topology());
    }
}
