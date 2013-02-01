package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.util.Arrays;

import jp.scid.bio.sequence.genbank.Accession.Builder;

import org.junit.Before;
import org.junit.Test;

public class AccessionTest {
    Builder builder;
    
    @Before
    public void setUp() throws Exception {
        builder = new Builder();
    }

    @Test
    public void Builder_empty() {
        assertEquals("primary", "", builder.build().primary());
        assertNotNull("secondary", builder.build().secondary());
        assertEquals("secondary", 0, builder.build().secondary().size());
    }
    
    @Test
    public void Builder_primary() {
        assertEquals("primary", "NC_001773", builder.append("NC_001773").build().primary());
        assertNotNull("secondary", builder.build().secondary());
        assertEquals("secondary", 0, builder.build().secondary().size());
    }
    
    @Test
    public void Builder_secondary() {
        Accession accession = builder.append("NC_001773").append("NC_001773_2").build();
        assertEquals("primary", "NC_001773", accession.primary());
        assertEquals("secondary", Arrays.asList("NC_001773_2"), accession.secondary());
        
        accession = builder.append("NC_001773_3").build();
        assertEquals("secondary", Arrays.asList("NC_001773_2", "NC_001773_3"), accession.secondary());
    }
    
    @Test
    public void equals() {
        Accession empty = builder.build();
        
        assertTrue("empty", new Builder().build().equals(empty));
        
        assertFalse("empty", builder.append("NC_013419").build().equals(empty));
        
        assertTrue("empty", new Builder().append("NC_013419").build().equals(builder.build()));
        
        assertTrue("empty", Accession.newAccession("NC_013419", "NC_013419_2").equals(
                builder.append("NC_013419_2").build()));
    }
}
