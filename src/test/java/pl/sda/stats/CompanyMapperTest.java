package pl.sda.stats;

import org.junit.jupiter.api.Test;
import pl.sda.stats.company.Company;
import pl.sda.stats.company.Sector;
import pl.sda.stats.importing.CompanyMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyMapperTest {

    @Test
    void shouldMapCompany() {
        //given
        String[] input = "MMM,3M Company,Industrials,222.89,24.31,2.3328617,7.92,259.77,175.49,138721055226,9048000000,4.3902707,11.34,http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=MMM\n".split(",");
        Company expectedCompany = Company.builder()
                .name("3M Company")
                .sector(Sector.UNKNOWN)
                .price(new BigDecimal("222.89"))
                .dividendYield(new BigDecimal("2.3328617"))
                .weeksHigh(new BigDecimal("259.77"))
                .weeksLow(new BigDecimal("175.49"))
                .ebitda(9048000000L)
                .build();

        //when
        Company company = new CompanyMapper().fromLine(input).get();
        //then
        assertEquals(expectedCompany, company);
    }

}