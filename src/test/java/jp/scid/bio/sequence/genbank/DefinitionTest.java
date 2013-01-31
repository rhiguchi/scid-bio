package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import org.junit.Test;

public class DefinitionTest {

    @Test
    public void emptyInstance() {
        assertEquals("empty", "", Definition.newDefinition("").value());
    }

    @Test
    public void definition_value() {
        assertEquals("with value", "def", Definition.newDefinition("def").value());
    }
}
