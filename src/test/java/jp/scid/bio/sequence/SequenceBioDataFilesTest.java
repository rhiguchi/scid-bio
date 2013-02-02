package jp.scid.bio.sequence;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;

import jp.scid.bio.sequence.SequenceBioDataFiles.FastaFileMatcher;
import jp.scid.bio.sequence.SequenceBioDataFiles.GenBankFileMatcher;
import jp.scid.bio.sequence.fasta.FastaFormat;
import jp.scid.bio.sequence.genbank.GenBankFormat;

import org.junit.Before;
import org.junit.Test;

public class SequenceBioDataFilesTest {
    private static final String FASTA_EXAMPLE_FILE_1 = "fasta/fasta_example_1.fasta";
    private static final String FASTA_EXAMPLE_FILE_2 = "fasta/fasta_example_2.fasta";
    private static final String GENBANK_EXAMPLE_FILE_1 = "genbank/NC_001773.gbk";
    private static final String GENBANK_EXAMPLE_FILE_2 = "genbank/NC_013419.gbk";
    
    SequenceBioDataFiles files;
    
    @Before
    public void setUp() throws Exception {
        files = new SequenceBioDataFiles();
    }
    
    @Test
    public void findFormat_Fasta() throws IOException {
        files.addMatcher(FastaFormat.class, new FastaFileMatcher());
        
        assertEquals(FastaFormat.class,
                files.findFormat(">gi|129295|sp|P01013|OVAX_CHICK GENE X PROTEIN (OVALBUMIN-RELATED)"));
        assertEquals(FastaFormat.class,
                files.findFormat(getClass().getResource(FASTA_EXAMPLE_FILE_1)));
        assertEquals(FastaFormat.class,
                files.findFormat(new InputStreamReader(getClass().getResourceAsStream(FASTA_EXAMPLE_FILE_2))));
        // not find
        assertNull(files.findFormat("invalid text"));
        assertNull(files.findFormat(getClass().getResource(GENBANK_EXAMPLE_FILE_1)));
    }
    
    @Test
    public void findFormat_GenBank() throws IOException {
        files.addMatcher(GenBankFormat.class, new GenBankFileMatcher());
        
        assertEquals(GenBankFormat.class,
                files.findFormat("LOCUS       NC_013131           10467782 bp    DNA     circular BCT 09-NOV-2009\nDEF"));
        assertEquals(GenBankFormat.class,
                files.findFormat(getClass().getResource(GENBANK_EXAMPLE_FILE_1)));
        assertEquals(GenBankFormat.class,
                files.findFormat(new InputStreamReader(getClass().getResourceAsStream(GENBANK_EXAMPLE_FILE_2))));
        // not find
        assertNull(files.findFormat("invalid text"));
        assertNull(files.findFormat(getClass().getResource(FASTA_EXAMPLE_FILE_1)));
    }
    
    @Test
    public void newDefaultFormats() throws IOException {
        files = SequenceBioDataFiles.newDefaultFormats();
        
        assertEquals(GenBankFormat.class,
                files.findFormat(getClass().getResource(GENBANK_EXAMPLE_FILE_1)));
        assertEquals(FastaFormat.class,
                files.findFormat(getClass().getResource(FASTA_EXAMPLE_FILE_1)));
        assertNull(files.findFormat("invalid text"));
    }
}
