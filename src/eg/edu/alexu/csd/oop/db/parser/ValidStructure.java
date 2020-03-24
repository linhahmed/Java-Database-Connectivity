package eg.edu.alexu.csd.oop.db.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidStructure implements Parser {
    private static final String createDBPattern = "(\\A)(?i)(\\s*)(create)(\\s+)(database)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
    private static final String createTablePattern = "(\\A)(?i)(\\s*)(create)(\\s+)(table)(\\s+)(\\w+)(\\s*)[(](((\\s*)(\\w+)(\\s+)((varchar)|(int))(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s+)((varchar)|(int))(\\s*)))[)](\\s*)(?-i)(\\z)";
    private static final String dropDBPattern = "(\\A)(?i)(\\s*)(drop)(\\s+)(database)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
    private static final String dropTablePattern = "(\\A)(?i)(\\s*)(drop)(\\s+)(table)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";

    public int isValid(String query) {
        return Math.max(createIsValid(query), dropIsValid(query));
    }

    private int createIsValid(String query) {
        return Math.max(regexMatcher(query, createDBPattern) ? 1 : -1, regexMatcher(query, createTablePattern) ? 2 : -1);
    }

    private int dropIsValid(String query) {
        return Math.max(regexMatcher(query, dropDBPattern) ? 3 : -1, regexMatcher(query, dropTablePattern) ? 4 : -1);
    }

    private boolean regexMatcher(String input, String pattern) {
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(input);
        return mat.matches();
    }

}
