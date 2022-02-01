package dev.dankom.sremapper.util;

public class StringUtil {
    public static String getAfter(String s, char after) {
        String out = "";
        boolean add = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == after) {
                add = true;
            } else if (add) {
                out += s.charAt(i);
            }
        }
        return out;
    }

    public static String getBetween(String s, char start, char end) {
        String out = "";
        boolean add = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == start) {
                add = true;
            } else if (s.charAt(i) == end) {
                break;
            } else if (add) {
                out += s.charAt(i);
            }
        }
        return out;
    }
}
