package pl.sda.stats.importing;

import pl.sda.stats.company.Company;

import java.util.List;

public interface CompanyImporter {

    List<Company> readCompanies();
}
