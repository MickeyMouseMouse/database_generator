import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlacesGenerator {
    final static List<Integer> existingPlaces = Arrays.asList(111, 112, 113,
            114, 115, 121, 122, 123, 124, 125, 211, 212, 213, 221, 222, 223);
    public static void generate(int numberOfFloors, int numberOfSectors, int numberOfPlaces) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        for(int i = 1; i <= numberOfFloors; i++)
            for(int j = 1; j <= numberOfSectors; j++)
                for(int k = 1; k <= numberOfPlaces; k++) {
                    if (existingPlaces.contains(100 * i + 10 * j + k)) continue;
                    cmd.append("INSERT INTO Places(place_floor, place_sector, place_number) VALUES ('")
                            .append(i).append("','")
                            .append(j).append("','")
                            .append(k)
                            .append("') ON CONFLICT DO NOTHING;");
                }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }
}
