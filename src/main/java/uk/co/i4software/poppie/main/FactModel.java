package uk.co.i4software.poppie.main;

import lombok.Getter;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import uk.co.i4software.poppie.census.FactName;
import uk.co.i4software.poppie.census.Location;

import java.io.Serializable;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since September 2015
 */
class FactModel implements Serializable {

    private static final long serialVersionUID = -736078452584137611L;

    @Getter
    private final PieChartModel pieChartModel;
    @Getter
    private final HorizontalBarChartModel barChartModel;

    public FactModel(PieChartModel pieChartModel, HorizontalBarChartModel barChartModel) {

        this.pieChartModel = pieChartModel;
        this.barChartModel = barChartModel;
    }

    public Number valueOf(Location location, FactName factName) {
        return location.factValueOf(factName);
    }

    public Number percentageOf(Location location, FactName factName) {
        return location.factPercentageOf(factName);
    }
}
