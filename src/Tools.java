import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Tools {
    static Connection connect() {
        final String url = "jdbc:postgresql://localhost:5432/Logbook";
        Connection result = null;
        try {
             result = DriverManager.getConnection(url, "postgres", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert result != null;
        return result;
    }

    static void disconnect(Connection db) {
        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void executeUpdate(Connection db, String sql) {
        try {
            db.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static int getIntViaSQL(Connection db, String sql) {
        try {
            ResultSet rs = db.createStatement().executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static ArrayList<Integer> getArrayOfIntViaSQL(Connection db, String sql) {
        ArrayList<Integer> result = new ArrayList<>();
        try {
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next())
                result.add(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static ArrayList<String> getArrayOfStringViaSQL(Connection db, String sql) {
        ArrayList<String> result = new ArrayList<>();
        try {
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next())
                result.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static ArrayList<String> getFileContent(String filename) {
        ArrayList<String> result = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        }
        String line;
        try {
            line = reader.readLine();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}