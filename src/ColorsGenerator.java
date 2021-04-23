import java.sql.Connection;
import java.util.ArrayList;

public class ColorsGenerator {
    public static void generate(int quantity) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        ArrayList<String> colors = Tools.getFileContent("resources/colors.txt");
        for(int i = 0; i < colors.size(); i++) {
            if (i == quantity) break;
            cmd.append("INSERT INTO Colors(color_name) VALUES ('")
                    .append(colors.get(i))
                    .append("') ON CONFLICT DO NOTHING;");
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }
}
