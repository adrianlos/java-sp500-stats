package pl.sda.stats.importing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import pl.sda.stats.company.Company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class FromJSONCompanyImporter implements CompanyImporter {
    private static final Logger logger = Logger.getLogger(FromJSONCompanyImporter.class.getName());
    private final String pathToFile;

    @Override
    public List<Company> readCompanies() {
        try {
            Company[] companies = new ObjectMapper().readValue(fileAsString(), Company[].class);
            return Arrays.asList(companies);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error when reading file", e);
            return Collections.emptyList();
        }
    }

    private String fileAsString() throws IOException {
        return Files.readString(Paths.get(pathToFile));
    }
}
