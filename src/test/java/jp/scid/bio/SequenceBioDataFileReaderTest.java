package jp.scid.bio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

public class SequenceBioDataFileReaderTest {
    @Test
    public void findFormat() throws IOException {
        SequenceBioDataFormatSearcher reader = new SequenceBioDataFormatSearcher();
        
        URL gbkSource = getClass().getResource(GenBankFormatTest.GENBANK_FILE);
        assertTrue("find format", reader.findFormat(gbkSource) instanceof GenBankFormat);
        
        URL fastaSource = getClass().getResource(FastaFormatTest.FASTA_EXAMPLE_2);
        assertTrue("find format", reader.findFormat(fastaSource) instanceof FastaFormat);
    }
}
