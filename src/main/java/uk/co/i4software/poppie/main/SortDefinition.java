package uk.co.i4software.poppie.main;

import lombok.Getter;
import uk.co.i4software.poppie.census.FactName;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
public class SortDefinition {


    @Getter
    private final SortType sortType;
    @Getter
    private final FactName factName;
    @Getter
    private final boolean ascending;

    public SortDefinition(SortType sortType, FactName factName, boolean ascending) {
        this.sortType = sortType;
        this.factName = factName;
        this.ascending = ascending;
    }

    enum SortType { LOCATION, VALUE, PERCENTAGE}
}
