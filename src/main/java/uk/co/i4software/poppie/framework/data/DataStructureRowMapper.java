package uk.co.i4software.poppie.framework.data;

import javax.json.JsonArray;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public interface DataStructureRowMapper<T> {

    void mapRow(T dataStructure, JsonArray jsonArray);
}
