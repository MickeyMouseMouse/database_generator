import java.sql.Connection;
import java.util.ArrayList;

public class ParkingLogbookGenerator {
    private static final ArrayList<CarsOnParking> carsOnParking = new ArrayList<>();
    private static ArrayList<Integer> freePlaces;

    public static void generate(String currentDate, int days) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        ArrayList<Integer> workingShifts = Tools.getArrayOfIntViaSQL(db, "SELECT working_shift_id FROM Working_Shifts;");
        freePlaces = Tools.getArrayOfIntViaSQL(db, "SELECT place_id FROM Places;");
        addPreviousData();

        DateTime dt = new DateTime(currentDate, "00:00:00");
        for(int i = 0; i < days; i++) {
            for(int j = 0; j < 3; j++) { // 3 working shifts per day
                for(int k = 0; k < 8 * 6 - 1; k++) { // (8 * 6 - 1) events per shift
                    dt.getNextTime(10);

                    int owner_to_car_id = getOwnerToCarID(db);
                    boolean entry_exit = false; // false = entry; true = exit
                    for(CarsOnParking car : carsOnParking) {
                        if (car.owner_to_car_id == owner_to_car_id) { // the car pulled out from parking
                            cmd.append("INSERT INTO Parking_Logbook(working_shift_id, log_date, log_time, owner_to_car_id, entry_exit, place_id, tariff_id) VALUES ('")
                                    .append(workingShifts.get(3 * i + j)).append("','")
                                    .append(dt.getCurrentDate()).append("','")
                                    .append(dt.getCurrentTime()).append("','")
                                    .append(owner_to_car_id).append("','")
                                    .append(true).append("','")
                                    .append(car.place_id).append("','")
                                    .append(car.tariff_id).append("') ON CONFLICT DO NOTHING;");
                            entry_exit = true;
                            freePlaces.add(car.place_id);
                            carsOnParking.remove(car);
                            break;
                        }
                    }

                    if (!entry_exit) { // the car arrived at the parking
                        int place = getPlaceID(db);
                        int tariff = getTariffID(db);
                        carsOnParking.add(new CarsOnParking(owner_to_car_id, place, tariff));
                        cmd.append("INSERT INTO Parking_Logbook(working_shift_id, log_date, log_time, owner_to_car_id, entry_exit, place_id, tariff_id) VALUES ('")
                                .append(workingShifts.get(3 * i + j)).append("','")
                                .append(dt.getCurrentDate()).append("','")
                                .append(dt.getCurrentTime()).append("','")
                                .append(owner_to_car_id).append("','")
                                .append(false).append("','")
                                .append(place).append("','")
                                .append(tariff).append("') ON CONFLICT DO NOTHING;");
                    }
                }
                dt.getNextTime(10);
            }
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }

    private static void addPreviousData() {
        carsOnParking.add(new CarsOnParking(3, 2, 2));
        carsOnParking.add(new CarsOnParking(1, 3, 2));
        freePlaces.removeIf(i -> i == 2 || i == 3);
    }

    private static int getPlaceID(Connection db) {
        while (true) {
            int result = Tools.getIntViaSQL(db, "SELECT place_id FROM Places ORDER BY RANDOM() LIMIT 1;");
            for(Integer i : freePlaces) {
                if (i == result) {
                    freePlaces.remove(i);
                    return result;
                }
            }
        }
    }

    private static int getTariffID(Connection db) {
        return Tools.getIntViaSQL(db, "SELECT tariff_id FROM Tariffs ORDER BY RANDOM() LIMIT 1;");
    }

    private static int getOwnerToCarID(Connection db) {
        return Tools.getIntViaSQL(db, "SELECT owner_to_car_id FROM Owner_to_car ORDER BY RANDOM() LIMIT 1;");
    }
}

class CarsOnParking {
    public int owner_to_car_id, place_id, tariff_id;
    CarsOnParking(int owner_to_car_id, int place_id, int tariff_id) {
        this.owner_to_car_id = owner_to_car_id;
        this.place_id = place_id;
        this.tariff_id = tariff_id;
    }
}