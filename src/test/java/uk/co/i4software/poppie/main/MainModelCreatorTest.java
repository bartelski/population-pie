package uk.co.i4software.poppie.main;

import org.junit.Test;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.LocationType;
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

    private final MockCensus mockCensus = new MockCensus();
    private static final TreeNode TREE_ROOT;

    private MainModel mainModel;

    static {
        TREE_ROOT = new DefaultTreeNode(MainModelCreator.LOCATION_TREE);
    }

    @Test
    public void testCreate() throws Exception {

        mainModel = new MainModelCreator(mockCensus.fetchLocationTypes()).create();

        assertEquals(TREE_ROOT, mainModel.getLocationTree());
        assertArrayEquals(FactType.values(), mainModel.getFactTypes());

        testLocationTypesAreCreated();
        testLocationsAreCreated();
    }

    private void testLocationTypesAreCreated() {
        assertEquals(treeNode(COUNTRY), findNode(0));
        assertEquals(treeNode(LOCAL_AUTHORITY), findNode(1));
        assertEquals(treeNode(WARD), findNode(2));
        assertEquals(treeNode(LOWER_SUPER_OUTPUT_AREA), findNode(3));
        assertEquals(treeNode(OUTPUT_AREA), findNode(4));
    }

    private void testLocationsAreCreated() {
        assertEquals(treeNode(ENGLAND_AND_WALES), findNode(0, 0));
        assertEquals(treeNode(BATH_AND_NORTH_EAST_SOMERSET), findNode(1, 0));
        assertEquals(treeNode(ABBEY), findNode(2, 0));
        assertEquals(treeNode(BATHAVON_NORTH), findNode(2, 1));
        assertEquals(treeNode(E01014370), findNode(3, 0));
        assertEquals(treeNode(E00072588), findNode(4, 0));
    }

    private TreeNode treeNode(LocationType locationType) {
        return new DefaultTreeNode(locationType);
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