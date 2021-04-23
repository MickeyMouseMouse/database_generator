import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class WorkingShiftsGenerator {
    public static void generate(String currentDate, int days) {
        Connection db = Tools.connect();
        StringBuilder cmd = new StringBuilder();

        List<String> workingHours = Arrays.asList("00:00:00", "07:59:59",
                "08:00:00", "15:59:59", "16:00:00", "23:59:59");
        DateTime dt = new DateTime(currentDate, "00:00:00");
        for(int i = 0; i < days; i++) {
            for(int j = 0; j < 5; j += 2)
                cmd.append("INSERT INTO Working_Shifts(work_date_start, time_start, work_date_end, time_end, guard_id) VALUES ('")
                        .append(currentDate).append("','")
                        .append(workingHours.get(j)).append("','")
                        .append(currentDate).append("','")
                        .append(workingHours.get(j + 1)).append("','")
                        .append(getGuardID(db)).append("') ON CONFLICT DO NOTHING;");
            currentDate = dt.getNextDate();
        }

        Tools.executeUpdate(db, cmd.toString());
        Tools.disconnect(db);
    }

    private static int getGuardID(Connection db) {
        return Tools.getIntViaSQL(db, "SELECT guard_id FROM Security_Guards ORDER BY RANDOM() LIMIT 1;");
    }
}
