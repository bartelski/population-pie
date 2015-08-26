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

    private static final String[] THEMES = { "afterdark", "afternoon", "afterwork", "aristo", "black-tie", "blitzer",
            "bluesky", "bootstrap", "casablanca", "cupertino", "cruze", "dark-hive", "delta", "dot-luv", "eggplant",
            "excite-bike", "flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc",
            "overcast", "pepper-grinder", "redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny",
            "swanky-purse", "trontastic", "ui-darkness", "ui-lightness", "vader" };

    @Inject
    private Census census;

    public List<Location> getRootLocations() {
        return census.fetchRootLocations();
    }

    public FactType[] getFactTypes() {
        return census.fetchFactTypes();
    }

    public String[] getThemes() {
        return THEMES;
    }
}
