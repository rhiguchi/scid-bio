package jp.scid.bio.sequence;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.scid.bio.sequence.fasta.FastaFormat;
import jp.scid.bio.sequence.genbank.GenBankFormat;
import jp.scid.bio.sequence.genbank.LocusFormat;

public class SequenceBioDataFiles {
    private final Map<Class<? extends SequenceBioDataFormat<?>>, FileMatcher> matcherMap;
    
    public SequenceBioDataFiles() {
        matcherMap = new LinkedHashMap<Class<? extends SequenceBioDataFormat<?>>, SequenceBioDataFiles.FileMatcher>();
    }
    
    public static SequenceBioDataFiles newDefaultFormats() {
        SequenceBioDataFiles files = new SequenceBioDataFiles();
        files.addMatcher(GenBankFormat.class, new GenBankFileMatcher());
        files.addMatcher(FastaFormat.class, new FastaFileMatcher());
        
        return files;
    }
    
    public void addMatcher(Class<? extends SequenceBioDataFormat<?>> formatClass, FileMatcher matcher) {
        if (formatClass == null) throw new IllegalArgumentException("formatClass must not be null");
        if (matcher == null) throw new IllegalArgumentException("matcher must not be null");
        
        matcherMap.put(formatClass, matcher);
    }
    
    public void removeMatcher(Class<? extends SequenceBioDataFormat<?>> formatClass) {
        matcherMap.remove(formatClass);
    }
    
    public Class<? extends SequenceBioDataFormat<?>> findFormat(Reader reader) throws IOException {
        CharBuffer buf = CharBuffer.allocate(512);
        reader.read(buf);
        String fileHeadText = buf.flip().toString();
        
        return findFormat(fileHeadText);
    }

    public Class<? extends SequenceBioDataFormat<?>> findFormat(String fileHeadText) {
        if (fileHeadText == null)
            throw new IllegalArgumentException("fileHeadText must not be null");
        
        for (Map.Entry<Class<? extends SequenceBioDataFormat<?>>, FileMatcher> entry: matcherMap.entrySet()) {
            FileMatcher matcher = entry.getValue();
            if (matcher.matches(fileHeadText)) {
                return entry.getKey();
            }
        }
        
        return null;
    }
    
    public Class<? extends SequenceBioDataFormat<?>> findFormat(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        try {
            return findFormat(fileReader);
        }
        finally {
            fileReader.close();
        }
    }
    
    public Class<? extends SequenceBioDataFormat<?>> findFormat(URL url) throws IOException {
        InputStreamReader instReader = new InputStreamReader(url.openStream());
        try {
            return findFormat(instReader);
        }
        finally {
            instReader.close();
        }
    }
    
    public static interface FileMatcher {
        boolean matches(String headText);
    }
    
    static class GenBankFileMatcher implements FileMatcher {
        private final LocusFormat format = new LocusFormat();
        
        @Override
        public boolean matches(String headText) {
            return format.isHeadLine(headText);
        }
    }
    
    static class FastaFileMatcher implements FileMatcher {
        @Override
        public boolean matches(String headText) {
            return headText.startsWith(">");
        }
    }
}
