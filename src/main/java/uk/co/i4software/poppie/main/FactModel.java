package uk.co.i4software.poppie.main;

import lombok.Getter;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.Location;

import java.io.Serializable;
import java.util.Map;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
public class FactModel implements Serializable {

    private static final long serialVersionUID = -736078452584137611L;

    @Getter
    private final PieChartModel pieChartModel;
    @Getter private final HorizontalBarChartModel barChartModel;

    private final Map<Location, Map<FactName, Number>> locationPercentages;

    public FactModel(PieChartModel pieChartModel, HorizontalBarChartModel barChartModel,
                     Map<Location, Map<FactName, Number>> locationPercentages) {

        this.pieChartModel = pieChartModel;
        this.barChartModel = barChartModel;
        this.locationPercentages = locationPercentages;
    }

    public Number valueOf(Location location, FactName factName) {
        return location.factValueOf(factName);
    }

    public Number percentageOf(Location location, FactName factName) {
        return locationPercentages.get(location).get(factName);
    }
}
