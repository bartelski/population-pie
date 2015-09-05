package uk.co.i4software.poppie.main;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.MockCensus;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static uk.co.i4software.poppie.census.MockCensus.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public class LocationTreeBuilderTest {

    private final MockCensus mockCensus = new MockCensus();
    private TreeNode locationTree;

    @Before
    public void createLocationTree() {
        locationTree = new LocationTreeBuilder(
                mockCensus.fetchLocationHierarchy(), new ArrayList<Location>(), new ArrayList<Location>()).build();
    }

    @Test
    public void testLocationTree() {

        assertEquals(treeNode(ENGLAND_AND_WALES), findNode(0));
        assertEquals(treeNode(BATH_AND_NORTH_EAST_SOMERSET), findNode(0, 0));
        assertEquals(treeNode(ABBEY), findNode(0, 0, 0));
        assertEquals(treeNode(BATHAVON_NORTH), findNode(0, 0, 1));
        assertEquals(treeNode(E00072563), findNode(0, 0, 0, 0));
        assertEquals(treeNode(E00072564), findNode(0, 0, 0, 1));
    }

    private TreeNode treeNode(Location location) {
        return new DefaultTreeNode(location);
    }

    private TreeNode findNode(int... siblings) {

        TreeNode treeNode = locationTree;

        for (int sibling : siblings)
            treeNode = findNode(treeNode, sibling);

        return treeNode;
    }

    private TreeNode findNode(TreeNode treeNode, int sibling) {
        return treeNode.getChildren().get(sibling);
    }

}