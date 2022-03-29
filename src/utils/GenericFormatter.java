package utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Locale.Category;

public class GenericFormatter {
    static Locale lDefault = Locale.getDefault(Category.DISPLAY);
    static Locale lFormat = Locale.getDefault(Category.FORMAT);
    static DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    static ResourceBundle text = ResourceBundle.getBundle("vista.Texts", lDefault);
    static NumberFormat nFormatter;
    static NumberFormat cFormatter;
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void setLocale() {
        System.out.println(lDefault.toString());
        System.out.println(lFormat.toString());
        if (!lDefault.equals(new Locale("es", "ES")) && !lDefault.equals(new Locale("ca", "ES"))
                && !lDefault.equals(new Locale("en", "US"))) {
            lDefault = new Locale("es", "ES");
            System.out.println(lDefault);
            df = DateFormat.getDateInstance(DateFormat.SHORT, lDefault);
            text = ResourceBundle.getBundle("vista.Texts", lDefault);
        }
        if (!lFormat.equals(new Locale("es", "ES")) && !lFormat.equals(new Locale("ca", "ES"))) {
            lFormat = new Locale("es", "ES");
            System.out.println(lFormat);
        }

        nFormatter = NumberFormat.getNumberInstance(lFormat);
        cFormatter = NumberFormat.getCurrencyInstance(lFormat);
    }

    public static ResourceBundle getResourceBundle() {
        return text;
    }

    public static String formatNumber(Integer num) {
        if (num != null) {
            return nFormatter.format(num);
        }
        return " ";
    }

    public static String formatPrice(Double num) {
        if (num != null) {
            return cFormatter.format(num);
        }
        return " ";
    }

    public static String formatTime(LocalTime time) {
        if (time != null) {
            return timeFormatter.format(time);
        }
        return " ";
    }

    public static String formatDate(LocalDate date) {
        if (date != null) {
            return date.format(dateFormatter);
        }
        return null;
    }
}
