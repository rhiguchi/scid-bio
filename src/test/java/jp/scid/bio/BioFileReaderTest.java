package jp.scid.bio;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import jp.scid.bio.sequence.genbank.GeneBankFileReader;

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
    public void test() throws IOException {
        GeneBankFileReader builder = new GeneBankFileReader();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(gbResource));
        List<GenBank> data = builder.readFromBufferedReader(reader);
        
        assertEquals(1, data.size());
    }
}
