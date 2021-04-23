import java.sql.Connection;
import java.util.ArrayList;

public class SecurityGuardsGenerator {
    public static void generate(int quantity) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        ArrayList<String> persons = Tools.getFileContent("resources/persons.txt");
        for(int i = 0; i < persons.size(); i++) {
            if (i == quantity) break;
            String[] parts = (persons.get((int)(persons.size() * Math.random()))).split(" ");
            cmd.append("INSERT INTO Security_Guards(name, surname, patronymic) VALUES ('")
                    .append(parts[1]).append("','")
                    .append(parts[0]).append("','")
                    .append(parts[2]).append("') ON CONFLICT DO NOTHING;");
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }
}
