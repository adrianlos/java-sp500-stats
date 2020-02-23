package pl.sda.stats;

import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class Main {
    public static void main(String[] args) {
        CompanyImporter companyImporter = new CompanyImporter("src/main/resources/constituents-financials_csv.csv");
        List<Company> companies = companyImporter.readCompanies();

        companies.stream().sorted(new ByPriceCompanyComparator())
                .limit(3).forEach(System.out::println);
        //1
        companies.stream().sorted(new ByDividendCompanyComparator().reversed())
                .limit(3).forEach(System.out::println);

        //2
        companies.stream().sorted(new Comparator<Company>() {
            @Override
            public int compare(Company o1, Company o2) {
                return o2.getName().compareTo(o1.getName());
            }
        }.reversed())
                .limit(3).forEach(System.out::println);


        //3
        companies.stream()
                .sorted(((company1, company2) -> {
                    return company2.getName().compareTo(company1.getName());
                }))
                .limit(3).forEach(System.out::println);

        //3a
        companies.stream()
                .sorted(((company1, company2) -> company2.getName().compareTo(company1.getName())))
                .limit(3).forEach(System.out::println);

        //4
        companies.stream().sorted(comparing(Company::getName)).limit(3).forEach(System.out::println);

        //5
        companies.stream().sorted().limit(3).forEach(System.out::println);

        companies.stream()
                .map(CompanyWithDividend::fromCompany)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .forEach(System.out::println);

    }


}
