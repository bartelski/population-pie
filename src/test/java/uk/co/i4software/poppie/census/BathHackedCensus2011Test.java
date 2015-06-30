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
import static uk.co.i4software.poppie.census.FactName.*;
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

    private List<LocationType> locationTypes;
    private List<Location> locations;

    @Before
    public void setup() {
        locationTypes = census2011.fetchLocationTypes();
        createLocations();
    }

    private void createLocations() {

        locations = new ArrayList<Location>();

        for (LocationType locationType : locationTypes)
            locations.addAll(locationType.getLocations());
    }

    @Test
    public void testLocationTypes() throws Exception {
        final LocationType[] mockLocationTypes = {COUNTRY, LOCAL_AUTHORITY, WARD, LOWER_SUPER_OUTPUT_AREA, OUTPUT_AREA};
        assertLocationTypes(mockLocationTypes, locationTypes);
    }

    private void assertLocationTypes(LocationType[] expected, List<LocationType> actual) {

        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], actual.get(i));
    }

    @Test
    public void testLocations() throws Exception {

        testLocation(ENGLAND_AND_WALES);
        testLocation(BATH_AND_NORTH_EAST_SOMERSET);
        testLocation(ABBEY);
        testLocation(BATHAVON_NORTH);
        testLocation(E00072588);
        testLocation(E01014370);

        assertEquals(737, locations.size());
    }

    private void testLocation(Location location) {
        assertEquals(location, actual(location));
    }

    private Location actual(Location location) {
        return locations.get(locations.indexOf(location));
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
    public void testEthnicBackgroundFacts() throws Exception {
        assertEquals(4976, factValue(ABBEY, WHITE));
        assertEquals(694, factValue(ABBEY, BLACK_AND_MINORITY_ETHNIC));
    }

    private long factValue(Location location, FactName factName) {
        return actual(location).factValueOf(factName);
    }

    @Test
    public void testEthnicBackgroundWhiteFacts() throws Exception {
        assertEquals(4238, factValue(ABBEY, FactName.WHITE_BRITISH));
        assertEquals(78, factValue(ABBEY, FactName.WHITE_IRISH));
        assertEquals(0, factValue(ABBEY, FactName.WHITE_GYPSY_OR_IRISH_TRAVELLER));
        assertEquals(660, factValue(ABBEY, FactName.OTHER_WHITE));
    }

    @Test
    public void testEthnicBackgroundMixedFacts() throws Exception {
        assertEquals(19, factValue(ABBEY, FactName.WHITE_AND_BLACK_CARIBBEAN));
        assertEquals(17, factValue(ABBEY, FactName.WHITE_AND_BLACK_AFRICAN));
        assertEquals(34, factValue(ABBEY, FactName.WHITE_AND_ASIAN));
        assertEquals(49, factValue(ABBEY, FactName.OTHER_MIXED));
    }

    @Test
    public void testEthnicBackgroundAsianFacts() throws Exception {
        assertEquals(83, factValue(ABBEY, FactName.INDIAN));
        assertEquals(3, factValue(ABBEY, FactName.PAKISTANI));
        assertEquals(10, factValue(ABBEY, FactName.BANGLADESHI));
        assertEquals(196, factValue(ABBEY, FactName.CHINESE));
        assertEquals(136, factValue(ABBEY, FactName.OTHER_ASIAN));
    }

    @Test
    public void testEthnicBackgroundBlackFacts() throws Exception {
        assertEquals(22, factValue(ABBEY, FactName.AFRICAN));
        assertEquals(32, factValue(ABBEY, FactName.CARIBBEAN));
        assertEquals(8, factValue(ABBEY, FactName.OTHER_BLACK));
    }

    @Test
    public void testEthnicBackgroundOtherFacts() throws Exception {
        assertEquals(40, factValue(ABBEY, FactName.ARAB));
        assertEquals(45, factValue(ABBEY, FactName.OTHER));
    }

}