
package util;

import java.text.DecimalFormat;

/**
 *
 * @author malit
 */
public class GeneralUtil {
    public static String getCurrencyString(double v) {
        if (v==0) {
            return "0.00";
        }
        return new DecimalFormat("#,###.00").format(v);
    }

    public static double currencyStringToDouble(String input) throws NumberFormatException{
        if (input == null || input.trim().isEmpty()) {
            return 0.0;
        }

        // Remove currency letters and spaces (keep digits, comma, dot, minus)
        String cleaned = input.replaceAll("[^0-9,.-]", "");

        // Remove all commas (assuming they are thousand separators)
        cleaned = cleaned.replace(",", "");

        // Handle edge case like "." or "-"
        if (cleaned.isEmpty() || cleaned.equals(".") || cleaned.equals("-")) {
            return 0.0;
        }

        return Double.parseDouble(cleaned);
    }
}
