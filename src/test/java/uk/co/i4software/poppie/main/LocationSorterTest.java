package uk.co.i4software.poppie.main;

import org.junit.Before;
import org.junit.Test;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.MockCensus;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.co.i4software.poppie.main.SortDefinition.*;

/**
 * Copyright DonRiver Inc. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
public class LocationSorterTest {

    private List<Location> locationHierarchy;


    private static final SortDefinition DESCENDING_LOCATIONS;
    private static final SortDefinition ASCENDING_LOCATIONS;
    private static final SortDefinition DESCENDING_VALUES;
    private static final SortDefinition ASCENDING_VALUES;
    private static final SortDefinition DESCENDING_PERCENTAGES;
    private static final SortDefinition ASCENDING_PERCENTAGES;

    static {

        DESCENDING_LOCATIONS = new SortDefinition(SortType.LOCATION, null, false);
        ASCENDING_LOCATIONS = new SortDefinition(SortType.LOCATION, null, true);

        DESCENDING_VALUES = new SortDefinition(SortType.VALUE, FactName.AGE_0_4, false);
        ASCENDING_VALUES = new SortDefinition(SortType.VALUE, FactName.AGE_0_4, false);

        DESCENDING_PERCENTAGES = new SortDefinition(SortType.VALUE, FactName.AGE_0_4, false);
        ASCENDING_PERCENTAGES = new SortDefinition(SortType.VALUE, FactName.AGE_0_4, false);

    }

    @Before
    public void setup() {
        locationHierarchy = new MockCensus().fetchLocationHierarchy();
    }


    @Test
    public void descendingLocations() throws Exception {

        LocationSorter.sort(locationHierarchy, DESCENDING_LOCATIONS);

        assertEquals(findLocation(), MockCensus.ENGLAND_AND_WALES);
        assertEquals(findLocation(0), MockCensus.BATH_AND_NORTH_EAST_SOMERSET);
        assertEquals(findLocation(0, 0), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 1), MockCensus.ABBEY);
        assertEquals(findLocation(0, 1, 0), MockCensus.E00072564);
        assertEquals(findLocation(0, 1, 1), MockCensus.E00072563);

    }

    @Test
    public void ascendingLocations() throws Exception {

        LocationSorter.sort(locationHierarchy, ASCENDING_LOCATIONS);

        assertEquals(findLocation(), MockCensus.ENGLAND_AND_WALES);
        assertEquals(findLocation(0), MockCensus.BATH_AND_NORTH_EAST_SOMERSET);
        assertEquals(findLocation(0, 0), MockCensus.ABBEY);
        assertEquals(findLocation(0, 1), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 0, 0), MockCensus.E00072563);
        assertEquals(findLocation(0, 0, 1), MockCensus.E00072564);
    }

    @Test
    public void descendingValues() throws Exception {

        LocationSorter.sort(locationHierarchy, DESCENDING_VALUES);

        assertEquals(findLocation(0, 0), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 1), MockCensus.ABBEY);

    }

    @Test
    public void ascendingValues() throws Exception {

        LocationSorter.sort(locationHierarchy, ASCENDING_VALUES);

        assertEquals(findLocation(0, 1), MockCensus.ABBEY);
        assertEquals(findLocation(0, 0), MockCensus.BATHAVON_NORTH);

    }

    @Test
    public void descendingPercentages() throws Exception {

        LocationSorter.sort(locationHierarchy, DESCENDING_PERCENTAGES);

        assertEquals(findLocation(0, 0), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 1), MockCensus.ABBEY);

    }

    @Test
    public void ascendingPercentages() throws Exception {

        LocationSorter.sort(locationHierarchy, ASCENDING_PERCENTAGES);

        assertEquals(findLocation(0, 0), MockCensus.BATHAVON_NORTH);
        assertEquals(findLocation(0, 1), MockCensus.ABBEY);

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