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
        addPreviousData();

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

    // delete already distributed pairs (car number - driver license numbers)
    private static void addPreviousData() {
        carNumbers.removeIf(number -> number.equals("А111АА178") || number.equals("А222АА178") ||
                number.equals("В333ВВ178") || number.equals("С444СС178"));
        driverLicenseNumbers.removeIf(number -> number.equals("1111111111") ||
                number.equals("2222222222") || number.equals("3333333333") ||
                number.equals("4444444444"));
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
