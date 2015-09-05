package uk.co.i4software.poppie.main;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;

import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
class LocationTreeBuilder {

    private static final String LOCATION_TREE = "LOCATIONS";

    private final List<Location> locationHierarchy;
    private final List<Location> selectedLocations;
    private final List<Location> expandedLocations;

    public LocationTreeBuilder(
            List<Location> locationHierarchy,
            List<Location> selectedLocations,
            List<Location> expandedLocations) {

        this.locationHierarchy = locationHierarchy;
        this.selectedLocations = selectedLocations;
        this.expandedLocations = expandedLocations;
    }

    public TreeNode build() {

        TreeNode treeRoot = new DefaultTreeNode(LOCATION_TREE);
        buildLocationTree(locationHierarchy, treeRoot);

        return treeRoot;
    }

    private void buildLocationTree(List<Location> locations, TreeNode treeNode) {
        for (Location location : locations) {
            buildLocationTree(location, treeNode);
        }
    }

    private void buildLocationTree(Location location, TreeNode treeNode) {

        TreeNode currentNode = new DefaultTreeNode(location, treeNode);

        if (selectedLocations.contains(location)) currentNode.setSelected(true);
        if (expandedLocations.contains(location)) currentNode.setExpanded(true);
        buildLocationTree(location.getChildLocations(), currentNode);
    }
}
