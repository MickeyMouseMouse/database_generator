import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class CarNumbersGenerator {
    public static void generate(int quantity) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        for(int i = 0; i < quantity; i++) {
            cmd.append("INSERT INTO Car_Numbers(car_number, model_id, color_id) VALUES ('")
                    .append(getCarNumber()).append("','")
                    .append(getModelID(db)).append("','")
                    .append(getColorID(db)).append("') ON CONFLICT DO NOTHING;");
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }

    private static final List<String> letters = Arrays.asList("А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х");
    private static String getCarNumber() {
        return letters.get((int) (letters.size() * Math.random())) +
                (int) (9 * Math.random()) +
                (int) (9 * Math.random()) +
                (int) (9 * Math.random()) +
                letters.get((int) (letters.size() * Math.random())) +
                letters.get((int) (letters.size() * Math.random())) +
                (int) (9 * Math.random()) +
                (int) (9 * Math.random()) +
                (int) (9 * Math.random());
    }

    private static int getModelID(Connection db) {
        return Tools.getIntViaSQL(db, "SELECT model_id FROM Models ORDER BY RANDOM() LIMIT 1;");
    }

    private static int getColorID(Connection db) {
        return Tools.getIntViaSQL(db, "SELECT color_id FROM Colors ORDER BY RANDOM() LIMIT 1;");
    }
}
