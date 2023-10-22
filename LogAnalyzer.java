/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David DiBenedetto, David J. Barnes and Michael Kölling.
 * @version 2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
    }

    /**
     * Create an object to analyze hourly web accesses.
     *
     * @param filename the name of the log file to analyze
     */
    public LogAnalyzer(String filename) {
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }

    /**
     * Return the busiest hour of the day, based on the number of web accesses.
     *
     * @return the busiest hour of the day
     */
    public int getBusiestHour() {
        int busiestHour = 0;
        int highestCount = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > highestCount) {
                busiestHour = hour;
                highestCount = hourCounts[hour];
            }
        }
        return busiestHour;
    }

    /**
     * Return the least busy hour of the day, based on the number of web accesses.
     *
     * @return the least busy hour of the day
     */
    public int getLeastBusyHour() {
        int leastBusyHour = 0;
        int lowestCount = Integer.MAX_VALUE;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < lowestCount) {
                leastBusyHour = hour;
                lowestCount = hourCounts[hour];
            }
        }
        return leastBusyHour;
    }

    /**
     * Return the total number of web accesses.
     *
     * @return the total number of web accesses
     */
    public int getTotalAccesses() {
        int totalAccesses = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            totalAccesses += hourCounts[hour];
        }
        return totalAccesses;
    }

    /**
     * Return the average number of web accesses per hour.
     *
     * @return the average number of web accesses per hour
     */
    public double getAverageAccessesPerHour() {
        double averageAccessesPerHour = (double) getTotalAccesses() / hourCounts.length;
        return averageAccessesPerHour;
    }

    public int busiestTwoHour() {
        int[] twoHourCounts = new int[12];
        for (int hour = 0; hour < hourCounts.length; hour++) {
            twoHourCounts[hour / 2] += hourCounts[hour];
        }

        int busiestTwoHour = 0;
        int highestCount = 0;
        for (int hour = 0; hour < twoHourCounts.length; hour++) {
            if (twoHourCounts[hour] > highestCount) {
                busiestTwoHour = hour;
                highestCount = twoHourCounts[hour];
            }
        }

        return busiestTwoHour;
    }

    /**
     * Return the month with the fewest accesses.
     *
     * @return the month with the fewest accesses
     */
    public int quietestMonth() {
        int[] monthCounts = new int[12];

        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();

            monthCounts[month - 1]++;
        }

        int quietestMonth = 0;
        int lowestCount = Integer.MAX_VALUE;
        for (int month = 0; month < monthCounts.length; month++) {
            if (monthCounts[month] < lowestCount) {
                quietestMonth = month;
                lowestCount = monthCounts[month];
            }
        }

        return quietestMonth + 1;
    }

    /**
     * Return the month with the highest number of accesses.
     *
     * @return the month with the highest number of accesses
     */
    public int busiestMonth() {
        int[] monthCounts = new int[12];

        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();

            monthCounts[month - 1]++;
        }

        int busiestMonth = 0;
        int highestCount = 0;
        for (int month = 0; month < monthCounts.length; month++) {
            if (monthCounts[month] > highestCount) {
                busiestMonth = month;
                highestCount = monthCounts[month];
            }
        }

        return busiestMonth + 1;
    }

    /**
     * Return the average number of accesses per month.
     *
     * @return the average number of accesses per month
     */
    public double averageAccessesPerMonth() {
        int[] monthCounts = new int[12];

        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();

            monthCounts[month - 1]++;
        }

        int totalAccesses = 0;
        for (int month = 0; month < monthCounts.length; month++) {
            totalAccesses += monthCounts[month];
        }

        double averageAccessesPerMonth = (double) totalAccesses / monthCounts.length;
        return averageAccessesPerMonth;
    }

}
