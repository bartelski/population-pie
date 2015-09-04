package uk.co.i4software.poppie.main;

import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@FacesComponent(value = "main")
public class MainComponent extends UINamingContainer {

    private static final String ROOT_LOCATIONS_ATTR = "rootLocations";
    private static final String INITIAL_LOCATIONS = "initialLocations";
    private static final String EXPANDED_LOCATIONS = "expandedLocations";
    private static final String FACT_TYPES_ATTR = "factTypes";
    private static final String MAIN_MODEL = "MAIN_MODEL";

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        final List<Location> selectedLocations = initialLocations();
        final List<Location> expandedLocations = expandedLocations();

        final TreeNode locationTree = new LocationTreeBuilder(rootLocations(), selectedLocations, expandedLocations).build();
        final Map<FactType, FactModel> factModel = new FactModelsBuilder(selectedLocations, factTypes()).build();

        setMainModel(new MainModel(locationTree, factModel, selectedLocations));
    }

    @SuppressWarnings("unchecked")
    private List<Location> rootLocations() {
        return (List<Location>) getAttributes().get(ROOT_LOCATIONS_ATTR);
    }

    @SuppressWarnings("unchecked")
    private List<Location> initialLocations() {
        return (List<Location>) getAttributes().get(INITIAL_LOCATIONS);
    }

    @SuppressWarnings("unchecked")
    private List<Location> expandedLocations() {
        return (List<Location>) getAttributes().get(EXPANDED_LOCATIONS);
    }

    public void onLocationsSelect() {

        final MainModel mainModel = getMainModel();
        final List<Location> selectedLocations = toLocations(mainModel.getSelectedTreeNodes());

        mainModel.setSelectedLocations(selectedLocations);
        mainModel.setFactModels(new FactModelsBuilder(selectedLocations, factTypes()).build());
    }

    private MainModel getMainModel() {
        return (MainModel) getStateHelper().get(MAIN_MODEL);
    }

    private void setMainModel(MainModel mainModel) {
        getStateHelper().put(MAIN_MODEL, mainModel);
    }

    private List<Location> toLocations(TreeNode[] selectedTreeNodes) {

        List<Location> selectedLocations = new ArrayList<Location>();

        for (TreeNode selectedTreeNode : selectedTreeNodes)
            selectedLocations.add((Location) selectedTreeNode.getData());

        return selectedLocations;
    }

    public TreeNode getLocationTree() {
        return getMainModel().getLocationTree();
    }

    public TreeNode[] getSelectedTreeNodes() {
        return getMainModel().getSelectedTreeNodes();
    }

    public void setSelectedTreeNodes(TreeNode[] selectedTreeNodes) {
        getMainModel().setSelectedTreeNodes(selectedTreeNodes);
    }

    private FactType[] factTypes() {
        return (FactType[]) getAttributes().get(FACT_TYPES_ATTR);
    }

    public PieChartModel pieChartModelFor(FactType factType) {
        return getMainModel().pieChartModelFor(factType);
    }

    public HorizontalBarChartModel barChartModelFor(FactType factType) {
        return getMainModel().barChartModelFor(factType);
    }

    public Number valueOf(Location location, FactType factType, FactName factName) {
        return getMainModel().valueOf(location, factType, factName);
    }

    public Number percentageOf(Location location, FactType factType, FactName factName) {
        return getMainModel().percentageOf(location, factType, factName);
    }

    public List<Location> getSelectedLocations() {
        return getMainModel().getSelectedLocations();
    }


    public boolean isPieChartRendered() {
        return areChartsRendered();
    }

    public boolean isBarChartRendered() {
        return areChartsRendered();
    }

    private boolean areChartsRendered() {
        return ! (getSelectedLocations() == null || getSelectedLocations().size() == 0);
    }

    public void sortLocationTreeByValue(FactName factName) {
    }


}
