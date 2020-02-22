package pl.sda.stats;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CompanyImporter companyImporter = new CompanyImporter("src/main/resources/constituents-financials_csv.csv");
        List<Company> companies = companyImporter.readCompanies();

        companies.stream().sorted(new ByPriceCompanyComparator())
                .limit(3).forEach(System.out::println);

        companies.stream().sorted(new ByDividendCompanyComparator())
                .limit(3).forEach(System.out::println);

    }
}
