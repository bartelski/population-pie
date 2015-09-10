package uk.co.i4software.poppie.census;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@EqualsAndHashCode(of = {"locationName"})
public class Location implements Serializable {

    private static final long serialVersionUID = 1947698132369542867L;

    @Getter
    private final String locationName;
    @Getter
    private final String displayName;
    @Getter
    private final List<Fact> facts;

    private final Map<FactName, Fact> factMap;
    private final Map<FactType, Long> factTypeTotals;
    private final Map<FactName, Number> factPercentages;

    @Getter
    @Setter
    private List<Location> childLocations = new ArrayList<Location>();

    public Location(String locationName, List<Fact> facts) {
        this(locationName, locationName, facts);
    }

    public Location(String locationName, String displayName, List<Fact> facts) {
        this.locationName = locationName;
        this.displayName = displayName;
        this.facts = facts;

        this.factMap = indexFacts();
        this.factTypeTotals = indexFactTotals();
        this.factPercentages = indexFactPercentages();
    }

    private Map<FactName, Fact> indexFacts() {

        Map<FactName, Fact> factMap = new HashMap<FactName, Fact>();

        for (Fact fact : facts)
            factMap.put(fact.getFactName(), fact);

        return factMap;
    }

    private Map<FactType, Long> indexFactTotals() {

        Map<FactType, Long> factTypeTotals = new HashMap<FactType, Long>();

        for (FactType factType : FactType.values())
            factTypeTotals.put(factType, calculateTotalFor(factType));

        return factTypeTotals;
    }

    private Map<FactName, Number> indexFactPercentages() {

        Map<FactName, Number> factPercentages = new HashMap<FactName, Number>();

        for (FactType factType : FactType.values())
            factPercentages.putAll(calculateFactPercentagesFor(factType));

        return factPercentages;
    }

    private long calculateTotalFor(FactType factType) {
        long factTypeTotal = 0;

        for (FactName factname : factType.getFactNames()) {

            if (factMap.containsKey(factname))
                factTypeTotal += factMap.get(factname).getFactValue();
        }
        return factTypeTotal;
    }

    private Map<FactName, Number> calculateFactPercentagesFor(FactType factType) {

        Map<FactName, Number> factPercentages = new HashMap<FactName, Number>();

        for (FactName factName : factType.getFactNames())
            factPercentages.put(factName, percentageOf(factName, factType));
        return factPercentages;
    }

    private Number percentageOf(FactName factName, FactType factType) {

        final double factTypeTotal = factTotalOf(factType);
        return factTypeTotal == 0 ? 0 : formatAsPercentage(factValueOf(factName) * 100 / factTypeTotal);
    }

    private Number formatAsPercentage(double percentage) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(percentage));
    }

    private Long factTotalOf(FactType factType) {
        return factTypeTotals.get(factType);
    }

    public Long factValueOf(FactName factName) {
        return hasFact(factName) ? factMap.get(factName).getFactValue() : 0;
    }

    private boolean hasFact(FactName factName) {
        return factMap.containsKey(factName);
    }

    public String toString() {
        return displayName;
    }

    public List<Fact> factsFor(FactName... factNames) {

        List<Fact> facts = new ArrayList<Fact>();

        for (FactName factName : factNames)
            if (hasFact(factName))
                facts.add(factMap.get(factName));

        return facts;
    }

    public Number factPercentageOf(FactName factName) {
        return hasFact(factName) ? factPercentages.get(factName) : 0;
    }
}
