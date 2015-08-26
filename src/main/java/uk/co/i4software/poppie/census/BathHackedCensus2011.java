package uk.co.i4software.poppie.census;

import uk.co.i4software.poppie.framework.data.BathHackedJsonLoader;
import uk.co.i4software.poppie.framework.data.DataStructureRowMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import java.util.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@ApplicationScoped
public class BathHackedCensus2011 extends BathHackedJsonLoader implements Census {

    private static final long serialVersionUID = 972915064623897929L;

    private static final String JSON_FILENAME = "/census2011.json";

    private List<Location> rootLocations;

    public BathHackedCensus2011() {
        super(JSON_FILENAME);
    }

    private static List<Fact> createFacts(JsonArray jsonArray) {
        List<Fact> facts = new ArrayList<Fact>();

        for (FactName factName : FactName.values()) {
            facts.add(new Fact(factName, longAt(jsonArray, factName.getJsonColumnNumber())));
        }
        return facts;
    }

    public List<Location> fetchRootLocations() {
        if (rootLocations == null) createRootLocations();
        return rootLocations;
    }

    public FactType[] fetchFactTypes() {
        return FactType.values();
    }

    private void createRootLocations() {

        Map<LocationType, List<Location>> locationTypeMap = locationTypeMap();


        rootLocations = locationTypeMap.get(LocationType.COUNTRY);
        Collections.sort(rootLocations, new LocationComparator());

        final List<Location> localAuthorities = locationTypeMap.get(LocationType.LOCAL_AUTHORITY);
        Collections.sort(localAuthorities, new LocationComparator());

        final List<Location> wards = locationTypeMap.get(LocationType.WARD);
        Collections.sort(wards, new LocationComparator());

        final List<Location> outputAreas = locationTypeMap.get(LocationType.OUTPUT_AREA);
        Collections.sort(outputAreas, new LocationComparator());

        addOutputAreasToWards(outputAreas, wards);
        addWardsToLocationAuthorties(wards, localAuthorities);
        addLocalAuthoritiesToCountries(localAuthorities, rootLocations);
    }

    private Map<LocationType, List<Location>> locationTypeMap() {

        Map<LocationType, List<Location>> locationTypeMap = new LinkedHashMap<LocationType, List<Location>>();
        buildDataStructure(locationTypeMap, new LocationTypeMapRowMapper());

        return locationTypeMap;
    }

    private void addOutputAreasToWards(List<Location> outputAreas, List<Location> wards) {

        for (Location ward : wards)
            ward.setChildLocations(findChildOutputAreas(outputAreas, ward));
    }

    private void addWardsToLocationAuthorties(List<Location> wards, List<Location> localAuthorities) {

        for (Location localAuthority : localAuthorities)
            localAuthority.setChildLocations(wards);
    }

    private void addLocalAuthoritiesToCountries(List<Location> localAuthorities, List<Location> countries) {
        for (Location country : countries)
            country.setChildLocations(localAuthorities);
    }

    private List<Location> findChildOutputAreas(List<Location> outputAreas, Location ward) {

        List<Location> childOutputAreas = new ArrayList<Location>();

        for (Location outputArea : outputAreas)
            if (outputAreaIsInWard(outputArea, ward))
                childOutputAreas.add(outputArea);

        return childOutputAreas;
    }

    private boolean outputAreaIsInWard(Location outputArea, Location ward) {
        return outputArea.getLocationName().contains(ward.getLocationName());
    }

    private enum LocationType {COUNTRY, LOCAL_AUTHORITY, WARD, OUTPUT_AREA, LOWER_SUPER_OUTPUT_AREA, UNKNOWN}

    private static class LocationTypeMapRowMapper implements DataStructureRowMapper<Map<LocationType, List<Location>>> {

        private static final int LOCATION_TYPE_COLUMN = 8;
        private static final int LOCATION_NAME_COLUMN = 10;

        public void mapRow(Map<LocationType, List<Location>> map, JsonArray jsonArray) {

            String[] locationFields = stringsAt(jsonArray, LOCATION_TYPE_COLUMN, LOCATION_NAME_COLUMN);
            if (isNull(locationFields)) return;

            final LocationType locationType = toLocationType(locationFields[0]);
            final String locationName = locationFields[1];
            final String displayName = toLocationDisplayName(locationType, locationName);
            final List<Fact> facts = createFacts(jsonArray);

            locationList(map, locationType).add(new Location(locationName, displayName, facts));
        }

        private LocationType toLocationType(String locationName) {

            if (locationName.equals("Country")) return LocationType.COUNTRY;
            if (locationName.equals("Local Authority")) return LocationType.LOCAL_AUTHORITY;
            if (locationName.equals("Ward")) return LocationType.WARD;
            if (locationName.equals("Lower Super Output Area")) return LocationType.LOWER_SUPER_OUTPUT_AREA;
            if (locationName.equals("Output Area")) return LocationType.OUTPUT_AREA;

            return LocationType.UNKNOWN;
        }

        private String toLocationDisplayName(LocationType locationType, String locationName) {

            if (locationType == LocationType.OUTPUT_AREA)
                return locationName.split("ward: ")[1];
            else
                return locationName;
        }

        private List<Location> locationList(Map<LocationType, List<Location>> map, LocationType locationType) {

            if (!map.containsKey(locationType))
                map.put(locationType, new ArrayList<Location>());

            return map.get(locationType);
        }
    }

    private class LocationComparator implements Comparator<Location> {
        public int compare(Location o1, Location o2) {
            return o1.getLocationName().compareTo(o2.getLocationName());
        }
    }
}