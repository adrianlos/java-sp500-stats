package pl.sda.stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompanyImporter {
    private static final CompanyMapper COMPANY_MAPPER = new CompanyMapper();
    private final String pathToFile;

    public CompanyImporter(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public List<Company> readCompanies() {
        return fileAsLines()
                .map(line -> line.split(","))
                .map(COMPANY_MAPPER::fromLine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Stream<String> fileAsLines() {
        try {
            return Files.lines(Paths.get(pathToFile));
        } catch (IOException e) {
            return Stream.empty();
        }
    }

}
