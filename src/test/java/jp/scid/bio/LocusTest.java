package jp.scid.bio;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import jp.scid.bio.sequence.genbank.Locus;
import jp.scid.bio.sequence.genbank.Locus.Builder;
import jp.scid.bio.sequence.genbank.Locus.Format;

import org.junit.Before;
import org.junit.Test;

public class LocusTest {
	Builder builder;
	@Before
	public void setUp() throws Exception {
		builder = new Builder();
	}

	// name
	@Test
	public void Builder_name() {
		assertEquals("empty initial", "", builder.build().getName());
		
		builder.name("name");
		assertEquals("name", builder.build().getName());
		
		builder.name("name2");
		assertEquals("name2", builder.build().getName());
	}
	
	// sequenceLength
	@Test
	public void Builder_sequenceLength() {
		assertEquals("zero initial", 0, builder.build().getSequenceLength());
		
		builder.sequenceLength(1);
		assertEquals(1, builder.build().getSequenceLength());
		
		builder.sequenceLength(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, builder.build().getSequenceLength());
	}
	
	// sequenceUnit
	@Test
	public void Builder_sequenceUnit() {
		assertEquals("empty initial", "", builder.build().getSequenceUnit());
		
		builder.sequenceUnit("bp");
		assertEquals("bp", builder.build().getSequenceUnit());
		
		builder.sequenceUnit("aa");
		assertEquals("aa", builder.build().getSequenceUnit());
	}
	
	// molculeType
	@Test
	public void Builder_molculeType() {
		assertEquals("empty initial", "", builder.build().getMolculeType());
		
		builder.molculeType("RNA");
		assertEquals("RNA", builder.build().getMolculeType());
		
		builder.molculeType("DNA");
		assertEquals("DNA", builder.build().getMolculeType());
	}
	
	// Parse
	@Test
	public void parse_1() throws ParseException {
		builder.name("NC_001773");
		builder.sequenceLength(3444);
		builder.sequenceUnit("bp");
		builder.molculeType("DNA");
		builder.topology("circular");
		builder.division("BCT");
		builder.date(new SimpleDateFormat("yyyy-MM-dd").parse("2006-03-30"));
		
		String line = "LOCUS       NC_001773               3444 bp    DNA     circular BCT 30-MAR-2006";
		
		Format format = new Format();
		Locus locus = format.parse(line);
		
		assertEquals(builder.build(), locus);
	}
}
