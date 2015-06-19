package uk.co.i4software.poppie.main;

import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.LocationType;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@FacesComponent(value = "main")
public class MainComponent extends UINamingContainer {


    private static final String LOCATION_TYPES_ATTR = "locationTypes";
    private static final String MAIN_MODEL = "MAIN_MODEL";

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        final MainModel mainModel = new MainModelCreator(locationTypes()).create();
        setMainModel(mainModel);
    }

    @SuppressWarnings("unchecked")
    private List<LocationType> locationTypes() {
        return (List<LocationType>) getAttributes().get(LOCATION_TYPES_ATTR);
    }

    private void setMainModel(MainModel mainModel) {
        getStateHelper().put(MAIN_MODEL, mainModel);
    }

    public MainModel getMainModel() {
        return (MainModel) getStateHelper().get(MAIN_MODEL);
    }

    public void onLocationsSelect() {

        final MainModel mainModel = getMainModel();

        final TreeNode[] selectedTreeNodes = mainModel.getSelectedTreeNodes();
        final Location[] selectedLocations = convertToLocationArray(selectedTreeNodes);

        mainModel.setSelectedLocations(selectedLocations);
    }

    private Location[] convertToLocationArray(TreeNode[] selectedTreeNodes) {

        Location[] selectedLocations = new Location[selectedTreeNodes.length];

        for (int i=0; i<selectedTreeNodes.length; i++)
            selectedLocations[i] = (Location)selectedTreeNodes[i].getData();

        return selectedLocations;
    }

}
