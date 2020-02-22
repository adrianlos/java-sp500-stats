package pl.sda.stats;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class Company {
    private String name;
    private Sector sector;
    private BigDecimal price;
    private BigDecimal dividendYield;
    private BigDecimal weeksLow;
    private BigDecimal weeksHigh;
    private long ebitda;

}
