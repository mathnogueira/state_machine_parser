package statemachine.utils;

public class StringUtils {

    public static String sanitize(String input) {
        return input
                .replaceAll("\\t", "")
                .replaceAll(" ", "");
    }
}
