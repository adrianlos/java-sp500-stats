package pl.sda.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CompanyWithDividend implements Comparable<CompanyWithDividend> {
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
