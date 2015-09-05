package uk.co.i4software.poppie.main;

import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
public class LocationFinder {

    static List<Location> findExpandedLocations(TreeNode locationTree) {

        return find(locationTree, new LocationTreeCondition() {
            public boolean is(TreeNode treeNode) {
                return treeNode.isExpanded();
            }
        });
    }

    static List<Location> findSelectedLocations(TreeNode locationTree) {

        return find(locationTree, new LocationTreeCondition() {
            public boolean is(TreeNode treeNode) {
                return treeNode.isSelected();
            }
        });
    }

    private static List<Location> find(TreeNode locationTree, LocationTreeCondition condition) {
        return find(locationTree.getChildren(), condition);
    }

    private static List<Location> find(List<TreeNode> childTreeNodes, LocationTreeCondition locationTreeCondition) {

        List<Location> foundLocations = new ArrayList<Location>();

        for (TreeNode childTreeNode : childTreeNodes) {
            if (locationTreeCondition.is(childTreeNode)) foundLocations.add((Location) childTreeNode.getData());
            foundLocations.addAll(find(childTreeNode.getChildren(), locationTreeCondition));
        }
        return foundLocations;
    }

    public interface LocationTreeCondition {
        boolean is(TreeNode treeNode);
    }
}
