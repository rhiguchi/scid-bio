package jp.scid.bio.sequence.fasta;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.scid.bio.sequence.fasta.Fasta;
import jp.scid.bio.sequence.fasta.FastaFormat;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FastaFormatTest {
    final static String FASTA_EXAMPLE_1 = "fasta_example_1.fasta";
    final static String FASTA_EXAMPLE_2 = "fasta_example_2.fasta";
    FastaFormat format;
    
    public FastaFormatTest() {
        format = new FastaFormat();
    }
    
    @Test
    public void makeHeaderValues_1() throws ParseException {
        String line = ">gi|532319|pir|TVFV2E|TVFV2E envelope protein";
        Fasta.Builder builder = new Fasta.Builder();
        
        format.makeHeaderValues(line, builder);
        Fasta fasta = builder.build();
        
        assertEquals("TVFV2E", fasta.name());
        assertEquals("532319", fasta.identifier());
        assertEquals("pir", fasta.namespace());
        assertEquals("TVFV2E", fasta.accession());
        assertEquals(0, fasta.version());
        assertEquals("envelope protein", fasta.description());
    }
    
    @Test
    public void parse_single() throws IOException, ParseException {
        List<String> source = IOUtils.readLines(
                getClass().getResourceAsStream(FASTA_EXAMPLE_1));
        Fasta fasta = format.parse(source);
        
        assertEquals("OVAX_CHICK", fasta.name());
        assertEquals("129295", fasta.identifier());
        assertEquals("sp", fasta.namespace());
        assertEquals("P01013", fasta.accession());
        assertEquals(0, fasta.version());
        assertEquals("GENE X PROTEIN (OVALBUMIN-RELATED)", fasta.description());
        assertEquals("QIKDLLVSSSTDLDTTLVLVNAIYFKGMWKTAFNAEDTREMPFHVTKQESKPVQMMCMNNSFNVATLPAE"
                + "FLFLIKHNPTNTIVYFGRYWSP", fasta.sequence());
    }
}
