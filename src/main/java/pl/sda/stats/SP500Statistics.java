package pl.sda.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.sda.stats.company.Company;
import pl.sda.stats.company.comparators.ByDividendCompanyComparator;
import pl.sda.stats.company.comparators.ByPriceCompanyComparator;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class SP500Statistics {
    private final List<Company> companies;

    List<Company> companiesWithHighestPrice(int i) {
        return companies.stream().sorted(new ByPriceCompanyComparator())
                .limit(i).collect(Collectors.toList());
    }

    List<Company> companiesWithHighestDividendYield(int i) {
        return companies.stream().sorted(new ByDividendCompanyComparator())
                .limit(i).collect(Collectors.toList());
    }

    List<CompanyWithDividend> companiesWithHighestDividend(int i) {
        return companies.stream()
                .map(CompanyWithDividend::fromCompany)
                .sorted(Comparator.reverseOrder())
                .limit(i)
                .collect(Collectors.toList());
    }


//
//    //1
//        companies.stream().sorted(new ByDividendCompanyComparator().reversed())
//            .limit(3).forEach(System.out::println);
//
//    //2
//        companies.stream().sorted(new Comparator<Company>() {
//        @Override
//        public int compare(Company o1, Company o2) {
//            return o2.getName().compareTo(o1.getName());
//        }
//    }.reversed())
//            .limit(3).forEach(System.out::println);
//
//
//    //3
//        companies.stream()
//                .sorted(((company1, company2) -> {
//        return company2.getName().compareTo(company1.getName());
//    }))
//            .limit(3).forEach(System.out::println);
//
//    //3a
//        companies.stream()
//                .sorted(((company1, company2) -> company2.getName().compareTo(company1.getName())))
//            .limit(3).forEach(System.out::println);
//
//    //4
//        companies.stream().sorted(comparing(Company::getName)).limit(3).forEach(System.out::println);
//
//    //5
//        companies.stream().sorted().limit(3).forEach(System.out::println);

    @RequiredArgsConstructor
    @Getter
    static class CompanyWithDividend implements Comparable<CompanyWithDividend> {
        private final Company company;
        private final BigDecimal dividend;

        public static CompanyWithDividend fromCompany(Company company) {
            //BigDecimal dividend = company.getDividendYield().multiply(company.getPrice());
            return new CompanyWithDividend(company, company.getDividendYield().multiply(company.getPrice()));
        }

        @Override
        public int compareTo(CompanyWithDividend otherCompany) {
            return this.dividend.compareTo(otherCompany.getDividend());
        }

        @Override
        public String toString() {
            return "CompanyWithDividend{" +
                    "company=" + company.getName() +
                    ", dividend=" + dividend +
                    '}';
        }
    }

}
