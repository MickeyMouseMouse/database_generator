import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int ALL_ITEMS = Integer.MAX_VALUE;
        final String CURRENT_DATE = "2021-05-01";
        final int DURATION = 31;

        // add model names from a file
        ModelsGenerator.generate(ALL_ITEMS);

        // add color names from a file
        ColorsGenerator.generate(ALL_ITEMS);

        // add security persons with full names from a file
        SecurityGuardsGenerator.generate(5);

        // generate car numbers and also colors and models
        CarNumbersGenerator.generate(40);

        // generate driver's license numbers and also full names of people
        OwnersGenerator.generate(25);

        // generate working shifts starting from the specified date
        // for the specified number of days
        WorkingShiftsGenerator.generate(CURRENT_DATE, DURATION);

        // generate records that map owners to their cars
        // the first array defines the number of persons who drive one car
        // the second array defines the number of cars that belong to one person
        OwnerToCarGenerator.generate(Arrays.asList(1, 2, 3, 2, 1, 1, 1, 1, 1, 1),
                Arrays.asList(2, 3, 2, 4, 2, 2, 2, 2, 5, 2)); // total: 36 cars, 24 persons

        // generate the parking spaces
        // args: number of floors, number of sectors per floor, number of places per sector
        PlacesGenerator.generate(3, 3, 5); // total: 45 places

        // generate the main table
        // records start from the specified date and last the specified number of days
        ParkingLogbookGenerator.generate(CURRENT_DATE, DURATION);
    }
}