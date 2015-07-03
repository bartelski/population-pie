package uk.co.i4software.poppie.population;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.Fact;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.Location;

import java.text.DecimalFormat;
import java.util.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
class PopulationModelCreator {

    private static final boolean PIE_SHOW_DATA_LABELS = true;
    private static final String PIE_LEGEND_POSITION = "n";
    private static final String BAR_LEGEND_POSITION = "sw";
    private static final boolean BAR_STACKED = false;
    private static final String BAR_DATATIP_FORMAT = "%1$d";
    private static final boolean BAR_ANIMATED = true;
    public static final int PIE_DIAMETER = 325;

    private final Location[] locations;
    private final FactName[] factNames;


    public PopulationModelCreator(Location[] locations, FactName[] factNames) {
        this.locations = locations;
        this.factNames = factNames;
    }

    public PopulationModel create() {

        final Map<FactName, Long> factTotals = indexFactTotals();
        final Map<Location, Long> locationTotals = indexLocationTotals();

        final Map<Location, Map<FactName, String>> locationPercentages = indexLocationPercentages(locationTotals);

        return new PopulationModel(pieChartModel(factTotals), barChartModel(), locationPercentages);
    }

    private Map<FactName, Long> indexFactTotals() {

        Map<FactName, Long> factTotals = new HashMap<FactName, Long>();

        for (Location location : locations)
            calculateFactTotals(location, factTotals);

        return factTotals;
    }

    private void calculateFactTotals(Location location, Map<FactName, Long> factTotals) {

        for (Fact fact : location.factsFor(factNames))
            factTotals.put(fact.getFactName(), totalFor(fact, factTotals));
    }

    private long totalFor(Fact fact, Map<FactName, Long> factTotals) {
        return runningTotalFor(fact.getFactName(), factTotals) + fact.getFactValue();
    }

    private long runningTotalFor(FactName factName, Map<FactName, Long> factTotals) {
        return factTotals.containsKey(factName) ? factTotals.get(factName) : 0;
    }

    private Map<Location, Long> indexLocationTotals() {

        Map<Location, Long> locationTotals = new HashMap<Location, Long>();

        for (Location location : locations)
            locationTotals.put(location, totalFor(location));

        return locationTotals;
    }

    private long totalFor(Location location) {

        long locationTotal = 0;

        for (Fact fact : location.factsFor(factNames))
            locationTotal += fact.getFactValue();

        return locationTotal;
    }

    private Map<Location, Map<FactName, String>> indexLocationPercentages(Map<Location, Long> locationTotals) {

        Map<Location, Map<FactName, String>> locationPercentages = new HashMap<Location, Map<FactName, String>>();

        for (Location location : locations)
            locationPercentages.put(location, factPercentagesFor(location, locationTotals));

        return locationPercentages;
    }

    private Map<FactName, String> factPercentagesFor(Location location, Map<Location, Long> locationTotals) {

        Map<FactName, String> factPercentages = new HashMap<FactName, String>();

        for (FactName factName : factNames)
            factPercentages.put(factName, percentageOf(location.factValueOf(factName), locationTotals.get(location)));

        return factPercentages;
    }

    private String percentageOf(Long factValue, Long factTotal) {
        return factValue == null ? null : formatAtPercentage(factValue.doubleValue() * 100 / factTotal.doubleValue());
    }

    private String formatAtPercentage(double percentage) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(percentage);
    }

    private PieChartModel pieChartModel(Map<FactName, Long> factTotals) {

        PieChartModel pieChartModel = new PieChartModel(pieData(factTotals));
        configurePie(pieChartModel);

        return pieChartModel;
    }

    private Map<String, Number> pieData(Map<FactName, Long> factTotals) {

        Map<String, Number> pieData = new LinkedHashMap<String, Number>();

        for (FactName factName : factNames)
            pieData.put(factName.getDisplayName(), factTotals.get(factName));

        return pieData;
    }

    private void configurePie(PieChartModel pieChartModel) {
        pieChartModel.setShowDataLabels(PIE_SHOW_DATA_LABELS);
        pieChartModel.setLegendPosition(PIE_LEGEND_POSITION);
        pieChartModel.setDiameter(PIE_DIAMETER);
        pieChartModel.setLegendCols(legendCols(pieChartModel));
    }

    private int legendCols(PieChartModel pieChartModel) {
        return Math.min((int) Math.ceil(((double) pieChartModel.getData().size()) / 2), 3);
    }

    private HorizontalBarChartModel barChartModel() {

        HorizontalBarChartModel barChartModel = new HorizontalBarChartModel();

        for (FactName factName : factNames)
            barChartModel.addSeries(createChartSeriesFor(factName));

        configureBarChart(barChartModel);

        return barChartModel;
    }

    private ChartSeries createChartSeriesFor(FactName factName) {

        ChartSeries chartSeries = new ChartSeries(factName.getDisplayName());
        chartSeries.setData(locationDataFor(factName));
        return chartSeries;
    }

    private Map<Object, Number> locationDataFor(FactName factName) {

        Map<Object, Number> locationData = new HashMap<Object, Number>();

        for (Location location : locations)
            locationData.put(location.getLocationName(), location.factValueOf(factName));

        return locationData;
    }

    private void configureBarChart(HorizontalBarChartModel barChartModel) {
        barChartModel.setLegendPosition(BAR_LEGEND_POSITION);
        barChartModel.setStacked(BAR_STACKED);
        barChartModel.setDatatipFormat(BAR_DATATIP_FORMAT);
        barChartModel.setAnimate(BAR_ANIMATED);
    }

}
