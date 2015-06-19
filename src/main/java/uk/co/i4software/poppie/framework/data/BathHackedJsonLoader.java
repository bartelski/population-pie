package uk.co.i4software.poppie.framework.data;

import javax.json.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */

public abstract class BathHackedJsonLoader {

    private static final String JSON_DATA_KEY = "data";
    private static final String NULL_VALUE = "null";

    private JsonObject jsonObject;

    protected BathHackedJsonLoader(String jsonFilename) {
        loadJsonObject(jsonFilename);
    }

    private void loadJsonObject(String jsonFilename) {

        InputStream inputStream = getClass().getResourceAsStream(jsonFilename);
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));

        jsonObject = jsonReader.readObject();
    }

    private JsonArray data() {
        return jsonObject.getJsonArray(JSON_DATA_KEY);
    }

    private static String removeQuotes(String value) {
        return value.replace("\"", "");
    }

    protected static String[] stringsAt(JsonValue jsonValue, int... columns) {

        String[] values = new String[columns.length];

        for (int i = 0; i < columns.length; i++) {
            values[i] = stringAt(jsonValue, columns[i]);
        }

        return values;
    }

    private static String stringAt(JsonValue jsonArray, int column) {
        return removeQuotes(((JsonArray) jsonArray).get(column).toString());
    }

    protected static Long longAt(JsonArray jsonArray, int columnNumber) {
        return Long.valueOf(stringAt(jsonArray, columnNumber));
    }

    protected static boolean isNull(String... strings) {
        return Arrays.asList(strings).contains(NULL_VALUE);
    }

    protected <T> void buildDataStructure(T dataStructure, DataStructureRowMapper<T> dataStructureRowMapper)  {

        for (JsonValue jsonValue : data())
            dataStructureRowMapper.mapRow(dataStructure, (JsonArray) jsonValue);
    }
}
