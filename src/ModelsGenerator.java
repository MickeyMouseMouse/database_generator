import java.sql.Connection;
import java.util.ArrayList;

public class ModelsGenerator {
    public static void generate(int quantity) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        ArrayList<String> models = Tools.getFileContent("resources/models.txt");
        for(int i = 0; i < models.size(); i++) {
            if (i == quantity) break;
            cmd.append("INSERT INTO Models(model_name) VALUES ('")
                    .append(models.get(i))
                    .append("') ON CONFLICT DO NOTHING;");
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }
}
