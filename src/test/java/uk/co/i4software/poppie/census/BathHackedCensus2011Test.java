package uk.co.i4software.poppie.census;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.co.i4software.poppie.census.MockCensus.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public class BathHackedCensus2011Test {

    @Rule
    public NeedleRule needleRule = new NeedleRule();

    @ObjectUnderTest
    private BathHackedCensus2011 census2011;

    private List<Location> allLocations;

    @Before
    public void setup() {

        allLocations = new ArrayList<Location>();
        createLocations(census2011.fetchRootLocations());
    }

    private void createLocations(List<Location> locations) {

        for (Location location : locations) {
            this.allLocations.add(location);
            createLocations(location.getChildLocations());
        }
    }

    @Test
    public void testLocations() throws Exception {

        testLocation(ENGLAND_AND_WALES);
        testLocation(BATH_AND_NORTH_EAST_SOMERSET);
        testLocation(ABBEY);
        testLocation(BATHAVON_NORTH);
        testLocation(E00072563);
        testLocation(E00072564);

        assertEquals(622, allLocations.size());
    }

    private void testLocation(Location location) {
        assertEquals(location, actual(location));
    }

    private Location actual(Location location) {
        return allLocations.get(allLocations.indexOf(location));
    }

    @Test
    public void testLocationFacts() throws Exception {
        testLocationFacts(ABBEY);
        testLocationFacts(BATHAVON_NORTH);
    }

    private void testLocationFacts(Location mockLocation) {
        compareFactsAtLocations(mockLocation, actual(mockLocation));
    }

    private void compareFactsAtLocations(Location mockLocation, Location actualLocation) {

        for (Fact fact : mockLocation.getFacts()) {
            assertTrue(actualLocation.getFacts().contains(fact));
            assertEquals(mockLocation.factValueOf(fact.getFactName()), actualLocation.factValueOf(fact.getFactName()));
        }
    }

    @Test
    public void testAbbeyFacts() throws Exception {
        for (FactName factName : FactName.values()) {
            assertEquals(factName.getTestValue(), factValueForAbbey(factName));
        }
    }

    private long factValueForAbbey(FactName factName) {
        return actual(MockCensus.ABBEY).factValueOf(factName);
    }
}