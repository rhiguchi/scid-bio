package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Collections;

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

        Definition definition = format.parse(Collections.singletonList(line));

        assertEquals(
                "parsed valueList",
                Definition.newDefinition("Pyrococcus abyssi GE5 plasmid pGT5, complete sequence."),
                definition);
    }
}
