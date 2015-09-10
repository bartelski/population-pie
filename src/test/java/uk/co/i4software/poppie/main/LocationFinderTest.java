package uk.co.i4software.poppie.main;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.MockCensus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Copyright DonRiver Inc. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
public class LocationFinderTest {

    private final MockCensus mockCensus = new MockCensus();

    private static List<Location> expanded = new ArrayList<Location>();
    private static List<Location> selected = new ArrayList<Location>();

    private TreeNode locationTree;

    static {

        expanded.add(MockCensus.BATHAVON_NORTH);
        expanded.add(MockCensus.E00072564);
        selected.add(MockCensus.ABBEY);

    }

    @Before
    public void setup() {
        locationTree = new LocationTreeBuilder(mockCensus.fetchLocationHierarchy(), selected, expanded).build();
    }

    @Test
    public void testFindExpandedLocations() throws Exception {

        List<Location> expandedLocations = LocationFinder.findExpandedLocations(locationTree);

        assertEquals(2, expandedLocations.size());

        assertTrue(expandedLocations.contains(MockCensus.BATHAVON_NORTH));
        assertTrue(expandedLocations.contains(MockCensus.E00072564));

    }

    @Test
    public void testFindSelectedLocations() throws Exception {

        List<Location> selectedLocations = LocationFinder.findSelectedLocations(locationTree);

        assertEquals(1, selectedLocations.size());
        assertTrue(selectedLocations.contains(MockCensus.ABBEY));

    }
}