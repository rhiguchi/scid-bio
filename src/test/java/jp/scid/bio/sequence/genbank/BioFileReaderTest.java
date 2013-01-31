package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import jp.scid.bio.GenBank;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BioFileReaderTest {
    final static String GENBANK_FILE = "NC_001773.gbk";
    InputStream gbResource;
    
    @Before
    public void setUp() throws Exception {
        gbResource = getClass().getResourceAsStream(GENBANK_FILE);
        
    }
    
    @After
    public void tearDown() throws Exception {
        if (gbResource != null)
            gbResource.close();
    }

    @Test
    public void parse() throws IOException {
        GeneBankFileReader builder = new GeneBankFileReader();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(gbResource));
        List<GenBank> list = builder.readFromBufferedReader(reader);
        
        assertEquals(1, list.size());
        
        GenBank data = list.get(0);
        assertEquals(new Locus.Builder()
                .name("NC_001773").sequenceLength(3444).sequenceUnit("bp").molculeType("DNA")
                .topology("circular").division("BCT").date(2006, 3, 30).build(), data.locus());
    }
}
