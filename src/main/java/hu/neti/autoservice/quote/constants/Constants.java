package hu.neti.autoservice.quote.constants;

import lombok.experimental.UtilityClass;

import java.util.Currency;
import java.util.Locale;

@UtilityClass
public class Constants {

    public static final String PARTS_PRICES = "Alkatrészek össz ára: %s";
    public static final String DISCOUNT_ON_PARTS_PRICES = "Kedvezmény az alkatrészek árából: %s";
    public static final String LABOR_FEE = "Munkadíj: %s";
    public static final String TOTAL = "Össz ár: %s";
    public static final Locale LOCALE_HU = Locale.of("hu", "HU");
    public static final String QUOTE = "Árajánlat";
    public static final String EMPTY_STRING = "";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String WHITE_SPACE_MARK = "\\s";
    public static final String WHITE_SPACE = " ";
    public static final String QUOTE_FILE_NAME_START = "quote";
    public static final String UNDERLINE = "_";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String CUSTOMER_NAME = "Ügyfél neve: ";
    public static final String CUSTOMER_TYPE = "Ügyfél típusa: ";
    public static final String PART_NAME = "Alkatrész név";
    public static final String PRICE = String.join("", "Ár (", Currency.getInstance(LOCALE_HU).getSymbol(), ")");
    public static final String AUTO_SERVICE = "Autószervíz Kft";
    public static final String COMMA = ", ";
    public static final String LOYAL = "törzsvásárló";
    public static final String LOYAL_TYPE = "LOYAL";
    public static final String VIP_TYPE = "VIP";
    public static final String MINUS = "-";
}
