package uk.co.i4software.poppie.main;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.data.SortEvent;
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

    private static final String LOCATION_HIERARCHY_ATTR = "locationHierarchy";
    private static final String INITIAL_LOCATIONS = "initialLocations";
    private static final String EXPANDED_LOCATIONS = "expandedLocations";
    private static final String FACT_TYPES_ATTR = "factTypes";
    private static final String MAIN_MODEL = "MAIN_MODEL";

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        final List<Location> selectedLocations = initialLocations();
        final List<Location> expandedLocations = expandedLocations();
        final List<Location> locationHierarchy = locationHierarchy();

        final TreeNode locationTree = new LocationTreeBuilder(locationHierarchy, selectedLocations,
                expandedLocations).build();

        final Map<FactType, FactModel> factModel = new FactModelsBuilder(selectedLocations, factTypes()).build();

        mainModel(new MainModel(locationTree, factModel, selectedLocations));
    }

    @SuppressWarnings("unchecked")
    private List<Location> initialLocations() {
        return (List<Location>) getAttributes().get(INITIAL_LOCATIONS);
    }

    @SuppressWarnings("unchecked")
    private List<Location> expandedLocations() {
        return (List<Location>) getAttributes().get(EXPANDED_LOCATIONS);
    }

    @SuppressWarnings("unchecked")
    private List<Location> locationHierarchy() {
        return (List<Location>) getAttributes().get(LOCATION_HIERARCHY_ATTR);
    }

    private FactType[] factTypes() {
        return (FactType[]) getAttributes().get(FACT_TYPES_ATTR);
    }

    private void mainModel(MainModel mainModel) {
        getStateHelper().put(MAIN_MODEL, mainModel);
    }

    public void onLocationsSelect() {

        final MainModel mainModel = mainModel();
        final List<Location> selectedLocations = toLocations(mainModel.getSelectedTreeNodes());

        mainModel.setSelectedLocations(selectedLocations);
        mainModel.setFactModels(new FactModelsBuilder(selectedLocations, factTypes()).build());
    }

    private MainModel mainModel() {
        return (MainModel) getStateHelper().get(MAIN_MODEL);
    }

    private List<Location> toLocations(TreeNode[] selectedTreeNodes) {

        List<Location> selectedLocations = new ArrayList<Location>();

        for (TreeNode selectedTreeNode : selectedTreeNodes)
            selectedLocations.add((Location) selectedTreeNode.getData());

        return selectedLocations;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        event.getTreeNode().setExpanded(true);
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        event.getTreeNode().setExpanded(false);
    }

    public TreeNode getLocationTree() {
        return mainModel().getLocationTree();
    }

    public TreeNode[] getSelectedTreeNodes() {
        return mainModel().getSelectedTreeNodes();
    }

    public void setSelectedTreeNodes(TreeNode[] selectedTreeNodes) {
        mainModel().setSelectedTreeNodes(selectedTreeNodes);
    }

    public PieChartModel pieChartModelFor(FactType factType) {
        return mainModel().pieChartModelFor(factType);
    }

    public HorizontalBarChartModel barChartModelFor(FactType factType) {
        return mainModel().barChartModelFor(factType);
    }

    public Number valueOf(Location location, FactType factType, FactName factName) {
        return mainModel().valueOf(location, factType, factName);
    }

    public Number percentageOf(Location location, FactType factType, FactName factName) {
        return mainModel().percentageOf(location, factType, factName);
    }

    public boolean isPieChartRendered() {
        return areChartsRendered();
    }

    private boolean areChartsRendered() {
        return !(getSelectedLocations() == null || getSelectedLocations().size() == 0);
    }

    public List<Location> getSelectedLocations() {
        return mainModel().getSelectedLocations();
    }

    public boolean isBarChartRendered() {
        return areChartsRendered();
    }

    public void sortLocationTreeByValue(FactName factName) {

        final MainModel mainModel = mainModel();
        final TreeNode locationTree = mainModel.getLocationTree();

        final List<Location> selected = LocationFinder.findSelectedLocations(locationTree);
        final List<Location> expanded = LocationFinder.findExpandedLocations(locationTree);
        final List<Location> locationHierarchy = locationHierarchy();

        LocationSorter.sort(locationHierarchy, factName, mainModel.getLastSortBy());
        mainModel.setLocationTree(new LocationTreeBuilder(locationHierarchy, selected, expanded).build());
        mainModel.setLastSortBy(factName);

    }

    public void sort(SortEvent event) {
    }

}
