package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DefinitionFormatTest {
    private DefinitionFormat format;

    @Before
    public void setUp() throws Exception {
        format = new DefinitionFormat();
    }

    @Test
    public void parse() throws ParseException {
        String line = "DEFINITION  Pyrococcus abyssi GE5 plasmid pGT5, complete sequence.";

        assertEquals(
                "parsed valueList",
                Definition.newDefinition("Pyrococcus abyssi GE5 plasmid pGT5, complete sequence."),
                format.parse(Collections.singletonList(line)));
    }

    @Test
    public void parse_2() throws ParseException {
        List<String> lines = Arrays.asList(
                "DEFINITION  Blattabacterium sp. (Periplaneta americana) str. BPLAN plasmid",
                "            pBPLAN, complete sequence.");
        
        assertEquals(
                "parsed valueList",
                Definition
                        .newDefinition("Blattabacterium sp. (Periplaneta americana) str. BPLAN plasmid\n"
                            + "pBPLAN, complete sequence."), format.parse(lines));
    }
}
