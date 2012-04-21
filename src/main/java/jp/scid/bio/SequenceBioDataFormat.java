package jp.scid.bio;

import java.text.ParseException;

public interface SequenceBioDataFormat<E extends SequenceBioData> {
    boolean isStartOfData(String line);
    
    E parse(Iterable<String> sourceLines) throws ParseException;
}
