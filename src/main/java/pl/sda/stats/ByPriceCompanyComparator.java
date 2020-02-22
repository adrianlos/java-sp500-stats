package pl.sda.stats;

import java.util.Comparator;

public class ByPriceCompanyComparator implements Comparator<Company> {

    @Override
    public int compare(Company o1, Company o2) {
        return o2.getPrice().compareTo(o1.getPrice());
    }
}
