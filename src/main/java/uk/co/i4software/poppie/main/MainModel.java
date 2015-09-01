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
    @Getter @Setter private TreeNode[] selectedTreeNodes;

    @Getter private Location[] selectedLocations;
    private Map<FactType, FactModel> factModels;


    public MainModel(TreeNode locationTree) {
        this.locationTree = locationTree;
    }

    public FactType[] getFactTypes() {
        return FactType.values();
    }

    public void setSelectedLocationsAndFactModels(Location[] locations, Map<FactType, FactModel> factModels) {
        this.selectedLocations = locations;
        this.factModels = factModels;
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

}
