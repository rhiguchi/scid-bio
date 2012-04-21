package jp.scid.bio;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class OriginFormatTest {
    Origin.Format format;
    
    final static List<String> originText = Arrays.asList(
            "ORIGIN      ",
            "        1 attatccttt agaaacgcgt tgggtattgg gggagccccc cttagtgggg agctccccct",
            "       61 aaacaccccc aagaca");

    @Before
    public void setup() throws Exception {
        format = new Origin.Format();
    }
    
    @Test
    public void parse() throws ParseException {
        Origin origin = format.parse(originText);
        
        assertNotNull(origin);
        assertEquals("seqLength", 76, origin.getSequence().length());
        assertEquals("sequence",
                "attatcctttagaaacgcgttgggtattgggggagccccccttagtggggagctccccctaaacacccccaagaca",
                origin.getSequence());
    }

}
