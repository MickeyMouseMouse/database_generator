import java.sql.Connection;

public class PlacesGenerator {
    public static void generate(int numberOfFloors, int numberOfSectors, int numberOfPlaces) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        for(int i = 1; i <= numberOfFloors; i++)
            for(int j = 1; j <= numberOfSectors; j++)
                for(int k = 1; k <= numberOfPlaces; k++)
                    cmd.append("INSERT INTO Places(place_floor, place_sector, place_number) VALUES ('")
                            .append(i).append("','")
                            .append(j).append("','")
                            .append(k)
                            .append("') ON CONFLICT DO NOTHING;");

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }
}
