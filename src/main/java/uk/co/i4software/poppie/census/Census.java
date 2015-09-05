package uk.co.i4software.poppie.census;

import java.io.Serializable;
import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public interface Census extends Serializable {

    List<Location> fetchLocationHierarchy();

    FactType[] fetchFactTypes();
}
