package jp.scid.bio;

import static java.lang.String.*;

import java.text.ParseException;
import java.util.Iterator;

abstract class AbstractAttributeFormat implements GenBankAttribute.Format {
    int identifierDigits = 12;
    String identifier;

    public AbstractAttributeFormat(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getIdentifierDigits() {
        return identifierDigits;
    }

    public boolean isHeadLine(String line) {
        return line.startsWith(getIdentifier())
            && line.substring(getIdentifier().length(), getIdentifierDigits()).trim().isEmpty();
    }

    String ensureHeadLineExistence(Iterator<String> linesIterator) throws ParseException {
        final String firstLine;
        if (!linesIterator.hasNext() || !isHeadLine(firstLine = linesIterator.next())) {
            throw new ParseException(format("need %s line at first of source lines", identifier), 0);
        }
        return firstLine;
    }
    
    protected String getValueString(String line) {
        return line.substring(identifierDigits);
    }
    
    static int parseSequenceLength(String integerText) throws ParseException {
        try {
            return Integer.parseInt(integerText);
        }
        catch (IllegalArgumentException e) {
            throw new ParseException(integerText, 0);
        }
    }

    String getValueString(Iterable<String> lines, String sep) throws ParseException {
        Iterator<String> linesIterator = lines.iterator();
        String first = ensureHeadLineExistence(linesIterator);
        
        StringBuilder sb = new StringBuilder();
        sb.append(getValueString(first));

        while (linesIterator.hasNext()) {
            String line = linesIterator.next();
            String data = getValueString(line);

            sb.append(sep).append(data);
        }

        String value = sb.toString();
        return value;
    }
}