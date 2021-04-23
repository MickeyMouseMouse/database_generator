import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OwnerToCarGenerator {
    private static ArrayList<String> carNumbers;
    private static ArrayList<String> driverLicenseNumbers;

    public static void generate(List<Integer> personsOnCar,
                                List<Integer> CarsOnPerson) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        carNumbers = Tools.getArrayOfStringViaSQL(db, "SELECT car_number FROM Car_Numbers;");
        driverLicenseNumbers = Tools.getArrayOfStringViaSQL(db, "SELECT driver_license_number FROM Owners;");

        for (Integer persons : personsOnCar) {
            String number = getCarNumber();
            for (int j = 0; j < persons; j++) {
                String person = getDriverLicenseNumber();
                cmd.append("INSERT INTO Owner_to_car(car_number, driver_license_number) VALUES ('")
                        .append(number).append("','")
                        .append(person).append("') ON CONFLICT DO NOTHING;");
            }
        }

        for (Integer cars : CarsOnPerson) {
            String person = getDriverLicenseNumber();
            for (int j = 0; j < cars; j++) {
                String number = getCarNumber();
                cmd.append("INSERT INTO Owner_to_car(car_number, driver_license_number) VALUES ('")
                        .append(number).append("','")
                        .append(person).append("') ON CONFLICT DO NOTHING;");
            }
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }

    private static String getCarNumber() {
        int index = (int) (carNumbers.size() * Math.random());
        String result = carNumbers.get(index);
        carNumbers.remove(index);
        return result;
    }

    private static String getDriverLicenseNumber() {
        int index = (int) (driverLicenseNumbers.size() * Math.random());
        String result = driverLicenseNumbers.get(index);
        driverLicenseNumbers.remove(index);
        return result;
    }
}
