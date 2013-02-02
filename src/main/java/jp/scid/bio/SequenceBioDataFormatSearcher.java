package jp.scid.bio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.scid.bio.sequence.SequenceBioData;
import jp.scid.bio.sequence.fasta.FastaFormat;
import jp.scid.bio.sequence.genbank.GenBankFormat;

public class SequenceBioDataFormatSearcher {
    private List<SequenceBioDataFormat<?>> formats;
    
    public SequenceBioDataFormatSearcher(List<SequenceBioDataFormat<?>> formatList) {
        this.formats = new ArrayList<SequenceBioDataFormat<?>>(formatList);
    }
    
    public SequenceBioDataFormatSearcher(SequenceBioDataFormat<?>... formats) {
        this(Arrays.asList(formats));
    }
    
    public SequenceBioDataFormatSearcher() {
        this(new GenBankFormat(), new FastaFormat());
    }
    
    public SequenceBioDataFormat<? extends SequenceBioData> findFormat(File file) throws IOException {
        return findFormat(file.toURI().toURL());
    }
    
    public SequenceBioDataFormat<? extends SequenceBioData> findFormat(URL url) throws IOException {
        InputStreamReader instReader = new InputStreamReader(url.openStream());
        
        String headText;
        
        try {
            BufferedReader reader = new BufferedReader(instReader);
        
            char[] cbuf = new char[1024];
            int read = reader.read(cbuf);
            headText = read <= 0 ? "" : new String(cbuf);
        }
        finally {
            instReader.close();
        }
        
        return findFormat(headText);
    }

    public SequenceBioDataFormat<? extends SequenceBioData> findFormat(String fileText) throws IOException {
        for (SequenceBioDataFormat<?> format: formats) {
            if (existsStartOfData(fileText, format)) {
                return format;
            }
        }
        
        return null;
    }
    
    boolean existsStartOfData(String headText, SequenceBioDataFormat<?> format) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(headText));
        
        String line;
        while ((line = reader.readLine()) != null) {
            if (format.isStartOfData(line)) {
                return true;
            }
        }
        
        return false;
    }
}
