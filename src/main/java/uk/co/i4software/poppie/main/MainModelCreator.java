package uk.co.i4software.poppie.main;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.LocationType;

import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
class MainModelCreator {

    static final String LOCATION_TREE = "LOCATIONS";

    private final List<LocationType> locationTypes;

    public MainModelCreator(List<LocationType> locationTypes) {
        this.locationTypes = locationTypes;
    }

    public MainModel create() {

        TreeNode treeRoot = new DefaultTreeNode(LOCATION_TREE);

        for (LocationType locationType : locationTypes)
            buildLocationTree(locationType, treeRoot);

        return new MainModel(treeRoot);
    }

    private void buildLocationTree(LocationType locationType, TreeNode treeRoot) {

        TreeNode locationTypeTreeNode = new DefaultTreeNode(locationType, treeRoot);
        locationTypeTreeNode.setSelectable(false);

        createLocationTreeNodes(locationTypeTreeNode, locationType.getLocations());
    }

    private void createLocationTreeNodes(TreeNode parent, List<Location> locations) {
        for (Location location : locations)
            new DefaultTreeNode(location, parent);
    }
}
