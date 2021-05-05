public class DateTime {
    private int year, month, day, hour, minute, second; // initial date and time
    private final int[] numberOfDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    DateTime(String date, String time) {
        String[] d = date.split("-");
        String[] t = time.split(":");

        this.year = Integer.parseInt(d[0]);
        this.month = Integer.parseInt(d[1]);
        this.day = Integer.parseInt(d[2]);
        this.hour = Integer.parseInt(t[0]);
        this.minute = Integer.parseInt(t[1]);
        this.second = Integer.parseInt(t[2]);
    }

    public String getCurrentTime() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getNextTime(int plusMinutes) {
        minute += plusMinutes;
        if (minute >= 60) {
            minute %= 60;
            if (++hour == 24) {
                hour = 0;
                if (++day == numberOfDays[month - 1] + 1) {
                    day = 1;
                    if (++month == 13) {
                        month = 1;
                        year++;
                    }
                }
            }
        }
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getCurrentDate() {
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public String getNextDate() {
        if (++day == numberOfDays[month - 1] + 1) {
            day = 1;
            if (++month == 13) {
                month = 1;
                year++;
            }
        }

        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
