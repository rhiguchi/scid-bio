package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import jp.scid.bio.sequence.genbank.Source;
import jp.scid.bio.sequence.genbank.SourceFormat;

import org.junit.Before;
import org.junit.Test;

public class SourceFormatTest {
    private final static List<String> sourceLines1 = Arrays.asList(
            "SOURCE      Pyrococcus abyssi GE5",
            "  ORGANISM  Pyrococcus abyssi GE5",
            "            Archaea; Euryarchaeota; Thermococci; Thermococcales;",
            "            Thermococcaceae; Pyrococcus.");
    private final static List<String> sourceLines2 = Arrays.asList(
            "SOURCE      Listeria ivanovii",
            "  ORGANISM  Listeria ivanovii",
            "            Bacteria; Firmicutes; Bacillales; Listeriaceae; Listeria.");
    
    SourceFormat format;
    
    @Before
    public void setup() throws Exception {
        format = new SourceFormat();
    }
    
    @Test
    public void parse_1() throws ParseException {
        Source source = format.parse(sourceLines1);
        
        assertNotNull(source);
        assertEquals("value", "Pyrococcus abyssi GE5", source.getValue());
        assertEquals("organism", "Pyrococcus abyssi GE5", source.getOrganism());
        assertEquals("taxonomy", Arrays.asList("Archaea", "Euryarchaeota", "Thermococci",
                "Thermococcales", "Thermococcaceae", "Pyrococcus"), source.getTaxonomy());
    }
    
    @Test
    public void parse_2() throws ParseException {
        Source source = format.parse(sourceLines2);
        
        assertNotNull(source);
        assertEquals("value", "Listeria ivanovii", source.getValue());
        assertEquals("organism", "Listeria ivanovii", source.getOrganism());
        assertEquals("taxonomy", Arrays.asList("Bacteria", "Firmicutes", "Bacillales",
                "Listeriaceae", "Listeria"), source.getTaxonomy());
    }
}
