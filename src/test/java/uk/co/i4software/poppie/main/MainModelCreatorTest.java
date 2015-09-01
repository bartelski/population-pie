package uk.co.i4software.poppie.main;

import org.junit.Test;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.MockCensus;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static uk.co.i4software.poppie.census.MockCensus.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public class MainModelCreatorTest {

    private static final TreeNode TREE_ROOT;

    static {
        TREE_ROOT = new DefaultTreeNode(MainModelCreator.LOCATION_TREE);
    }

    private final MockCensus mockCensus = new MockCensus();
    private MainModel mainModel;

    @Test
    public void testCreate() throws Exception {

        mainModel = new MainModelCreator(mockCensus.fetchRootLocations(), mockCensus.fetchFactTypes()).create();

        assertEquals(TREE_ROOT, mainModel.getLocationTree());
        assertArrayEquals(FactType.values(), mainModel.getFactTypes());

        testLocationsAreCreated();
    }

    private void testLocationsAreCreated() {
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
        TreeNode treeNode = mainModel.getLocationTree();

        for (int sibling : siblings)
            treeNode = findNode(treeNode, sibling);

        return treeNode;
    }

    private TreeNode findNode(TreeNode treeNode, int sibling) {
        return treeNode.getChildren().get(sibling);
    }

}