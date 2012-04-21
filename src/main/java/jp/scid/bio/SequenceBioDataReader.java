package jp.scid.bio;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class SequenceBioDataReader<E extends SequenceBioData> implements Iterator<E>, Closeable {
    private final BufferedReader reader;
    private final SequenceBioDataFormat<E> format;
    
    private String nextFirstLine = null;
    private IOException lastException = null;
    
    public SequenceBioDataReader(Reader reader, SequenceBioDataFormat<E> format) {
        if (reader == null) throw new IllegalArgumentException("reader must not be null");
        if (format == null) throw new IllegalArgumentException("format must not be null");
        
        this.reader = new BufferedReader(reader);
        this.format = format;
    }
    
    public static <E extends SequenceBioData> SequenceBioDataReader<E> fromFile(
            File file, SequenceBioDataFormat<E> format) throws IOException {
        return fromURL(file.toURI().toURL(), format);
    }
    
    public static <E extends SequenceBioData> SequenceBioDataReader<E> fromURL(
            URL url, SequenceBioDataFormat<E> format) throws IOException {
        InputStreamReader reader = new InputStreamReader(url.openStream());
        return new SequenceBioDataReader<E>(reader, format);
    }

    @Override
    public boolean hasNext() {
        ensureExistsNextLine();
        
        return nextFirstLine != null;
    }

    @Override
    public E next() {
        ensureExistsNextLine();
        
        if (nextFirstLine == null)
            throw new NoSuchElementException();
        
        E nextValue = readNext();
        return nextValue;
    }

    public IOException getLastException() {
        return lastException;
    }
    
    void ensureExistsNextLine() {
        if (nextFirstLine != null)
            return;
        
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (format.isStartOfData(line)) {
                    nextFirstLine = line;
                    break;
                }
            }
        }
        catch (IOException e) {
            lastException = e;
        }
    }
    
    E readNext() {
        if (nextFirstLine == null)
            throw new IllegalStateException("check first line before call readNext()");
        
        List<String> lines = new LinkedList<String>();
        lines.add(nextFirstLine);
        
        nextFirstLine = null;
        
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (format.isStartOfData(line)) {
                    nextFirstLine = line;
                    break;
                }

                lines.add(line);
            }
        }
        catch (IOException e) {
            lastException = e;
        }
        
        try {
            return format.parse(lines);
        }
        catch (ParseException e) {
            throw new IllegalStateException("cannot parse data from: " + lines.get(0), e);
        }
    }
    
    List<String> readUntilStart() throws IOException {
        List<String> lines = new LinkedList<String>();
        String line;
        
        while ((line = reader.readLine()) != null) {
            if (format.isStartOfData(line))
                break;
            
            lines.add(line);
        }
        
        return lines;
    }
    
    @Override
    public void close() throws IOException {
        reader.close();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
