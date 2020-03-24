package eg.edu.alexu.csd.oop.db.parser;

public class QV {
    private Parser ValidReadQuery;
    private Parser ValidStructure;
    private Parser ValidUpdateQuery;

    public QV() {
        ValidReadQuery = new ValidReadQuery();
        ValidStructure = new ValidStructure();
        ValidUpdateQuery = new ValidUpdateQuery();
    }

    public int validReadQuery(String query) {
        return ValidReadQuery.isValid(query);
    }

    public int validStructure(String query) {
        return ValidStructure.isValid(query);
    }

    public int validUpdateQuery(String query) {
        return ValidUpdateQuery.isValid(query);
    }

    public int isValidQuery(String query) {
        return Math.max(Math.max(validReadQuery(query), validStructure(query)), validUpdateQuery(query));
    }

}

