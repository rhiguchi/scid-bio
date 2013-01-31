package jp.scid.bio;

import static org.junit.Assert.*;
import static jp.scid.bio.FeatureLocationFormatTest.assertEqualsStartEnd;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.scid.bio.sequence.genbank.Feature;
import jp.scid.bio.sequence.genbank.FeatureFormat;
import jp.scid.bio.sequence.genbank.FeatureLocation;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class FeatureFormatTest {
    private final static String RESOURCE_FEATURE_SOURCE = "feature-source.gbk";
    private final static String RESOURCE_FEATURE_GENE = "feature-gene.gbk";
    private final static String RESOURCE_FEATURE_CDS = "feature-CDS.gbk";
    
    FeatureFormat format;
    
    @Before
    public void setup() throws Exception {
        format = new FeatureFormat();
    }
    
    static List<String> readResource(String name) {
        try {
            return IOUtils.readLines(FeatureFormatTest.class.getResourceAsStream(name));
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    
    @Test
    public void collectQualifireLines() {
        Iterator<String> source = readResource(RESOURCE_FEATURE_CDS).iterator();
        
        List<String> accume = new ArrayList<String>();
        
        String last = format.collectQualifireLines(accume, source);
        
        assertEquals("lineCount", 1, accume.size());
        assertEquals("line",
                "     CDS             103..2067", accume.get(0));
        
        assertEquals("line last",
                "                     /locus_tag=\"pGT5_01\"", last);
        
        // next
        last = format.collectQualifireLines(accume, source);
        
        accume.clear();
        accume.add(last);
        last = format.collectQualifireLines(accume, source);
        
        assertEquals("lineCount", 2, accume.size());
        assertEquals("line last",
                "                     /note=\"ORF1; contains possible alternative initiation", accume.get(0));
        assertEquals("line last",
                "                     site\"", accume.get(1));
    }
    
    @Test
    public void parseKey() throws ParseException {
        String cdsLine = readResource(RESOURCE_FEATURE_CDS).get(0);
        assertEquals("CDS", format.parseKey(cdsLine));
        
        String geneLine = readResource(RESOURCE_FEATURE_GENE).get(0);
        assertEquals("gene", format.parseKey(geneLine));
        
        String sourceLine = readResource(RESOURCE_FEATURE_SOURCE).get(0);
        assertEquals("source", format.parseKey(sourceLine));
    }

    @Test
    public void parseLocation() throws ParseException {
        String line = "     CDS             <1..>321";
        FeatureLocation location = format.parseLocation(Collections.singletonList(line));
        
        assertEqualsStartEnd(1, 321, false, location);
    }
    
    @Test
    public void parse_source() throws ParseException {
        List<String> sourceLines = readResource(RESOURCE_FEATURE_SOURCE);
        
        Feature feature = format.parse(sourceLines);
        
        assertNotNull(feature);
        assertEqualsStartEnd(1, 3444, false, feature.getLocation());
        assertEquals("qualifires", 5, feature.getQualifiers().size());
        assertEquals("organism", feature.getQualifiers().get(0).getKey());
        assertEquals("mol_type", feature.getQualifiers().get(1).getKey());
        assertEquals("strain", feature.getQualifiers().get(2).getKey());
        assertEquals("db_xref", feature.getQualifiers().get(3).getKey());
        assertEquals("plasmid", feature.getQualifiers().get(4).getKey());
    }
    
    @Test
    public void parse_gene() throws ParseException {
        List<String> sourceLines = readResource(RESOURCE_FEATURE_GENE);
        
        Feature feature = format.parse(sourceLines);
        
        assertNotNull(feature);
        assertEqualsStartEnd(103, 2067, false, feature.getLocation());
        assertEquals("count", 2, feature.getQualifiers().size());
        assertEquals("locus_tag", feature.getQualifiers().get(0).getKey());
        assertEquals("db_xref", feature.getQualifiers().get(1).getKey());
    }
}
