package pl.sda.stats.company;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum Sector {
    IT("Information Technology"),
    FINANCIALS("Financials"),
    TELECOM("Telecommunication Services"),
    UNKNOWN("Unknown");

    private String name;

    Sector(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Sector fromString(String stringName) {
        return Stream.of(values())
                .filter(sector -> sector.name.equals(stringName))
                .findAny()
                .orElse(Sector.UNKNOWN);
    }
}
