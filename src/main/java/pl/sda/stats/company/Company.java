package pl.sda.stats.company;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class Company implements Comparable<Company> {
    private String name;
    private Sector sector;
    private BigDecimal price;
    private BigDecimal dividendYield;
    private BigDecimal weeksLow;
    private BigDecimal weeksHigh;
    private long ebitda;

    @Override
    public int compareTo(Company o) {
        return this.getName().compareTo(o.getName());
    }

}
