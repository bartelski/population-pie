package uk.co.i4software.poppie.framework;

import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Ignore;
import org.junit.Test;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public class SodaTest {

    private static final String URL = "https://data.bathhacked.org";
    private static final String RESOURCE_ID = "qxks-a8tv";


    @Test
    @Ignore
    public void loadData() {

        Soda2Consumer consumer = Soda2Consumer.newConsumer(URL);

        try {

            ClientResponse response = consumer.query(RESOURCE_ID, HttpLowLevel.JSON_TYPE, SoqlQuery.SELECT_ALL);

            String payload = response.getEntity(String.class);

            System.out.println(payload);

        } catch (LongRunningQueryException e) {
            e.printStackTrace();
        } catch (SodaError sodaError) {
            sodaError.printStackTrace();
        }

    }



}