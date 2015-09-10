package uk.co.i4software.poppie.census;

import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static uk.co.i4software.poppie.census.FactName.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@Alternative
public class MockCensus implements Census {

    private static final long serialVersionUID = -5263924610246105985L;

    private static final Fact ABBEY_MALES = new Fact(TOTAL_MALES, 2903);
    private static final Fact ABBEY_FEMALES = new Fact(TOTAL_FEMALES, 2767);

    private static final Fact ABBEY_0_4 = new Fact(AGE_0_4, 141);
    private static final Fact ABBEY_5_9 = new Fact(AGE_5_9, 104);
    private static final Fact ABBEY_10_15 = new Fact(AGE_10_15, 102);
    private static final Fact ABBEY_16_24 = new Fact(AGE_16_24, 1181);
    private static final Fact ABBEY_25_44 = new Fact(AGE_25_44, 2018);
    private static final Fact ABBEY_45_64 = new Fact(AGE_45_64, 1148);
    private static final Fact ABBEY_65_74 = new Fact(AGE_65_74, 381);
    private static final Fact ABBEY_75_AND_OVER = new Fact(AGE_75_AND_OVER, 595);

    private static final List<Fact> ABBEY_FACTS = asList(ABBEY_MALES, ABBEY_FEMALES, ABBEY_0_4, ABBEY_5_9,
            ABBEY_10_15, ABBEY_16_24, ABBEY_25_44, ABBEY_45_64, ABBEY_65_74, ABBEY_75_AND_OVER);

    private static final Fact BATHAVON_NORTH_MALES = new Fact(TOTAL_MALES, 3452);
    private static final Fact BATHAVON_NORTH_0_4 = new Fact(AGE_0_4, 327);
    private static final Fact BATHAVON_NORTH_45_64 = new Fact(AGE_45_64, 2164);

    private static final List<Fact> BATHAVON_NORTH_FACTS =
            asList(BATHAVON_NORTH_MALES, BATHAVON_NORTH_0_4, BATHAVON_NORTH_45_64);

    public static final Location ENGLAND_AND_WALES = new Location("England and Wales", new ArrayList<Fact>());
    public static final Location BATH_AND_NORTH_EAST_SOMERSET = new Location("Bath and North East Somerset", new
            ArrayList<Fact>());

    public static final Location ABBEY = new Location("Abbey", ABBEY_FACTS);
    public static final Location BATHAVON_NORTH = new Location("Bathavon North", BATHAVON_NORTH_FACTS);
    public static final Location E00072563 = new Location("Abbey ward: E00072563", "E00072563", new ArrayList<Fact>());
    public static final Location E00072564 = new Location("Abbey ward: E00072564", "E00072564", new ArrayList<Fact>());

    private final List<Location> locationHierarchy;


    public MockCensus() {
        locationHierarchy = new ArrayList<Location>();
        buildLocationHierarchy();
    }

    private void buildLocationHierarchy() {

        final List<Location> locationAuthorities = new ArrayList<Location>();
        locationAuthorities.add(BATH_AND_NORTH_EAST_SOMERSET);

        final List<Location> wards = asList(ABBEY, BATHAVON_NORTH);
        final List<Location> abbeyOutputAreas = asList(E00072563, E00072564);

        ABBEY.setChildLocations(abbeyOutputAreas);
        BATH_AND_NORTH_EAST_SOMERSET.setChildLocations(wards);
        ENGLAND_AND_WALES.setChildLocations(locationAuthorities);

        locationHierarchy.add(ENGLAND_AND_WALES);
    }

    public List<Location> fetchLocationHierarchy() {
        return locationHierarchy;
    }

    public FactType[] fetchFactTypes() {
        return FactType.values();
    }
}
