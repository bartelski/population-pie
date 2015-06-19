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

    private List<LocationType> locationTypes;

    public BathHackedCensus2011() {
        super(JSON_FILENAME);
    }

    public List<LocationType> fetchLocationTypes() {
        if (locationTypes == null) createLocationTypes();
        return locationTypes;
    }

    public FactType[] fetchFactTypes() {
        return FactType.values();
    }

    private void createLocationTypes() {
        convertLocationTypeMapToLocationTypes(locationTypeMap());
    }

    private Map<String, List<Location>> locationTypeMap() {

        Map<String, List<Location>> locationTypeMap = new LinkedHashMap<String, List<Location>>();
        buildDataStructure(locationTypeMap, new LocationTypeMapRowMapper());

        return locationTypeMap;
    }

    private void convertLocationTypeMapToLocationTypes(Map<String, List<Location>> locationTypeMap) {

        locationTypes = new ArrayList<LocationType>();

        for (String locationTypeName : locationTypeMap.keySet()) {
            final List<Location> locations = locationTypeMap.get(locationTypeName);
            locationTypes.add(locationType(locationTypeName, locations));
        }
    }

    private LocationType locationType(String locationType, List<Location> locations) {
        Collections.sort(locations, new LocationComparator());
        return new LocationType(locationType, locations);
    }

    private class LocationComparator implements Comparator<Location> {
        public int compare(Location o1, Location o2) {
            return o1.getLocationName().compareTo(o2.getLocationName());
        }
    }

    private static class LocationTypeMapRowMapper implements DataStructureRowMapper<Map<String,List<Location>>> {

        private static final int LOCATION_TYPE_COLUMN = 8;
        private static final int LOCATION_NAME_COLUMN = 10;

        public void mapRow(Map<String, List<Location>> map, JsonArray jsonArray) {

            String[] locationFields = stringsAt(jsonArray, LOCATION_TYPE_COLUMN, LOCATION_NAME_COLUMN);
            if (isNull(locationFields)) return;

            List<Fact> facts = new ArrayList<Fact>();

            for (FactName factName : FactName.values()) {
                facts.add(new Fact(factName, longAt(jsonArray, factName.getJsonColumnNumber())));
            }

            final String locationTypeName = locationFields[0];
            final String locationName = locationFields[1];

            locationList(map, locationTypeName).add(new Location(locationName, facts));
        }

        private List<Location> locationList(Map<String, List<Location>> map, String locationTypeName) {

            if (!map.containsKey(locationTypeName))
                map.put(locationTypeName, new ArrayList<Location>());

            return map.get(locationTypeName);
        }
    }
}