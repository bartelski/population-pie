package uk.co.i4software.poppie.main;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.FactType;
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
    private final FactType[] factTypes;

    public MainModelCreator(List<Location> rootLocations, FactType[] factTypes) {
        this.rootLocations = rootLocations;
        this.factTypes = factTypes;
    }

    public MainModel create() {

        TreeNode treeRoot = new DefaultTreeNode(LOCATION_TREE);
        buildLocationTree(rootLocations, treeRoot);

        return new MainModel(treeRoot, factTypes);
    }

    private void buildLocationTree(List<Location> locations, TreeNode treeNode) {
        for (Location location : locations) {
            TreeNode currentNode = new DefaultTreeNode(location, treeNode);
            if (currentNode.getRowKey().length() <= 3) currentNode.setExpanded(true);
            buildLocationTree(location.getChildLocations(), currentNode);
        }
    }

}
