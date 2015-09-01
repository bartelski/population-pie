package uk.co.i4software.poppie.main;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
public class MainModel implements Serializable {

    private static final long serialVersionUID = 3353094740374788487L;

    @Getter private final TreeNode locationTree;
    @Getter private final FactType[] factTypes;
    @Getter @Setter private TreeNode[] selectedTreeNodes;

    @Getter private Location[] selectedLocations;
    private Map<FactType, FactModel> factModels;

    public MainModel(TreeNode locationTree, FactType[] factTypes, Location[] initialLocations) {
        this.locationTree = locationTree;
        this.factTypes = factTypes;

        updateModelForSelectedLocations(initialLocations);
    }


    public void updateModelForSelectedLocations(Location[] selectedLocations) {
        this.selectedLocations = selectedLocations;
        this.factModels = createFactModelsFor(selectedLocations);
    }

    private Map<FactType, FactModel> createFactModelsFor(Location[] selectedLocations) {

        Map<FactType, FactModel> factModels = new HashMap<FactType, FactModel>();

        for (FactType factType : factTypes)
            factModels.put(factType, new FactModelCreator(selectedLocations, factType.getFactNames()).create());

        return factModels;
    }



    public PieChartModel pieChartModelFor(FactType factType) {
        return factModels.get(factType).getPieChartModel();
    }

    public HorizontalBarChartModel barChartModelFor(FactType factType) {
        return factModels.get(factType).getBarChartModel();
    }

    public Number valueOf(Location location, FactType factType, FactName factName) {
        return factModels.get(factType).valueOf(location, factName);
    }

    public Number percentageOf(Location location, FactType factType, FactName factName) {
        return factModels.get(factType).percentageOf(location, factName);
    }

    static class FactModel implements Serializable {

        private static final long serialVersionUID = -736078452584137611L;

        @Getter private final PieChartModel pieChartModel;
        @Getter private final HorizontalBarChartModel barChartModel;

        private final Map<Location, Map<FactName, Number>> locationPercentages;

        public FactModel(PieChartModel pieChartModel, HorizontalBarChartModel barChartModel,
                         Map<Location, Map<FactName, Number>> locationPercentages) {

            this.pieChartModel = pieChartModel;
            this.barChartModel = barChartModel;
            this.locationPercentages = locationPercentages;
        }

        public Number valueOf(Location location, FactName factName) {
            return location.factValueOf(factName);
        }

        public Number percentageOf(Location location, FactName factName) {
            return locationPercentages.get(location).get(factName);
        }
    }

}
