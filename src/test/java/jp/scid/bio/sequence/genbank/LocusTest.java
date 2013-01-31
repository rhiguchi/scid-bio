package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import jp.scid.bio.sequence.genbank.Locus.Builder;

import org.junit.Before;
import org.junit.Test;

public class LocusTest {
    Builder builder;

    @Before
    public void setUp() throws Exception {
        builder = newBuilder();
    }

    private Builder newBuilder() {
        return new Builder();
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
    
    @Test
    public void equals() {
        Locus empty = builder.build();
        
        assertTrue("empty eq", newBuilder().build().equals(empty)); 
    }
    
    @Test
    public void equals_name() {
        Locus locus = builder.name("name").build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().name("name").build().equals(locus)); 
    }
    
    @Test
    public void equals_sequenceLength() {
        Locus locus = builder.sequenceLength(1234).build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().sequenceLength(1234).build().equals(locus)); 
    }
    
    @Test
    public void equals_sequenceUnit() {
        Locus locus = builder.sequenceUnit("bp").build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().sequenceUnit("bp").build().equals(locus)); 
    }
    
    @Test
    public void equals_molculeType() {
        Locus locus = builder.molculeType("DNA").build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().molculeType("DNA").build().equals(locus)); 
    }
    
    @Test
    public void equals_topology() {
        Locus locus = builder.topology("BCT").build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().topology("BCT").build().equals(locus)); 
    }
    
    @Test
    public void equals_division() {
        Locus locus = builder.division("BCT").build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().division("BCT").build().equals(locus)); 
    }
    
    @Test
    public void equals_date() {
        Locus locus = builder.date(new GregorianCalendar(2006, 2, 30).getTime()).build();
        assertFalse(newBuilder().build().equals(locus)); 
        assertTrue(newBuilder().date(new GregorianCalendar(2006, 2, 30).getTime()).build().equals(locus)); 
    }
}
