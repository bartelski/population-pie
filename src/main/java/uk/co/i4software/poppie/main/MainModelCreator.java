package uk.co.i4software.poppie.main;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;

import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
class MainModelCreator {

    static final String LOCATION_TREE = "LOCATIONS";

    private final List<Location> rootLocations;

    public MainModelCreator(List<Location> rootLocations) {
        this.rootLocations = rootLocations;
    }

    public MainModel create() {

        TreeNode treeRoot = new DefaultTreeNode(LOCATION_TREE);
        buildLocationTree(rootLocations, treeRoot);

        return new MainModel(treeRoot);
    }

    private void buildLocationTree(List<Location> locations, TreeNode treeNode) {

        for (Location location : locations) {
            TreeNode currentNode = new DefaultTreeNode(location, treeNode);
            buildLocationTree(location.getChildLocations(), currentNode);
        }
    }

}
