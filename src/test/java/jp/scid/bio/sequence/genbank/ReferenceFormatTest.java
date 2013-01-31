package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import jp.scid.bio.sequence.genbank.Reference;
import jp.scid.bio.sequence.genbank.ReferenceFormat;

import org.junit.Before;
import org.junit.Test;

public class ReferenceFormatTest {
    private final static List<String> referenceLines1 = Arrays.asList(
            "REFERENCE   1  (bases 1 to 3444)",
            "  AUTHORS   Erauso,G., Marsin,S., Benbouzid-Rollet,N., Baucher,M.F.,",
            "            Barbeyron,T., Zivanovic,Y., Prieur,D. and Forterre,P.",
            "  TITLE     Sequence of plasmid pGT5 from the archaeon Pyrococcus abyssi:",
            "            evidence for rolling-circle replication in a hyperthermophile",
            "  JOURNAL   J. Bacteriol. 178 (11), 3232-3237 (1996)",
            "   PUBMED   8655503");
    
    ReferenceFormat format;
    
    @Before
    public void setUp() throws Exception {
        format = new ReferenceFormat();
    }

    @Test
    public void parse_1() throws ParseException {
        Reference reference = format.parse(referenceLines1);
        
        assertNotNull(reference);
        assertEquals(1, reference.basesStart());
        assertEquals(3444, reference.basesEnd());
        assertEquals("Erauso,G., Marsin,S., Benbouzid-Rollet,N., Baucher,M.F.,\n"
                + "Barbeyron,T., Zivanovic,Y., Prieur,D. and Forterre,P.\n",
                reference.authors());
        assertEquals("Sequence of plasmid pGT5 from the archaeon Pyrococcus abyssi:\n"
                + "evidence for rolling-circle replication in a hyperthermophile\n",
                reference.title());
        assertEquals("J. Bacteriol. 178 (11), 3232-3237 (1996)\n",
                reference.journal());
        assertEquals("8655503\n",
                reference.pubmed());
    }
}
