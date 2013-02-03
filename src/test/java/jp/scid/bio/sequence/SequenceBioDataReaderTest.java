package jp.scid.bio.sequence;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import jp.scid.bio.sequence.fasta.Fasta;
import jp.scid.bio.sequence.fasta.FastaFormat;
import jp.scid.bio.sequence.genbank.GenBank;
import jp.scid.bio.sequence.genbank.GenBankFormat;

import org.junit.Test;

public class SequenceBioDataReaderTest {
    private static final String FASTA_EXAMPLE_FILE_1 = "fasta/fasta_example_1.fasta";
    private static final String FASTA_EXAMPLE_FILE_2 = "fasta/fasta_example_2.fasta";
    private static final String GENBANK_EXAMPLE_FILE_1 = "genbank/NC_001773.gbk";
    private static final String GENBANK_EXAMPLE_FILE_2 = "multi_sections.gbk";

    @Test
    public void readNext_GenBank() throws IOException, ParseException {
        SequenceBioDataReader<GenBank> reader = new SequenceBioDataReader<GenBank>(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(GENBANK_EXAMPLE_FILE_1))),
                new GenBankFormat());
        
        GenBank genBank = reader.readNext();
        assertNotNull(genBank);
        assertEquals(3444, genBank.locus().sequenceLength());
        
        assertNull(reader.readNext());
        reader.close();
    }
        
    @Test
    public void readNext_GenBank_multi() throws IOException, ParseException {
        SequenceBioDataReader<GenBank> reader = SequenceBioDataReader.fromUrl(
                getClass().getResource(GENBANK_EXAMPLE_FILE_2), new GenBankFormat());
        
        GenBank genBank = reader.readNext();
        assertNotNull(genBank);
        assertEquals(10467782, genBank.locus().sequenceLength());
        
        genBank = reader.readNext();
        assertNotNull(genBank);
        assertEquals(3448, genBank.locus().sequenceLength());
        
        assertNull(reader.readNext());
        reader.close();
    }
    
    @Test
    public void readNext_Fasta() throws IOException, ParseException {
        SequenceBioDataReader<Fasta> reader = new SequenceBioDataReader<Fasta>(
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(FASTA_EXAMPLE_FILE_1))),
                new FastaFormat());
        
        Fasta fasta = reader.readNext();
        assertNotNull(fasta);
        assertEquals(92, fasta.sequence().length());
        
        assertNull(reader.readNext());
        reader.close();
    }
    
    @Test
    public void readNext_Fasta_multi() throws IOException, ParseException {
        SequenceBioDataReader<Fasta> reader = SequenceBioDataReader.fromUrl(
                getClass().getResource(FASTA_EXAMPLE_FILE_2), new FastaFormat());
        
        Fasta fasta = reader.readNext();
        assertNotNull(fasta);
        assertEquals(181, fasta.sequence().length());
        
        fasta = reader.readNext();
        assertNotNull(fasta);
        assertEquals(120, fasta.sequence().length());
        
        assertNull(reader.readNext());
        reader.close();
    }
    
    @Test
    public void readNext_invalid() throws IOException {
        SequenceBioDataReader<Fasta> reader = SequenceBioDataReader.fromUrl(
                getClass().getResource(GENBANK_EXAMPLE_FILE_1), new FastaFormat());
        
        try {
            reader.readNext();
            fail("Expected exception was not thrown.");
        }
        catch (ParseException expected) {
            assertTrue(true);
        }
        
        reader.close();
    }
    
    @Test
    public void readNext_invalid2() throws IOException {
        SequenceBioDataReader<GenBank> reader = SequenceBioDataReader.fromUrl(
                getClass().getResource(FASTA_EXAMPLE_FILE_1), new GenBankFormat());
        
        try {
            reader.readNext();
            fail("Expected exception was not thrown.");
        }
        catch (ParseException expected) {
            assertTrue(true);
        }
        
        reader.close();
    }
}
