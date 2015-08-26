package uk.co.i4software.poppie.population;

import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.Location;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@FacesComponent(value = "population")
public class PopulationComponent extends UINamingContainer {

    private static final String LOCATIONS_ATTR = "locations";
    private static final String FACT_NAMES_ATTR = "factNames";

    private static final String POPULATION_MODEL = "POPULATION_MODEL";

    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        super.encodeBegin(context);
        final PopulationModel populationModel = new PopulationModelCreator(locations(), factNames()).create();
        setPopulationModel(populationModel);
    }

    private void setPopulationModel(PopulationModel populationModel) {
        this.getStateHelper().put(POPULATION_MODEL, populationModel);
    }

    private FactName[] factNames() {
        return (FactName[]) getAttributes().get(FACT_NAMES_ATTR);
    }

    private Location[] locations() {
        final Location[] locations = (Location[]) getAttributes().get(LOCATIONS_ATTR);

        return locations == null ? new Location[0] : locations;
    }

    private PopulationModel getPopulationModel() {
        return (PopulationModel) this.getStateHelper().get(POPULATION_MODEL);
    }

    public PieChartModel getPieChartModel() {
        return getPopulationModel().getPieChartModel();
    }

    public HorizontalBarChartModel getBarChartModel() { return getPopulationModel().getBarChartModel(); }

    public boolean isComponentRendered() {
        return locations().length > 0;
    }

    public Number valueOf(Location location, FactName factName) {
        return getPopulationModel().valueOf(location, factName);
    }

    public Number percentageOf(Location location, FactName factName) {
        return getPopulationModel().percentageOf(location, factName);
    }

}
