package pl.sda.stats.importing;

import pl.sda.stats.company.Company;
import pl.sda.stats.company.Sector;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

class CompanyMapper {
    private final Logger logger = Logger.getLogger(CompanyMapper.class.getName());


    Optional<Company> fromLine(String[] fields) {
        try {
            return Optional.of(
                    Company.builder()
                            .name(fields[1])
                            .sector(Sector.fromString(fields[2]))
                            .price(new BigDecimal(fields[3]))
                            .dividendYield(new BigDecimal(fields[5]))
                            .weeksHigh(new BigDecimal(fields[7]))
                            .weeksLow(new BigDecimal(fields[8]))
                            .ebitda(Long.parseLong(fields[10]))
                            .build()
            );
        } catch (NumberFormatException number) {
            logger.log(Level.INFO, "Company with name " + fields[1] + " has wrong data.");
        }
        return Optional.empty();
    }

}
