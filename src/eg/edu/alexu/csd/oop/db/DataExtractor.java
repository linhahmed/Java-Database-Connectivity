package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 class DataExtractor {

    private static DataExtractor instance = new DataExtractor();
    private DataExtractor(){

    }

    private String selectAllPattern = "(\\A)(?i)(\\s*)(select)(\\s*)[*](\\s*)(from)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
	private String selectAllWherePattern = "(\\A)(?i)(\\s*)(select)(\\s*)[*](\\s*)(from)(\\s+)(\\w+)(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";
	private String selectSomePattern = "(\\A)(?i)(\\s*)(select)(\\s+)(((\\s*)(\\w+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)))(\\s+)(from)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
	private String selectSomeWherePattern = "(\\A)(?i)(\\s*)(select)(\\s+)(((\\s*)(\\w+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)))(\\s+)(from)(\\s+)(\\w+)(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";
	private String createDBPattern = "(\\A)(?i)(\\s*)(create)(\\s+)(database)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
	private String createTablePattern = "(\\A)(?i)(\\s*)(create)(\\s+)(table)(\\s+)(\\w+)(\\s*)[(](((\\s*)(\\w+)(\\s+)((varchar)|(int))(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s+)((varchar)|(int))(\\s*)))[)](\\s*)(?-i)(\\z)";
	private String dropDBPattern = "(\\A)(?i)(\\s*)(drop)(\\s+)(database)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
	private String dropTablePattern = "(\\A)(?i)(\\s*)(drop)(\\s+)(table)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
	private String insertSomePattern = "(\\A)(?i)(\\s*)(insert)(\\s+)(into)(\\s+)(\\w+)(\\s*)([(](((\\s*)(\\w+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)))[)])(\\s*)(values)(\\s*)[(]((\\s*)(((\\s*)[']([^'])*['](\\s*)[,](\\s*))|((\\s*)([0-9])+(\\s*)[,](\\s*)))*(((\\s*)[']([^'])*['](\\s*))|((\\s*)([0-9])+(\\s*))))[)](\\s*)(?-i)(\\z)";
	private String insertAllPattern = "(\\A)(?i)(\\s*)(insert)(\\s+)(into)(\\s+)(\\w+)(\\s*)(values)(\\s*)[(]((\\s*)(((\\s*)[']([^'])*['](\\s*)[,](\\s*))|((\\s*)([0-9])+(\\s*)[,](\\s*)))*(((\\s*)[']([^'])*['](\\s*))|((\\s*)([0-9])+(\\s*))))[)](\\s*)(?-i)(\\z)";
	private String deleteAllPattern = "(\\A)(?i)(\\s*)(delete)(\\s+)(from)(\\s+)(\\w+)(\\s*)(?-i)(\\z)";
	private String deleteSomePattern = "(\\A)(?i)(\\s*)(delete)(\\s+)(from)(\\s+)(\\w+)(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";
	private String updatePattern = "(\\A)(?i)(\\s*)(update)(\\s+)(\\w+)(\\s+)(set)(\\s+)(((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)))(\\s*)(?-i)(\\z)";
	private String updateWherePattern = "(\\A)(?i)(\\s*)(update)(\\s+)(\\w+)(\\s+)(set)(\\s+)(((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)[,](\\s*))*((\\s*)(\\w+)(\\s*)[=](\\s*)(([']([^']*)['])|[0-9]+)(\\s*)))(\\s+)(where)(\\s+)(\\w+)(\\s*)([=<>])(\\s*)(([']([^'])*['])|([0-9]+))(\\s*)(?-i)(\\z)";

     static DataExtractor getInstance() {
        return instance;
    }

     DataCarrier createTableData(String query) throws SQLException{
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(createTablePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            String[] data = mat.group(9).split(",");
            toBeReturn.tableName= mat.group(7);
            if(toBeReturn.tableName.equalsIgnoreCase("table")||
                    toBeReturn.tableName.equalsIgnoreCase("database")||
                    toBeReturn.tableName.equalsIgnoreCase("create")||
                    toBeReturn.tableName.equalsIgnoreCase("insert")||
                    toBeReturn.tableName.equalsIgnoreCase("select")||
                    toBeReturn.tableName.equalsIgnoreCase("delete")||
                    toBeReturn.tableName.equalsIgnoreCase("update")||
                    toBeReturn.tableName.equalsIgnoreCase("where")){
                throw new SQLException("Invalid Name");
            }
            toBeReturn.columns = new String[data.length];
            toBeReturn.columnsTypes = new String[data.length];
            Pattern isolateTwoWords = Pattern.compile("\\A\\s*(\\w+)\\s+(\\w+)\\s*\\z");
            Matcher isolateMatcher;
            for (int i = 0; i < data.length; i++) {
                isolateMatcher = isolateTwoWords.matcher(data[i]);
                if (isolateMatcher.matches()) {
                    toBeReturn.columns[i] = isolateMatcher.group(1).toLowerCase();
                    toBeReturn.columnsTypes[i] = isolateMatcher.group(2).equalsIgnoreCase("varchar") ? "string" : "int";
                }
            }
            return toBeReturn;
        }
        return null;
    }

     DataCarrier insertSomeData(String query) throws SQLException {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(insertSomePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            String[] columns = mat.group(10).split(",");
            String[] values = mat.group(23).split(",");
            toBeReturn.tableName = mat.group(7);
            if (columns.length != values.length) {
                throw new SQLException("Columns and values count doesn't match !");
            }
            toBeReturn.columns = new String[columns.length];
            toBeReturn.values = new String[columns.length];
            fillColumns(toBeReturn, columns);
            fillValues(toBeReturn, values);
            return toBeReturn;
        }
        return null;
    }
     DataCarrier createDBData(String query) throws SQLException{
        return DBData(query,createDBPattern);
    }
     DataCarrier dropDBData(String query) throws SQLException{
        return DBData(query,dropDBPattern);
    }
     DataCarrier dropTableData(String query){
        return dropDeleteTable(query, dropTablePattern);
    }
    private DataCarrier DBData(String query,String pattern) throws SQLException{
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.DBName = mat.group(7);
            if(toBeReturn.DBName.equalsIgnoreCase("table")||
                    toBeReturn.DBName.equalsIgnoreCase("database")||
                    toBeReturn.DBName.equalsIgnoreCase("create")||
                    toBeReturn.DBName.equalsIgnoreCase("insert")||
                    toBeReturn.DBName.equalsIgnoreCase("select")||
                    toBeReturn.DBName.equalsIgnoreCase("delete")||
                    toBeReturn.DBName.equalsIgnoreCase("update")||
                    toBeReturn.DBName.equalsIgnoreCase("where")){
                throw new SQLException("Invalid Name");
            }
            return toBeReturn;
        }
        return null;
    }

     DataCarrier insertAllData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(insertAllPattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            String[] data = mat.group(11).split(",");
            toBeReturn.tableName = mat.group(7);
            toBeReturn.values = new String[data.length];
            fillValues(toBeReturn, data);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier deleteAllData(String query) {
        return dropDeleteTable(query, deleteAllPattern);
    }

    private DataCarrier dropDeleteTable(String query, String deleteAllPattern) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(deleteAllPattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.tableName = mat.group(7);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier deleteSomeData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(deleteSomePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.tableName = mat.group(7);
            toBeReturn.conditionColumn = mat.group(11).toLowerCase();
            toBeReturn.conditionValue = mat.group(15);
            if (toBeReturn.conditionValue.charAt(0) == '\'') {
                toBeReturn.conditionValue = toBeReturn.conditionValue.substring(1, toBeReturn.conditionValue.length() - 1);
            }
            toBeReturn.conditionOperator = mat.group(13).charAt(0);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier selectAllData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(selectAllPattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.tableName = mat.group(8);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier selectAllWhereData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(selectAllWherePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.tableName = mat.group(8);
            toBeReturn.conditionColumn = mat.group(12).toLowerCase();
            toBeReturn.conditionValue = mat.group(16);
            if (toBeReturn.conditionValue.charAt(0) == '\'') {
                toBeReturn.conditionValue = toBeReturn.conditionValue.substring(1, toBeReturn.conditionValue.length() - 1);
            }
            toBeReturn.conditionOperator = mat.group(14).charAt(0);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier selectSomeData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(selectSomePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.tableName = mat.group(18);
            String[] data = mat.group(5).split(",");
            fillColumns(toBeReturn, data);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier selectSomeWhereData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(selectSomeWherePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            toBeReturn.tableName = mat.group(18);
            toBeReturn.conditionColumn = mat.group(22).toLowerCase();
            toBeReturn.conditionValue = mat.group(26);
            if (toBeReturn.conditionValue.charAt(0) == '\'') {
                toBeReturn.conditionValue = toBeReturn.conditionValue.substring(1, toBeReturn.conditionValue.length() - 1);
            }
            toBeReturn.conditionOperator = mat.group(24).charAt(0);
            String[] data = mat.group(5).split(",");
            fillColumns(toBeReturn, data);
            return toBeReturn;
        }
        return null;
    }

    private void updateData(DataCarrier toBeReturn, Matcher mat) {
        toBeReturn.tableName = mat.group(5);
        String[] rawData = mat.group(9).split(",");
        String[] columnData = new String[rawData.length];
        String[] valuesData = new String[rawData.length];
        toBeReturn.values = new String[rawData.length];
        toBeReturn.columns = new String[rawData.length];
        for (int i = 0; i < rawData.length; i++) {
            String[] str = rawData[i].split("=");
            columnData[i] = str[0];
            valuesData[i] = str[1];
        }
        fillColumns(toBeReturn, columnData);
        fillValues(toBeReturn, valuesData);
    }

     DataCarrier updateData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(updatePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            updateData(toBeReturn, mat);
            return toBeReturn;
        }
        return null;
    }

     DataCarrier updateWhereData(String query) {
        DataCarrier toBeReturn = new DataCarrier();
        Pattern pat = Pattern.compile(updateWherePattern);
        Matcher mat = pat.matcher(query);
        if (mat.matches()) {
            updateData(toBeReturn, mat);
            toBeReturn.conditionColumn = mat.group(32).toLowerCase();
            toBeReturn.conditionValue = mat.group(36);
            if (toBeReturn.conditionValue.charAt(0) == '\'') {
                toBeReturn.conditionValue = toBeReturn.conditionValue.substring(1, toBeReturn.conditionValue.length() - 1);
            }
            toBeReturn.conditionOperator = mat.group(34).charAt(0);
            return toBeReturn;
        }
        return null;
    }


    private void fillColumns(DataCarrier toBeReturn, String[] data) {
        Pattern wordWithoutSingleQuotes = Pattern.compile("\\A\\s*(\\w+)\\s*\\z");
        Matcher matcher;
        toBeReturn.columns = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            matcher = wordWithoutSingleQuotes.matcher(data[i]);
            if (matcher.matches()) {
                toBeReturn.columns[i] = matcher.group(1).toLowerCase();
            }
        }
    }


    private void fillValues(DataCarrier toBeReturn, String[] data) {
        Pattern withSingleQuotes = Pattern.compile("\\A\\s*[']([^']*)[']\\s*\\z");
        Pattern withoutSingleQuotes = Pattern.compile("\\A\\s*([0-9]+)\\s*\\z");
        for (int i = 0; i < data.length; i++) {

            Matcher matcher;
            matcher = withoutSingleQuotes.matcher(data[i]);
            if (matcher.matches()) {
                toBeReturn.values[i] = matcher.group(1);
            }
            matcher = withSingleQuotes.matcher(data[i]);
            if (matcher.matches()) {
                toBeReturn.values[i] = matcher.group(1);
            }
        }
    }


}
