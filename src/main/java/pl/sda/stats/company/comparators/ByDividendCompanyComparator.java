package pl.sda.stats.company.comparators;

import pl.sda.stats.company.Company;

import java.util.Comparator;

public class ByDividendCompanyComparator implements Comparator<Company> {

    @Override
    public int compare(Company o1, Company o2) {
        return o2.getDividendYield().compareTo(o1.getDividendYield());
    }
}
