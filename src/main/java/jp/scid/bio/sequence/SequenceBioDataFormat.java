package jp.scid.bio.sequence;

import java.io.BufferedReader;
import java.text.ParseException;

public abstract class SequenceBioDataFormat<T extends SequenceBioData> {
    public abstract boolean isDataStartLine(String line);
    
    public abstract LineParser<T> createLineBuilder();
    
    public SequenceBioDataReader<T> createDataReader(BufferedReader source) {
        return new SequenceBioDataReader<T>(source, this);
    }
    
    protected abstract static class LineParser<T extends SequenceBioData> {
        public abstract void appendLine(String line) throws ParseException;
        
        public abstract T build() throws ParseException;
    }
}
