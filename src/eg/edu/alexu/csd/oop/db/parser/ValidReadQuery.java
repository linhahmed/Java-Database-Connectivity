package eg.edu.alexu.csd.oop.db.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidReadQuery implements Parser {
    private static final String selectAllPattern = "(\\A)(?i)(\\s*)(select)(\\s*)[*](\\s*)(from)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
    private static final String selectAllWherePattern = "(\\A)(?i)(\\s*)(select)(\\s*)[*](\\s*)(from)(\\s+)(\\w+)(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";
    private static final String selectSomePattern = "(\\A)(?i)(\\s*)(select)(\\s+)(((\\s*)(\\w+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)))(\\s+)(from)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
    private static final String selectSomeWherePattern = "(\\A)(?i)(\\s*)(select)(\\s+)(((\\s*)(\\w+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)))(\\s+)(from)(\\s+)(\\w+)(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";

    public int isValid(String query) {
        return selectIsValid(query);
    }

    private int selectIsValid(String query) {
        return Math.max(Math.max(regexMatcher(query, selectAllPattern) ? 5 : -1, regexMatcher(query, selectAllWherePattern) ? 6 : -1), Math.max(regexMatcher(query, selectSomePattern) ? 7 : -1, regexMatcher(query, selectSomeWherePattern) ? 8 : -1));
    }

    private boolean regexMatcher(String input, String pattern) {
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(input);
        return mat.matches();
    }

}
