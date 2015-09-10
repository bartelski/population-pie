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

    public static void sort(List<Location> locations, SortDefinition sortDefinition) {

        sort(locations, toComparator(sortDefinition));


    }

    private static void sort(List<Location> locations, Comparator<? super Location> comparator) {

        Collections.sort(locations, comparator);

        for (Location location : locations)
            sort(location.getChildLocations(), comparator);

    }


    private static Comparator<? super Location> toComparator(SortDefinition sortDefinition) {

        final int ascending = toInt(sortDefinition.isAscending());

        switch (sortDefinition.getSortType()) {

            case VALUE:

                return valueComparator(sortDefinition.getFactName(), ascending);

            case PERCENTAGE:
                return percentageComparator(sortDefinition.getFactName(), ascending);

            default:
                return locationComparator(ascending);
        }

    }

    private static Comparator<? super Location> locationComparator(final int ascending) {

        return new Comparator<Location>() {
            public int compare(Location l1, Location l2) {
                return (l1.getDisplayName().compareToIgnoreCase(l2.getDisplayName())) * ascending;
            }
        };
    }

    private static Comparator<? super Location> percentageComparator(FactName factName, int ascending) {
        return new Comparator<Location>() {
            public int compare(Location l1, Location l2) {
                return 0;
            }
        };
    }

    private static int toInt(boolean ascending) {
        return ascending ? 1 : -1;
    }

    private static Comparator<? super Location> valueComparator(final FactName factName, final int ascending) {

        return new Comparator<Location>() {
            public int compare(Location l1, Location l2) {

                final Long factValue1 = l1.factValueOf(factName);
                final Long factValue2 = l2.factValueOf(factName);

                return factValue1 < factValue2 ? (-ascending): factValue2 < factValue1 ? ascending : 0;
            }
        };
    }

}
