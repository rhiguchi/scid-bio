package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;
import jp.scid.bio.sequence.genbank.Origin.Builder;

import org.junit.Before;
import org.junit.Test;

public class OriginTest {
    Builder builder;
    
    @Before
    public void setUp() throws Exception {
        builder = new Builder();
    }
    
    @Test
    public void sequence() {
        assertEquals("", builder.build().sequence());
        assertEquals("sequence", builder.append("sequence").build().sequence());
        assertEquals("sequencex", builder.append("x").build().sequence());
    }

    @Test
    public void equals() {
        Origin empty = builder.build();
        
        assertTrue(builder.build().equals(empty));
        
        assertFalse(builder.append("test").build().equals(empty));
        
        Origin origin = builder.build();
        assertTrue(new Builder().append("test").build().equals(origin));
    }
}
