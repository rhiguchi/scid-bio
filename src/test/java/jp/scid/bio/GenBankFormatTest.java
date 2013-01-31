package jp.scid.bio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.scid.bio.sequence.genbank.GenBankFormat;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class GenBankFormatTest {
    final static String GENBANK_FILE = "NC_001773.gbk";
    
    GenBankFormat format;
    
    @Before
    public void setup() throws Exception {
        format = new GenBankFormat();
    }
    
    @Test
    public void parse() throws IOException, ParseException {
        List<String> source = IOUtils.readLines(
                getClass().getResourceAsStream(GENBANK_FILE));
        
        GenBank genBank = format.parse(source);
        
        assertNotNull(genBank);
        assertNotNull(genBank.getLocus());
        assertNotNull(genBank.getDefinition());
        assertNotNull(genBank.getAccession());
        assertNotNull(genBank.getVersion());
        assertNotNull(genBank.getKeywords());
        assertNotNull(genBank.getSource());
        assertNotNull(genBank.getReference());
        assertNotNull(genBank.getComment());
        
        assertNotNull(genBank.getFeatures());
        assertEquals("features count", 11, genBank.getFeatures().getFeatures().size());
        
        assertNotNull(genBank.getOrigin());
        assertEquals("origin length", 3444, genBank.getOrigin().getSequence().length());
    }
}
