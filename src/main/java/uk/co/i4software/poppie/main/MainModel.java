package uk.co.i4software.poppie.main;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;

import java.io.Serializable;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
public class MainModel implements Serializable {

    private static final long serialVersionUID = 3353094740374788487L;

    @Getter private final TreeNode locationTree;

    @Getter @Setter private TreeNode[] selectedTreeNodes;
    @Getter @Setter private Location[] selectedLocations;


    public MainModel(TreeNode locationTree) {
        this.locationTree = locationTree;
    }

    public FactType[] getFactTypes() {
        return FactType.values();
    }

}
