package uk.co.i4software.poppie.census;

import lombok.Getter;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since June 2015
 */
public enum FactName {

    TOTAL_MALES("Total Males", 12, 2903),
    TOTAL_FEMALES("Total Females", 13, 2767),

    AGE_0_4("Age 0-4", 14, 141),
    AGE_5_9("Age 5-9", 15, 104),
    AGE_10_15("Age 10-15", 16, 102),
    AGE_16_24("Age 16-24", 17, 1181),
    AGE_25_44("Age 25-44", 18, 2018),
    AGE_45_64("Age 45-64", 19, 1148),
    AGE_65_74("Age 65-75", 20, 381),
    AGE_75_AND_OVER("75 and over", 21, 595),

    IN_HOUSEHOLDS("In households", 22, 5480),
    IN_COMMUNAL_ESTABLISHMENTS("In communal establishments", 23, 190),
    FULL_TIME_STUDENTS("Full time students", 24, 903),

    WHITE("White", 25, 4976),
    BLACK_AND_MINORITY_ETHNIC("Black or Minority Ethnic", 30, 694),

    WHITE_BRITISH("White British", 26, 4238),
    WHITE_IRISH("White Irish", 27, 78),
    WHITE_GYPSY_OR_IRISH_TRAVELLER("White Gypsy or Irish Traveller", 28, 0),
    OTHER_WHITE("Other White", 29, 660),

    WHITE_AND_BLACK_CARIBBEAN("White and Black Caribbean", 31, 19),
    WHITE_AND_BLACK_AFRICAN("White and Black African", 32, 17),
    WHITE_AND_ASIAN("White and Asian", 33, 34),
    OTHER_MIXED("Other Mixed", 34, 49),

    INDIAN("Indian", 35, 83),
    PAKISTANI("Pakistani", 36, 3),
    BANGLADESHI("Bangladeshi", 37, 10),
    CHINESE("Chinese", 38, 196),
    OTHER_ASIAN("Other Asian", 39, 136),

    AFRICAN("African", 40, 22),
    CARIBBEAN("Caribbean", 41, 32),
    OTHER_BLACK("Other Black", 42, 8),

    ARAB("Arab", 43, 40),
    OTHER("Other", 44, 45),

    UNITED_KINGDOM("United Kingdom", 45, 4199),
    REPUBLIC_OF_IRELAND("Republic Of Ireland", 46, 48),
    EU_COUNTRIES("EU Countries", 47, 313),
    EU_ACCESSION_COUNTRIES("EU Accession Countries", 48, 207),
    OTHER_COUNTRIES("Other Countries", 49, 903),

    CHRISTIAN("Christian", 50, 2634),
    BUDDHIST("Buddhist", 51, 96),
    HINDU("Hindu", 52, 39),
    JEWISH("Jewish", 53, 14),
    MUSLIM("Muslim", 54, 113),
    SIKH("Sikh", 55, 3),
    OTHER_RELIGIONS("Other religions", 56, 62),
    NO_RELIGION("No religion", 57, 2181),
    RELIGION_NOT_STATED("Religion not stated", 58, 528);

    @Getter private final String displayName;
    @Getter private final int jsonColumnNumber;
    @Getter private final long testValue;

    FactName(String displayName, int jsonColumnNumber, long testValue) {
        this.displayName = displayName;
        this.jsonColumnNumber = jsonColumnNumber;
        this.testValue = testValue;
    }
}
