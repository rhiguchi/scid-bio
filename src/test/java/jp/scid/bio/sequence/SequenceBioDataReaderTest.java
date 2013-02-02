package jp.scid.bio.sequence;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import jp.scid.bio.sequence.fasta.Fasta;
import jp.scid.bio.sequence.fasta.FastaFormat;
import jp.scid.bio.sequence.fasta.FastaFormatTest;

import org.junit.Test;

public class SequenceBioDataReaderTest {
    
    @Test
    public void iteration() throws IOException {
        URL fastaSource = getClass().getResource("fasta/fasta_example_2.fasta");
        SequenceBioDataReader<Fasta> ite = SequenceBioDataReader.fromURL(fastaSource, new FastaFormat());
        
        assertTrue(ite.hasNext());
        
        Fasta fasta1 = ite.next();
        assertNotNull(fasta1);
        assertEquals("HSBGPG", fasta1.name());
        assertEquals("Human gene for bone gla protein (BGP)", fasta1.description());
        
        assertTrue(ite.hasNext());
        
        Fasta fasta2 = ite.next();
        assertNotNull(fasta2);
        assertEquals("HSGLTH1", fasta2.name());
        assertEquals("Human theta 1-globin gene", fasta2.description());
        
        assertFalse("file end", ite.hasNext());
    }
}
