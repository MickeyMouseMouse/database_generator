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
            final Statement st = db.createStatement();
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static int getIntViaSQL(Connection db, String sql) {
        int result = 0;
        try {
            final Statement st = db.createStatement();
            final ResultSet rs = st.executeQuery(sql);
            rs.next();
            result = rs.getInt(1);
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static ArrayList<Integer> getArrayOfIntViaSQL(Connection db, String sql) {
        final ArrayList<Integer> result = new ArrayList<>();
        try {
            final Statement st = db.createStatement();
            final ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getInt(1));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static ArrayList<String> getArrayOfStringViaSQL(Connection db, String sql) {
        final ArrayList<String> result = new ArrayList<>();
        try {
            final Statement st = db.createStatement();
            final ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    static ArrayList<String> getFileContent(String filename) {
        final ArrayList<String> result = new ArrayList<>();
        final BufferedReader reader;
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