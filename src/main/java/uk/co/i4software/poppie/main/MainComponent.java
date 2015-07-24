package uk.co.i4software.poppie.main;

import org.primefaces.model.TreeNode;
import uk.co.i4software.poppie.census.Location;

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


    private static final String ROOT_LOCATIONS_ATTR = "rootLocations";
    private static final String MAIN_MODEL = "MAIN_MODEL";

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        final MainModel mainModel = new MainModelCreator(rootLocations()).create();
        setMainModel(mainModel);
    }

    @SuppressWarnings("unchecked")
    private List<Location> rootLocations() {
        return (List<Location>) getAttributes().get(ROOT_LOCATIONS_ATTR);
    }

    public void onLocationsSelect() {

        final MainModel mainModel = getMainModel();

        final TreeNode[] selectedTreeNodes = mainModel.getSelectedTreeNodes();
        final Location[] selectedLocations = convertToLocationArray(selectedTreeNodes);

        mainModel.setSelectedLocations(selectedLocations);
    }

    public MainModel getMainModel() {
        return (MainModel) getStateHelper().get(MAIN_MODEL);
    }

    private void setMainModel(MainModel mainModel) {
        getStateHelper().put(MAIN_MODEL, mainModel);
    }

    private Location[] convertToLocationArray(TreeNode[] selectedTreeNodes) {

        Location[] selectedLocations = new Location[selectedTreeNodes.length];

        for (int i = 0; i < selectedTreeNodes.length; i++)
            selectedLocations[i] = (Location) selectedTreeNodes[i].getData();

        return selectedLocations;
    }

}
