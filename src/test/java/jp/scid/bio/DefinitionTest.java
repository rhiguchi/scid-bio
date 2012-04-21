package jp.scid.bio;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Collections;

import jp.scid.bio.Definition.Format;

import org.junit.Test;

public class DefinitionTest {

	@Test
	public void emptyInstance() {
		assertEquals("empty", "", Definition.newDefinition("").getValue());
	}
	
	@Test
	public void definition_value() {
		assertEquals("with value", "def", Definition.newDefinition("def").getValue());
	}

	@Test
	public void Format_parse() throws ParseException {
		String line = "DEFINITION  Pyrococcus abyssi GE5 plasmid pGT5, complete sequence.";
		Format format = new Format();
		
		Definition definition = format.parse(Collections.singletonList(line));
		
		assertEquals("parsed valueList",
				Definition.newDefinition("Pyrococcus abyssi GE5 plasmid pGT5, complete sequence."),
				definition);
	}
}
