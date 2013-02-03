package jp.scid.bio.sequence;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;

import jp.scid.bio.sequence.SequenceBioDataFormat.LineParser;

public class SequenceBioDataReader<E extends SequenceBioData> implements Closeable {
    private final BufferedReader source;
    private final SequenceBioDataFormat<E> format;
    private String lastReadLine = null;
    
    public SequenceBioDataReader(BufferedReader source, SequenceBioDataFormat<E> format) {
        if (source == null) throw new IllegalArgumentException("source must not be null");
        if (format == null) throw new IllegalArgumentException("format must not be null");
        
        this.source = source;
        this.format = format;
    }
    
    public static <E extends SequenceBioData> SequenceBioDataReader<E> fromFile(
            File file, SequenceBioDataFormat<E> format) throws IOException {
        FileReader fileReader = new FileReader(file);
        return new SequenceBioDataReader<E>(new BufferedReader(fileReader), format);
    }
    
    public static <E extends SequenceBioData> SequenceBioDataReader<E> fromUrl(
            URL url, SequenceBioDataFormat<E> format) throws IOException {
        InputStreamReader reader = new InputStreamReader(url.openStream());
        return new SequenceBioDataReader<E>(new BufferedReader(reader), format);
    }

    public E readNext() throws IOException, ParseException {
        String line;
        
        // read first line
        if (lastReadLine != null) {
            line = lastReadLine;
            lastReadLine = null;
        }
        else {
            line = source.readLine();
            if (line == null) {
                return null;
            }
        }
        
        LineParser<E> parser = format.createLineBuilder();
        parser.appendLine(line);
        
        while ((line = source.readLine()) != null) {
            if (format.isDataStartLine(line)) {
                lastReadLine = line;
                break;
            }
            
            parser.appendLine(line);
        }
        
        return parser.build();
    }
    
    @Override
    public void close() throws IOException {
        source.close();
    }
}
