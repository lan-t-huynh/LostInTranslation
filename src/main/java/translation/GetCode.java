package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GetCode {

    public Collection<String> getCountryOrLanguage(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            List<String> countries = new ArrayList<>();
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");
                countries.add(parts[0]);
            }
            return countries;
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Collection<String> getCountry(){
        return getCountryOrLanguage("country-codes.txt");
    }

    public Collection<String> getLanguage(){
        return getCountryOrLanguage("language-codes.txt");
    }
}