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
    
    @Test
    public void value_withSep() {
        assertEquals("sepalator", "value value", Definition.newDefinition("value\nvalue").value(" "));
        assertEquals("sepalator", "value_value", Definition.newDefinition("value\nvalue").value("_"));
    }
    
    @Test
    public void equals() {
        Definition empty = Definition.newDefinition("");
        
        assertTrue(Definition.newDefinition("").equals(empty));
        assertFalse(Definition.newDefinition("test").equals(empty));
        assertTrue(Definition.newDefinition("test").equals(Definition.newDefinition("test")));
    }
}
