package jp.scid.bio.sequence.genbank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Cli {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Cli.class.getName());
        GeneBankFileReader reader = new GeneBankFileReader();
        
        for (String path: args) {
            System.out.println("Reading: " + path);
            BufferedReader source = null;
            
            try {
                source = new BufferedReader(new FileReader(path));
                reader.readFromBufferedReader(source);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (source != null)
                        source.close();
                }
                catch (IOException e) {
                }
            }
        }
    }
}
