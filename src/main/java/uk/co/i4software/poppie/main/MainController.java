package uk.co.i4software.poppie.main;

import uk.co.i4software.poppie.census.Census;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@Named
@SessionScoped
public class MainController implements Serializable {

    private static final long serialVersionUID = 56485545678274039L;

    @Inject
    private Census census;

    public List<Location> getRootLocations() {
        return census.fetchRootLocations();
    }

    public FactType[] getFactTypes() {
        return census.fetchFactTypes();
    }
}
