package uk.co.i4software.poppie.main;

import org.junit.Before;
import org.junit.Test;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.MockCensus;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Copyright DonRiver Inc. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
public class LocationSorterTest {

    private List<Location> locationHierarchy;


    private static SortDefinition locationsDescending;
    private static SortDefinition locationsAscending;

    static {

        locationsDescending = new SortDefinition(SortDefinition.SortType.LOCATION, null, false);
        locationsAscending = new SortDefinition(SortDefinition.SortType.LOCATION, null, true);
    }

    @Before
    public void setup() {
        locationHierarchy = new MockCensus().fetchLocationHierarchy();
    }


    @Test
    public void locationsDescendingSort() throws Exception {

        LocationSorter.sort(locationHierarchy, locationsDescending);

        assertEquals(findLocation(), MockCensus.ENGLAND_AND_WALES);
        assertEquals(findLocation(0), MockCensus.BATH_AND_NORTH_EAST_SOMERSET);
        assertEquals(findLocation(0, 0), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 1), MockCensus.ABBEY);
        assertEquals(findLocation(0, 1, 0), MockCensus.E00072564);
        assertEquals(findLocation(0, 1, 1), MockCensus.E00072563);

    }

    @Test
    public void locationsAscendingSort() throws Exception {

        LocationSorter.sort(locationHierarchy, locationsAscending);

        assertEquals(findLocation(), MockCensus.ENGLAND_AND_WALES);
        assertEquals(findLocation(0), MockCensus.BATH_AND_NORTH_EAST_SOMERSET);
        assertEquals(findLocation(0, 0), MockCensus.ABBEY);
        assertEquals(findLocation(0, 1), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 0, 0), MockCensus.E00072563);
        assertEquals(findLocation(0, 0, 1), MockCensus.E00072564);
    }

    private Location findLocation(int... siblings) {

        Location location = locationHierarchy.get(0);

        for (int sibling : siblings)
            location = findLocation(location, sibling);

        return location;
    }

    private Location findLocation(Location location, int sibling) {
        return location.getChildLocations().get(sibling);
    }
}