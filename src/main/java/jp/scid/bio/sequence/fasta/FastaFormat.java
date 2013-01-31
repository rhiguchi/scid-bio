package jp.scid.bio.sequence.fasta;

import java.text.ParseException;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.scid.bio.SequenceBioDataFormat;

public class FastaFormat implements SequenceBioDataFormat<Fasta> {
    @SuppressWarnings("unused")
    private final static Logger logger = Logger.getLogger(FastaFormat.class.getName());

    // e.g. >gi|532319|pir|TVFV2E|TVFV2E envelope protein
    private final static String DEFAULT_HEADER_PATTERN = "(?x)^> \\s* (\\S+) (?:\\s+(.*))? ";
    private final static String DEFAULT_NAME_DETAILS_PATTERN = "(?x)^(?:gi \\| (\\d+) \\| )?" // e.g. "gi|532319|"
            +  "(\\w+) \\| (\\w+?) (?:\\. (\\d+) )? \\|" // e.g. "pir|TVFV2E|" or "pir|TVFV2E.1|"
            + "(\\S+)? $"; // e.g. "TVFV2E"

    public FastaFormat() {
    }

    public boolean isStartOfData(String line) {
        return line.startsWith(">");
    }
    
    public String getHeaderPattern() {
        return DEFAULT_HEADER_PATTERN;
    }
    
    public String getNameDetailsPattern() {
        return DEFAULT_NAME_DETAILS_PATTERN;
    }
    
    public Fasta parse(Iterable<String> sourceLines) throws ParseException {
        Iterator<String> source = sourceLines.iterator();
        final String first;
        
        if (!source.hasNext() || !isStartOfData(first = source.next())) {
            throw new ParseException("need header line starts with '>' at first of source lines", 0);
        }
        
        Fasta.Builder builder = new Fasta.Builder();
        
        // header
        makeHeaderValues(first, builder);
        
        // sequence
        StringBuilder sequence = new StringBuilder();
        
        while (source.hasNext()) {
            final String line = source.next();
            sequence.append(line);
        }
        
        builder.sequence(sequence.toString());
        
        return builder.build();
    }

    void makeHeaderValues(String first, Fasta.Builder builder) {
        Matcher headerMatcher = Pattern.compile(getHeaderPattern()).matcher(first);
        headerMatcher.find();
        String name = headerMatcher.group(1);
        String description = headerMatcher.group(2);
        
        Matcher descriptionMatcher = Pattern.compile(getNameDetailsPattern()).matcher(name);
        if (descriptionMatcher.matches()) {
            String identifier = descriptionMatcher.group(1);
            String namespace = descriptionMatcher.group(2);
            String accession = descriptionMatcher.group(3);
            String versionText = descriptionMatcher.group(4);
            int version = versionText != null ? Integer.parseInt(versionText) : 0;
            name = descriptionMatcher.group(5);
            
            builder.identifier(nonNull(identifier));
            builder.namespace(nonNull(namespace));
            builder.accession(nonNull(accession));
            builder.version(version);
        }
        
        builder.name(nonNull(name));
        builder.description(nonNull(description));
    }
    
    static String nonNull(String text) {
        return text == null ? "" : text;
    }
}
