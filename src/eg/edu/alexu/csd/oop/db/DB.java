package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

 class DB {
    private File directory;
    private ArrayList<Table> tables;

     DB(String name, File parentDirectory) {
        tables = new ArrayList<>();
        directory = new File(parentDirectory, name);
        directory.mkdir();

    }

     String getName() {
        return directory.getName();
    }

     boolean addTable(DataCarrier carrier) throws SQLException {
        if (tableExist(carrier.tableName)) {
            return false;
        } else {
            tables.add(new Table(directory, carrier));
            return true;
        }

    }

     boolean tableExist(String name) {
        for (Table t : tables) {
            if (name.equalsIgnoreCase(t.getName())) {
                return true;
            }
        }
        return false;
    }

     boolean deleteTable(DataCarrier carrier) {
        if (tableExist(carrier.tableName)) {
            int index = getTableIndex(carrier.tableName);
            Table temp = tables.get(index);
            tables.remove(index);
            return temp.deleteTable();
        }
        return false;
    }

     int getTableIndex(String name) {
        int i = 0;
        for (Table table : tables) {
            if (table.getName().equalsIgnoreCase(name))
                return i;
            i++;
        }
        return -1;
    }

     ArrayList<Table> getTables() {
        return tables;
    }

     String getPath(){
        return directory.getPath();
    }
     private void deleteFile(File f){
         if(f.delete())
             return;
         File[] list= f.listFiles();
         if (list!=null){
             for (File inside:list
             ) {
                 deleteFile(inside);
             }
         }

     }
}