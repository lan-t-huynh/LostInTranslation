package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GetCode {

    public static String[] getCountryOrLanguage(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(GetCode.class
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            String[] countries = new String[lines.size() - 1];
            int i = 0;
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");
                countries[i] += (parts[0]);
                i += 1;
            }
            return countries;
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String[] getCountry(){
        return getCountryOrLanguage("country-codes.txt");
    }

    public static String[] getLanguage(){
        return getCountryOrLanguage("language-codes.txt");
    }
}