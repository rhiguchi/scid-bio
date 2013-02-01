package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import jp.scid.bio.sequence.genbank.Origin;
import jp.scid.bio.sequence.genbank.OriginFormat;

import org.junit.Before;
import org.junit.Test;

public class OriginFormatTest {
    OriginFormat format;
    
    final static List<String> originText = Arrays.asList(
            "ORIGIN      ",
            "        1 attatccttt agaaacgcgt tgggtattgg gggagccccc cttagtgggg agctccccct",
            "       61 aaacaccccc aagaca");

    @Before
    public void setup() throws Exception {
        format = new OriginFormat();
    }
    
    @Test
    public void parse() throws ParseException {
        Origin origin = format.parse(originText);
        
        assertNotNull(origin);
        assertEquals("seqLength", 76, origin.sequence().length());
        assertEquals("sequence",
                "attatcctttagaaacgcgttgggtattgggggagccccccttagtggggagctccccctaaacacccccaagaca",
                origin.sequence());
    }

    @Test
    public void parse_2() throws ParseException {
        List<String> originText = Arrays.asList(
                "ORIGIN      ",
                "        1 acccgttttg gttatcccac caaggatgct gcgcaaagtg ttgatgtgcc actggacaag",
                "   155101 agattcttcc");
        
        assertEquals(
                new Origin.Builder()
                        .append("acccgttttggttatcccaccaaggatgctgcgcaaagtgttgatgtgccactggacaag")
                        .append("agattcttcc").build(),
                        format.parse(originText));
    }
    
    @Test
    public void parse_3() throws ParseException {
        List<String> originText = Arrays.asList(
                "ORIGIN      ",
                "        1 acccgttttg gttatcccac caaggatgct gcgcaaagtg ttgatgtgcc actggacaag",
                "   155101 agattcttcc ");
        
        assertEquals(
                new Origin.Builder()
                .append("acccgttttggttatcccaccaaggatgctgcgcaaagtgttgatgtgccactggacaag")
                .append("agattcttcc").build(),
                format.parse(originText));
    }
    
    @Test
    public void parse_4() throws ParseException {
        List<String> originText = Arrays.asList(
                "ORIGIN      ",
                "        1 acccgttttg gttatcccac caaggatgct gcgcaaagtg ttgatgtgcc actggacaag",
                "   155101 agattcttcc a");
        
        assertEquals(
                new Origin.Builder()
                .append("acccgttttggttatcccaccaaggatgctgcgcaaagtgttgatgtgccactggacaag")
                .append("agattcttcca").build(),
                format.parse(originText));
    }
    
    @Test
    public void parse_5() throws ParseException {
        List<String> originText = Arrays.asList(
                "ORIGIN      ",
                "        1 acccgttttg gttatcccac caaggatgct gcgcaaagtg ttgatgtgcc actggacaag");
        
        assertEquals(
                new Origin.Builder()
                .append("acccgttttggttatcccaccaaggatgctgcgcaaagtgttgatgtgccactggacaag").build(),
                format.parse(originText));
    }
    
    @Test
    public void parse_6() throws ParseException {
        List<String> originText = Arrays.asList(
                "ORIGIN      ",
                "        1 acccgttttg gttatcccac caaggatgct gcgcaaagtg ttgatgtgcc actggacaa");
        
        assertEquals(
                new Origin.Builder()
                .append("acccgttttggttatcccaccaaggatgctgcgcaaagtgttgatgtgccactggacaa").build(),
                format.parse(originText));
    }
}
