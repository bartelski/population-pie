package uk.co.i4software.poppie.census;

import lombok.Getter;

import static uk.co.i4software.poppie.census.FactName.*;

/**
 * (c) Copyright i4 Software Ltd. All Rights Reserved.
 *
 * @author David Barton
 * @since May 2015
 */
public enum FactType {

    SEX("Sex", TOTAL_MALES, TOTAL_FEMALES),
    AGES("Age", AGE_0_4, AGE_5_9, AGE_10_15, AGE_16_24, AGE_25_44, AGE_45_64, AGE_65_74, AGE_75_AND_OVER),
    HOUSEHOLDS("Households", IN_HOUSEHOLDS, IN_COMMUNAL_ESTABLISHMENTS, FULL_TIME_STUDENTS);

    /*
    ETHNIC_BACKGROUND(WHITE_TOTAL, BLACK_AND_MINORITY_ETHNIC_TOTAL);
    COUNTRY_OF_BIRTH(UNITED_KINGDOM, REPUBLIC_OF_IRELAND, OTHER_EU_MEMBER, OTHER_EU_ACCESSION, OTHER_COUNTRIES),
    RELIGION_FAITH(CHRISTIAN, BUDDHIST, HINDU, JEWISH, MUSLIM, SIKH, OTHER_RELIGIONS, NO_RELIGION, NOT_STATED),
    LANGUAGES_SPOKEN(MAIN_ENGLISH, MAIN_NOT_ENGLISH),
    TIME_IN_UK(BORN_IN_UK, LESS_THAN_TWO, TWO_TO_FIVE, FIVE_TO_TEN, TEN_OR_MORE),
    HEALTH_AND_CARING()
    */

    @Getter private final String displayName;
    @Getter private final FactName[] factNames;

    FactType(String displayName, FactName... factNames) {
        this.factNames = factNames;
        this.displayName = displayName;
    }

}
