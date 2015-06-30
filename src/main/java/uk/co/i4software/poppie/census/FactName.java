package uk.co.i4software.poppie.census;

import lombok.Getter;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
public enum FactName {

    TOTAL_MALES("Total Males", 12),
    TOTAL_FEMALES("Total Females", 13),

    AGE_0_4("Age 0-4", 14),
    AGE_5_9("Age 5-9", 15),
    AGE_10_15("Age 10-15", 16),
    AGE_16_24("Age 16-24", 17),
    AGE_25_44("Age 25-44", 18),
    AGE_45_64("Age 45-64", 19),
    AGE_65_74("Age 65-75", 20),
    AGE_75_AND_OVER("75 and over", 21),

    IN_HOUSEHOLDS("In households", 22),
    IN_COMMUNAL_ESTABLISHMENTS("In communal establishments", 23),
    FULL_TIME_STUDENTS("Full time students", 24),

    WHITE("White", 25),
    BLACK_AND_MINORITY_ETHNIC("Black or Minority Ethnic", 30),

    WHITE_BRITISH("White British", 26),
    WHITE_IRISH("White Irish", 27),
    WHITE_GYPSY_OR_IRISH_TRAVELLER("White Gypsy or Irish Traveller", 28),
    OTHER_WHITE("Other White", 29),

    WHITE_AND_BLACK_CARIBBEAN("White and Black Caribbean", 31),
    WHITE_AND_BLACK_AFRICAN("White and Black African", 32),
    WHITE_AND_ASIAN("White and Asian", 33),
    OTHER_MIXED("Other Mixed", 34),

    INDIAN("Indian", 35 ),
    PAKISTANI("Pakistani", 36),
    BANGLADESHI("Bangladeshi", 37),
    CHINESE("Chinese", 38),
    OTHER_ASIAN("Other Asian", 39),

    AFRICAN("African", 40),
    CARIBBEAN("Caribbean", 41),
    OTHER_BLACK("Other Black", 42),

    ARAB("Arab", 43),
    OTHER("Other", 44);

    @Getter
    private final String displayName;

    @Getter
    private final int jsonColumnNumber;

    FactName(String displayName, int jsonColumnNumber) {
        this.displayName = displayName;
        this.jsonColumnNumber = jsonColumnNumber;
    }
}
