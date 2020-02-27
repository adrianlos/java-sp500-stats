package pl.sda.stats.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company implements Comparable<Company> {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Sector")
    private Sector sector;
    @JsonProperty("Price")
    private BigDecimal price;
    @JsonProperty("Dividend Yield")
    private BigDecimal dividendYield;
    @JsonProperty("52 Week Low")
    private BigDecimal weeksLow;
    @JsonProperty("52 Week High")
    private BigDecimal weeksHigh;
    @JsonProperty("EBITDA")
    private long ebitda;

    @Override
    public int compareTo(Company o) {
        return this.getName().compareTo(o.getName());
    }

}
