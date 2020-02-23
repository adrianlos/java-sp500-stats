package pl.sda.stats;

import pl.sda.stats.company.Company;
import pl.sda.stats.importing.CompanyImporter;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        CompanyImporter companyImporter = new CompanyImporter("src/main/resources/constituents-financials_csv.csv");
        SP500Statistics sp500Statistics = new SP500Statistics(companyImporter.readCompanies());
        List<Company> companiesWithHighestPrice = sp500Statistics.companiesWithHighestPrice(3);
        List<Company> companiesWithHighestDividendYield = sp500Statistics.companiesWithHighestDividendYield(3);
        List<SP500Statistics.CompanyWithDividend> companiesWithDividends = sp500Statistics.companiesWithHighestDividend(3);

        companiesWithDividends.forEach(System.out::println);




    }


}
