package uk.co.i4software.poppie.census;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@EqualsAndHashCode(of={"locationTypeName"})
public class LocationType implements Serializable {

    private static final long serialVersionUID = -7092087510900452939L;

    @Getter private final String locationTypeName;
    @Getter private final List<Location> locations;

    public LocationType(String locationTypeName, List<Location> locations) {
        this.locationTypeName = locationTypeName;
        this.locations = locations;
    }

    @Override
    public String toString() {
        return locationTypeName;
    }

}
