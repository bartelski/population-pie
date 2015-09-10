package uk.co.i4software.poppie.main;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.Fact;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
class FactModelsBuilder {

    private final List<Location> selectedLocations;
    private final FactType[] factTypes;

    public FactModelsBuilder(List<Location> selectedLocations, FactType[] factTypes) {
        this.selectedLocations = selectedLocations;
        this.factTypes = factTypes;
    }

    public Map<FactType, FactModel> build() {

        Map<FactType, FactModel> factModel = new HashMap<FactType, FactModel>();

        for (FactType factType : factTypes) {
            factModel.put(factType, new FactModelBuilder(selectedLocations, factType.getFactNames()).build());
        }

        return factModel;
    }

    private static class FactModelBuilder {

        private static final boolean PIE_SHOW_DATA_LABELS = true;
        private static final String PIE_LEGEND_POSITION = "e";
        private static final String BAR_LEGEND_POSITION = "s";
        private static final boolean BAR_STACKED = false;
        private static final String BAR_DATATIP_FORMAT = "%1$d";
        private static final boolean BAR_ANIMATED = true;
        private static final int PIE_DIAMETER = 325;
        private static final int PIE_LEGEND_COLS = 1;
        private static final int BAR_LEGEND_ROWS = 1;

        private final List<Location> locations;
        private final FactName[] factNames;

        private Map<FactName, Long> factTotals;
        private Map<Location, Map<FactName, Number>> locationPercentages;


        public FactModelBuilder(List<Location> locations, FactName[] factNames) {
            this.locations = locations;
            this.factNames = factNames;
        }

        public FactModel build() {

            indexFactTotals();

            indexLocationPercentages();

            return new FactModel(pieChartModel(), barChartModel(locationPercentages));
        }

        private void indexFactTotals() {

            factTotals = new HashMap<FactName, Long>();

            for (Location location : locations)
                calculateFactTotals(location);
        }

        private void calculateFactTotals(Location location) {

            for (Fact fact : location.factsFor(factNames))
                factTotals.put(fact.getFactName(), totalFor(fact, factTotals));
        }

        private long totalFor(Fact fact, Map<FactName, Long> factTotals) {
            return runningTotalFor(fact.getFactName(), factTotals) + fact.getFactValue();
        }

        private long runningTotalFor(FactName factName, Map<FactName, Long> factTotals) {
            return factTotals.containsKey(factName) ? factTotals.get(factName) : 0;
        }

        private void indexLocationPercentages() {

            locationPercentages = new HashMap<Location, Map<FactName, Number>>();

            for (Location location : locations)
                locationPercentages.put(location, factPercentagesFor(location));
        }

        private Map<FactName, Number> factPercentagesFor(Location location) {

            Map<FactName, Number> factPercentages = new HashMap<FactName, Number>();

            for (FactName factName : factNames)
                factPercentages.put(factName, location.factPercentageOf(factName));

            return factPercentages;
        }


        private PieChartModel pieChartModel() {

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
            pieChartModel.setLegendCols(PIE_LEGEND_COLS);
        }

        private HorizontalBarChartModel barChartModel(Map<Location, Map<FactName, Number>> percentages) {

            HorizontalBarChartModel barChartModel = new HorizontalBarChartModel();

            for (FactName factName : factNames)
                barChartModel.addSeries(createChartSeriesFor(factName, percentages));

            configureBarChart(barChartModel);
            return barChartModel;
        }

        private ChartSeries createChartSeriesFor(FactName factName, Map<Location, Map<FactName, Number>> percentages) {

            ChartSeries chartSeries = new ChartSeries(factName.getDisplayName());
            chartSeries.setData(locationDataFor(factName, percentages));
            return chartSeries;
        }

        private Map<Object, Number> locationDataFor(FactName factName, Map<Location, Map<FactName, Number>> percentages) {

            Map<Object, Number> locationData = new HashMap<Object, Number>();

            for (Location location : locations)
                locationData.put(location.getLocationName(), percentages.get(location).get(factName));

            return locationData;
        }

        private void configureBarChart(HorizontalBarChartModel barChartModel) {
            barChartModel.setLegendPosition(BAR_LEGEND_POSITION);
            barChartModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
            barChartModel.setStacked(BAR_STACKED);
            barChartModel.setDatatipFormat(BAR_DATATIP_FORMAT);
            barChartModel.setAnimate(BAR_ANIMATED);
            barChartModel.setLegendRows(BAR_LEGEND_ROWS);
        }

    }
}
