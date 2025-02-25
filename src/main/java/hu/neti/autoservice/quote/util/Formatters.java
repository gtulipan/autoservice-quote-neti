package hu.neti.autoservice.quote.util;

import lombok.experimental.UtilityClass;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import static hu.neti.autoservice.quote.constants.Constants.*;

@UtilityClass
public class Formatters {
    private static NumberFormat CURRENCY_FORMATTER;

    public static NumberFormat getCurrencyFormatter() {
        CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(LOCALE_HU);
        CURRENCY_FORMATTER.setCurrency(Currency.getInstance(LOCALE_HU));
        CURRENCY_FORMATTER.setMaximumFractionDigits(2);
        return CURRENCY_FORMATTER;
    }

    public static String getFileName(String customerName) {
        return String.join(EMPTY_STRING,
                QUOTE_FILE_NAME_START,
                UNDERLINE,
                customerName.replaceAll(WHITE_SPACE_MARK, EMPTY_STRING),
                UNDERLINE,
                timestamp().replaceAll(COLON, DOT),
                EXTENSION_PDF);
    }

    public static String timestamp(){
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
    }

    public static String date(){
        return DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());
    }
}
