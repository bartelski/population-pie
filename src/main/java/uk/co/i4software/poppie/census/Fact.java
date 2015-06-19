package uk.co.i4software.poppie.census;

import lombok.Data;

import java.io.Serializable;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
@Data
public class Fact implements Serializable {

    private static final long serialVersionUID = -8272589926755798452L;

    private final FactName factName;
    private final long factValue;

    public Fact(FactName factName, long factValue) {
        this.factName = factName;
        this.factValue = factValue;
    }

    public String getFactDisplayName() {
        return factName.getDisplayName();
    }

    public String getFactValueAsString() { return String.valueOf(factValue); }

    @Override
    public String toString() {
        return factName.getDisplayName() + ":" + factValue;
    }
}


