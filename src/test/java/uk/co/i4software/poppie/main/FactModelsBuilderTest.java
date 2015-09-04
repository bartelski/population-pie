package uk.co.i4software.poppie.main;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.chart.ChartSeries;
import uk.co.i4software.poppie.census.FactType;
import uk.co.i4software.poppie.census.Location;
import uk.co.i4software.poppie.census.MockCensus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.co.i4software.poppie.census.FactType.AGES;
import static uk.co.i4software.poppie.census.MockCensus.ABBEY;
import static uk.co.i4software.poppie.census.MockCensus.BATHAVON_NORTH;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
public class FactModelsBuilderTest {

    private final MockCensus mockCensus = new MockCensus();

    private static final List<Location> SINGLE_LOCATION = Collections.singletonList(ABBEY);
    private static final List<Location> MULTIPLE_LOCATIONS = Arrays.asList(ABBEY, BATHAVON_NORTH);
    private static final Number[] SINGLE_LOCATION_PIE_VALUES = {141L, 104L, 102L, 1181L, 2018L, 1148L, 381L, 595L};
    private static final Number[] MULTIPLE_LOCATION_PIE_VALUES = {468L, 104L, 102L, 1181L, 2018L, 3312L, 381L, 595L};

    private static final Number[] ABBEY_TABLE_VALUES = {141L, 104L, 102L, 1181L, 2018L, 1148L, 381L, 595L};
    private static final Number[] ABBEY_TABLE_PERCENTAGES = {2.49, 1.83, 1.80, 20.83, 35.59, 20.25, 6.72, 10.49};

    private Map<FactType, FactModel> singleLocationFactModels;
    private Map<FactType, FactModel> multipleLocationFactModels;

    @Before
    public void setup() {
       singleLocationFactModels = new FactModelsBuilder(SINGLE_LOCATION, mockCensus.fetchFactTypes()).build();
       multipleLocationFactModels = new FactModelsBuilder(MULTIPLE_LOCATIONS, mockCensus.fetchFactTypes()).build();
    }

    @Test
    public void testPieChartModels() {
        testPieChartModel(singleLocationFactModels, SINGLE_LOCATION_PIE_VALUES);
        testPieChartModel(multipleLocationFactModels, MULTIPLE_LOCATION_PIE_VALUES);
    }

    private void testPieChartModel(Map<FactType, FactModel> factModels, Number[] values) {

        final Map<String, Number> pieData = factModels.get(AGES).getPieChartModel().getData();

        for (int i = 0; i < AGES.getFactNames().length; i++) {
            assertTrue(pieData.containsKey(AGES.getFactNames()[i].getDisplayName()));
            assertEquals(values[i], pieData.get(AGES.getFactNames()[i].getDisplayName()));
        }
    }

    @Test
    public void testTableModel() {

        final FactModel factModel = singleLocationFactModels.get(AGES);

        for (int i = 0; i < AGES.getFactNames().length; i++) {
            assertEquals(ABBEY_TABLE_VALUES[i], factModel.valueOf(ABBEY, AGES.getFactNames()[i]));
            assertEquals(ABBEY_TABLE_PERCENTAGES[i], factModel.percentageOf(ABBEY, AGES.getFactNames()[i]));
        }
    }

    @Test
    public void testBarChartModel() {

        final FactModel factModel = singleLocationFactModels.get(AGES);

        final List<ChartSeries> chartSeriesList = factModel.getBarChartModel().getSeries();

        for (int i = 0; i < AGES.getFactNames().length; i++) {

            final ChartSeries chartSeries = chartSeriesList.get(i);
            final String factDisplayName = AGES.getFactNames()[i].getDisplayName();
            final String chartSeriesLabel = chartSeries.getLabel();
            final Number chartValue = chartSeries.getData().get(ABBEY.getLocationName());

            assertEquals(factDisplayName, chartSeriesLabel);
            assertEquals(ABBEY_TABLE_PERCENTAGES[i], chartValue);

        }
    }
}