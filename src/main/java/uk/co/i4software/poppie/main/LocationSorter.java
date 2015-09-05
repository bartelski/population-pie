package uk.co.i4software.poppie.main;

import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.Location;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
class LocationSorter {

    public static void sort(List<Location> locations, FactName sortBy, FactName lastSortBy) {

        if (sortBy == lastSortBy)
            Collections.reverse(locations); else sortByDescendingValue(locations, sortBy);

        for (Location location : locations)
            sort(location.getChildLocations(), sortBy, lastSortBy);
    }

    private static void sortByDescendingValue(List<Location> locations, final FactName sortBy) {

        Collections.sort(locations, new Comparator<Location>() {
            public int compare(Location l1, Location l2) {

                final Long factValue1 = l1.factValueOf(sortBy);
                final Long factValue2 = l2.factValueOf(sortBy);

                return factValue1 < factValue2 ? 1 : factValue2 < factValue1 ? -1 : 0;
            }
        });
    }

}
