package pl.sda.stats;

import pl.sda.stats.company.Company;
import pl.sda.stats.importing.CompanyImporter;
import pl.sda.stats.importing.FromJSONCompanyImporter;

public class Main {
    public static void main(String[] args) {

//        FromCSVCompanyImporter companyImporter = new FromCSVCompanyImporter("src/main/resources/constituents-financials_csv.csv");
        CompanyImporter companyImporter = new FromJSONCompanyImporter("src/main/resources/constituents-financials_json.json");
        SP500Statistics sp500Statistics = new SP500Statistics(companyImporter.readCompanies());
        //List<Company> companiesWithHighestPrice = sp500Statistics.companiesWithHighestPrice(3);
        //List<Company> companiesWithHighestDividendYield = sp500Statistics.companiesWithHighestDividendYield(3);
        //List<SP500Statistics.CompanyWithDividend> companiesWithDividends = sp500Statistics.companiesWithHighestDividend(3);

        //companiesWithDividends.forEach(System.out::println);

        sp500Statistics.companyBySector().entrySet().stream().forEach(sectorCompanyEntry -> {
            System.out.print(sectorCompanyEntry.getKey() + " : ");
            System.out.println(sectorCompanyEntry.getValue().getName());
        });

        System.out.println("----");

        sp500Statistics.companyBySector2().entrySet().stream().forEach(sectorCompanyEntry -> {
            System.out.print(sectorCompanyEntry.getKey() + " : ");
            System.out.println(sectorCompanyEntry.getValue().getName());
        });

        System.out.println("----");

        sp500Statistics.companyBySector3().entrySet().stream().forEach(sectorCompanyEntry -> {
            System.out.print(sectorCompanyEntry.getKey() + " : ");
            sectorCompanyEntry.getValue().map(Company::getName).ifPresent(System.out::println);
        });



    }


}
