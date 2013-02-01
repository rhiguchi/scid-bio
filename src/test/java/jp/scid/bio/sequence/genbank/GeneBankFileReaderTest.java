package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GeneBankFileReaderTest {
    final static String GENBANK_FILE = "NC_001773.gbk";
    final static String GENBANK_FILE2 = "NC_013419.gbk";
    final static String GENBANK_FILE3 = "NC_013131.gbk";
    
    GeneBankFileReader reader;
    private BufferedReader source;
    
    private BufferedReader openResource(String resourcePath) {
        InputStream gbResource = getClass().getResourceAsStream(resourcePath);
        return new BufferedReader(new InputStreamReader(gbResource));
    }
    
    @Before
    public void setUp() throws Exception {
        reader = new GeneBankFileReader();
    }
    
    @After
    public void tearDown() throws Exception {
        if (source != null)
            source.close();
    }

    @Test
    public void parse() throws IOException {
        source = openResource(GENBANK_FILE);
        List<GenBank> result = reader.readFromBufferedReader(source);
        
        assertEquals(1, result.size());
        
        GenBank data = result.get(0);
        assertEquals(new Locus.Builder()
                .name("NC_001773").sequenceLength(3444).sequenceUnit("bp").molculeType("DNA")
                .topology("circular").division("BCT").date(2006, 3, 30).build(), data.locus());
        assertEquals(
                Definition.newDefinition("Pyrococcus abyssi GE5 plasmid pGT5, complete sequence."),
                data.definition());
        assertEquals(Accession.newAccession("NC_001773"), data.accession());
        assertEquals(Version.newVersion("NC_001773", 1, "10954552"), data.version());
    }
    
    @Test
    public void parse_2() throws IOException {
        source = openResource(GENBANK_FILE2);
        List<GenBank> result = reader.readFromBufferedReader(source);
        
        assertEquals(1, result.size());
        
        GenBank data = result.get(0);
        assertEquals(new Locus.Builder()
                .name("NC_013419").sequenceLength(3448).sequenceUnit("bp").molculeType("DNA")
                .topology("circular").division("BCT").date(2009, 11, 4).build(), data.locus());
        assertEquals(
                Definition.newDefinition("Blattabacterium sp. (Periplaneta americana) str. BPLAN plasmid\n" +
                		"pBPLAN, complete sequence."),
                data.definition());
        assertEquals(Accession.newAccession("NC_013419"), data.accession());
        assertEquals(Version.newVersion("NC_013419", 1, "261599111"), data.version());
    }
    
    @Test
    public void parse_3() throws IOException {
        source = openResource(GENBANK_FILE3);
        List<GenBank> result = reader.readFromBufferedReader(source);
        
        assertEquals(1, result.size());
        
        GenBank data = result.get(0);
        assertEquals(
                new Origin.Builder()
                        .append("tctgtctgaagtgcgccaacagggttctcgcgcacagacgcgcggcatcggcaatcgact")
                        .append("cgacgggcgtgtcgtctggcacaccaccacgtcgttgtgggggcgcactataggcacttc")
                        .append("cgccgaatccggtgtggactgcccgctgcgctttcccggtcaatatcacgacgacgaatc")
                        .append("gg").build(), data.origin());
    }
}
