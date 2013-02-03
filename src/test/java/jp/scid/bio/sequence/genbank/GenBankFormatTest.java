package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import jp.scid.bio.sequence.SequenceBioDataReader;
import jp.scid.bio.sequence.genbank.GenBankFormat.AttibuteKey;
import jp.scid.bio.sequence.genbank.GenBankFormat.LineParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenBankFormatTest {
    final static String GENBANK_FILE = "NC_001773.gbk";
    final static String GENBANK_FILE2 = "NC_013419.gbk";
    final static String GENBANK_FILE3 = "NC_013131.gbk";
    
    GenBankFormat format;
    Closeable resource;
    
    @Before
    public void setUp() throws Exception {
        format = new GenBankFormat();
    }

    @After
    public void tearDown() throws Exception {
        if (resource != null)
            resource.close();
    }

    private BufferedReader openResource(String resourcePath) {
        InputStream gbResource = getClass().getResourceAsStream(resourcePath);
        resource = gbResource;
        return new BufferedReader(new InputStreamReader(gbResource));
    }
    
    @Test
    public void isDataStartLine() {
        assertTrue(format.isDataStartLine("LOCUS       NC_013131           10467782 bp    DNA     circular BCT 09-NOV-2009"));
        assertFalse(format.isDataStartLine("DEFINITION  Blattabacterium sp. (Periplaneta americana) str. BPLAN plasmid"));
        assertFalse(format.isDataStartLine("//"));
        assertTrue(format.isDataStartLine("LOCUS       NC_013419               3448 bp    DNA     circular BCT 04-NOV-2009"));
    }
    
    @Test
    public void testAttibuteKey_getFormat() {
        assertTrue("LocusFormat", AttibuteKey.LOCUS.getFormat(format) instanceof LocusFormat);
        assertTrue("DefinitionFormat", AttibuteKey.DEFINITION.getFormat(format) instanceof DefinitionFormat);
        assertTrue("AccessionFormat", AttibuteKey.ACCESSION.getFormat(format) instanceof AccessionFormat);
        assertTrue("VersionFormat", AttibuteKey.VERSION.getFormat(format) instanceof VersionFormat);
        assertTrue("KeywordsFormat", AttibuteKey.KEYWORDS.getFormat(format) instanceof KeywordsFormat);
        assertTrue("SourceFormat", AttibuteKey.SOURCE.getFormat(format) instanceof SourceFormat);
        assertTrue("ReferenceFormat", AttibuteKey.REFERENCE.getFormat(format) instanceof ReferenceFormat);
        assertTrue("CommentFormat", AttibuteKey.COMMENT.getFormat(format) instanceof CommentFormat);
        assertTrue("FeaturesFormat", AttibuteKey.FEATURES.getFormat(format) instanceof FeaturesFormat);
        assertTrue("OriginFormat", AttibuteKey.ORIGIN.getFormat(format) instanceof OriginFormat);
        assertNull("BaseCountFormat", AttibuteKey.BASE_COUNT.getFormat(format));
        assertTrue("TerminateFormat", AttibuteKey.TERMINATE.getFormat(format) instanceof TerminateFormat);
    }

    @Test
    public void createLineBuilder() throws ParseException {
        LineParser parser = format.createLineBuilder();
        parser.appendLine("LOCUS       NC_006297              33716 bp    DNA     circular BCT 23-JUL-2008");
        GenBank genBank = parser.build();
        
        assertEquals(new Locus.Builder().name("NC_006297").sequenceLength(33716)
                .sequenceUnit("bp").molculeType("DNA").topology("circular")
                .division("BCT").date(2008, 7, 23).build(),
                genBank.locus());
    }

    @Test
    public void createDataReader() throws IOException, ParseException {
        SequenceBioDataReader<GenBank> dataReader = format.createDataReader(openResource(GENBANK_FILE));
        
        GenBank data = dataReader.readNext();
        assertNotNull(data);
        
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
    public void createDataReader_2() throws IOException, ParseException {
        SequenceBioDataReader<GenBank> dataReader = format.createDataReader(openResource(GENBANK_FILE2));
        
        GenBank data = dataReader.readNext();
        assertNotNull(data);

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
    public void createDataReader_3() throws IOException, ParseException {
        SequenceBioDataReader<GenBank> dataReader = format.createDataReader(openResource(GENBANK_FILE3));
        
        GenBank data = dataReader.readNext();
        assertNotNull(data);

        assertEquals(
                new Origin.Builder()
                        .append("tctgtctgaagtgcgccaacagggttctcgcgcacagacgcgcggcatcggcaatcgact")
                        .append("cgacgggcgtgtcgtctggcacaccaccacgtcgttgtgggggcgcactataggcacttc")
                        .append("cgccgaatccggtgtggactgcccgctgcgctttcccggtcaatatcacgacgacgaatc")
                        .append("gg").build(), data.origin());
    }
}
