package eg.edu.alexu.csd.oop.db.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUpdateQuery implements Parser {

    private static final String insertSomePattern = "(\\A)(?i)(\\s*)(insert)(\\s+)(into)(\\s+)(\\w+)(\\s*)([(](((\\s*)(\\w+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)))[)])(\\s*)(values)(\\s*)[(]((\\s*)(((\\s*)[']([^'])*['](\\s*)[,](\\s*))|((\\s*)([0-9])+(\\s*)[,](\\s*)))*(((\\s*)[']([^'])*['](\\s*))|((\\s*)([0-9])+(\\s*))))[)](\\s*)(?-i)(\\z)";
    private static final String insertAllPattern = "(\\A)(?i)(\\s*)(insert)(\\s+)(into)(\\s+)(\\w+)(\\s*)(values)(\\s*)[(]((\\s*)(((\\s*)[']([^'])*['](\\s*)[,](\\s*))|((\\s*)([0-9])+(\\s*)[,](\\s*)))*(((\\s*)[']([^'])*['](\\s*))|((\\s*)([0-9])+(\\s*))))[)](\\s*)(?-i)(\\z)";
    private static final String deleteAllPattern = "(\\A)(?i)(\\s*)(delete)(\\s+)(from)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
    private static final String deleteSomePattern = "(\\A)(?i)(\\s*)(delete)(\\s+)(from)(\\s+)(\\w+)(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";
    private static final String updatePattern = "(\\A)(?i)(\\s*)(update)(\\s+)(\\w+)(\\s+)(set)(\\s+)(((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)))(\\s*)(?-i)(\\z)";
    private static final String updateWherePattern = "(\\A)(?i)(\\s*)(update)(\\s+)(\\w+)(\\s+)(set)(\\s+)(((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)))(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";

    public int isValid(String query) {
        return Math.max(Math.max(insertIsValid(query), deleteIsValid(query)), updateIsValid(query));
    }

    private int deleteIsValid(String query) {
        return Math.max(regexMatcher(query, deleteAllPattern) ? 9 : -1, regexMatcher(query, deleteSomePattern) ? 10 : -1);
    }

    private int insertIsValid(String query) {
        return Math.max(regexMatcher(query, insertSomePattern) ? 11 : -1, regexMatcher(query, insertAllPattern) ? 12 : -1);
    }

    private int updateIsValid(String query) {
        return Math.max(regexMatcher(query, updatePattern) ? 13 : -1, regexMatcher(query, updateWherePattern) ? 14 : -1);
    }

    private boolean regexMatcher(String input, String pattern) {
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(input);
        return mat.matches();
    }

}
