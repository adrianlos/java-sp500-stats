package pl.sda.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.sda.stats.company.Company;
import pl.sda.stats.company.Sector;
import pl.sda.stats.company.comparators.ByDividendCompanyComparator;
import pl.sda.stats.company.comparators.ByPriceCompanyComparator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@AllArgsConstructor
class SP500Statistics {
    private final List<Company> companies;

    /**
     * Finds companies with highest share price, limits the findings to 'i' entries.
     * Companies are searched only among known sectors.
     *
     * @param i number of highest priced companies to find
     * @return 'i' highest priced companies
     */
    List<Company> companiesWithHighestPrice(int i) {
        return companies.stream()
                .filter(company -> company.getSector() != Sector.UNKNOWN)
                .sorted(new ByPriceCompanyComparator())
                .limit(i).collect(Collectors.toList());
    }

    /**
     * Finds companies with highest dividend yield, limits the findings to 'i' entries.
     * Companies are searched only among known sectors.
     *
     * @param i number of companies to find
     * @return 'i' companies with highest dividend yield
     */
    List<Company> companiesWithHighestDividendYield(int i) {
        return companies.stream()
                .filter(company -> company.getSector() != Sector.UNKNOWN)
                .sorted(new ByDividendCompanyComparator())
                .limit(i).collect(Collectors.toList());
    }

    /**
     * Finds companies with highest dividend amount per share,
     * limits the findings to 'i' entries.
     * Companies are searched only among known sectors.
     *
     * @param i number of companies to find
     * @return 'i' companies with respective dividend amount
     */
    List<CompanyWithDividend> companiesWithHighestDividend(int i) {
        return companies.stream()
                .filter(company -> company.getSector() != Sector.UNKNOWN)
                .map(CompanyWithDividend::fromCompany)
                .sorted(Comparator.reverseOrder())
                .limit(i)
                .collect(Collectors.toList());
    }

    /**
     * Returns highest earning company in each sector.
     * Uses iterating over sectors approach.
     *
     * @return map of sector -> highest earning company
     */
    public Map<Sector, Company> companyBySector() {
        Map<Sector, Company> mapSectors = new HashMap<>();
        for (Sector sector : Sector.values()) {
            Optional<Company> bestCompany = companyWithHighestEbitdaInSector(sector);
            bestCompany.ifPresent(company -> mapSectors.put(sector, company));
        }
        return mapSectors;
    }

    /**
     * Returns highest earning company in each sector.
     * Uses approach of grouping to map of (sector -> list of companies).
     * Then, the map is rewritten into other map with only highest earning company.
     *
     * @return map of (sector -> highest earning company)
     */
    public Map<Sector, Company> companyBySector2() {
        Map<Sector, List<Company>> sectorListMap = companies.stream()
                .filter(company -> company.getSector() != Sector.UNKNOWN)
                .collect(Collectors.groupingBy(Company::getSector));

        Map<Sector, Company> result = new HashMap<>();
        sectorListMap.entrySet()
                .forEach(entry -> {
                    List<Company> companiesInSector = entry.getValue();
                    companyWithHighestEbitdaInList(companiesInSector).ifPresent(company -> result.put(entry.getKey(), company));
                });

        // alternative notation below
        // here, the Entry<Sector, List<Company>> is unwrapped in lambda parameters
        //
        // sectorListMap.forEach((key, companiesInSector) -> companyWithHighestEbitdaInList(companiesInSector).ifPresent(company -> result.put(key, company)));

        return result;
    }

    /**
     * Returns highest earning company in each sector.
     * Uses stream-based approach.
     *
     * @return map of (sector -> highest earning company wrapped in optional)
     */
    public Map<Sector, Optional<Company>> companyBySector3() {
        return companies.stream()
                .filter(company -> company.getSector() != Sector.UNKNOWN)
                .collect(Collectors.groupingBy( // collect companies by grouping
                        Company::getSector, //group by sector
                        Collectors.maxBy( // choose 'max' company in each sector
                                Comparator.comparing(Company::getEbitda) // use 'ebitda' to compare companies
                        )
                ));
    }

    /**
     * For given list returns company with highest EBITDA.
     *
     * @param companyList list to search in
     * @return Optional wrapped around company with highest earnings,
     * Optional.empty() if given list is empty
     */
    private Optional<Company> companyWithHighestEbitdaInList(List<Company> companyList) {
        return companyList.stream()
                .max(comparing(Company::getEbitda));
    }

    /**
     * For given sector returns a company with highest EBITDA from all companies.
     *
     * @param sector sector in which we're searching
     * @return Optional wrapped around company with highest earnings in given
     * sector, Optional.empty() if there are no companies in this sector
     */
    private Optional<Company> companyWithHighestEbitdaInSector(Sector sector) {
        List<Company> companiesInSector = companies.stream()
                .filter(company -> company.getSector().equals(sector))
                .collect(Collectors.toList());
        return companyWithHighestEbitdaInList(companiesInSector);
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
