package jp.scid.bio.sequence.genbank;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.scid.bio.sequence.genbank.Feature;
import jp.scid.bio.sequence.genbank.Features;
import jp.scid.bio.sequence.genbank.FeaturesFormat;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class FeaturesFormatTest {
    private final static String RESOURCE_FEATURE_SOURCE = "features1.gbk";

    FeaturesFormat format;
    
    @Before
    public void setup() throws Exception {
        format = new FeaturesFormat();
    }
    
    @Test
    public void test() throws ParseException, IOException {
        List<String> featuresLines = IOUtils.readLines(
                getClass().getResourceAsStream(RESOURCE_FEATURE_SOURCE));
        
        Features features = format.parse(featuresLines);
        
        assertNotNull(features);
        List<Feature> featureList = features.getFeatures();
        
        assertEquals("feature count", 11, featureList.size());
        
        assertEquals("key for head feature", "source", featureList.get(0).getKey());
        
        assertEquals("key for last featureList",
                "repeat_region", featureList.get(featureList.size() - 1).getKey());
    }
}
