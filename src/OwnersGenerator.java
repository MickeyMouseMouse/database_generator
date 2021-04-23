import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OwnersGenerator {
    public static void generate(int quantity) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        ArrayList<String> persons = Tools.getFileContent("resources/persons.txt");
        for(int i = 0; i < persons.size(); i++) {
            if (i == quantity) break;
            String[] parts = (persons.get((int)(persons.size() * Math.random()))).split(" ");
            cmd.append("INSERT INTO Owners(driver_license_number, name, surname, patronymic) VALUES ('")
                    .append(getDriverLicenseNumber()).append("','")
                    .append(parts[1]).append("','")
                    .append(parts[0]).append("','")
                    .append(parts[2]).append("') ON CONFLICT DO NOTHING;");
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }

    public static List<String> letters = Arrays.asList("Q", "W", "E", "R", "T",
            "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K",
            "L", "Z", "X", "C", "V", "B", "N", "M");
    public static String getDriverLicenseNumber() {
        StringBuilder number = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            if (Math.random() > 0.5)
                number.append(letters.get((int) (letters.size() * Math.random())));
            else
                number.append((int) (9 * Math.random()));
        }
        return number.toString();
    }
}
