package telran.time;

import java.time.LocalDate;
import java.time.Month;

record MonthYear(int month, int year) {}

public class Main {
    public static void main(String[] args) {
        try {
            MonthYear monthYear = getMonthYear(args);
            printCalendar(monthYear);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printDates(MonthYear monthYear) {
        int lastDay = getLastDayOfMonth(monthYear);
        int firstDayOfWeek = getFirstDayOfWeek(monthYear);
        int offset = getOffset(firstDayOfWeek);

        for (int i = 0; i < offset; i++) {
            System.out.print("    ");
        }
        for (int day = 1; day <= lastDay; day++) {
            System.out.printf("%3d ", day);
            if ((day + offset) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void printWeekDays() {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String weekday: weekDays) {
            System.out.printf("%4s", weekday);
        }
        System.out.println();
    }

    private static void printTitle(MonthYear monthYear) {
        String monthName = Month.of(monthYear.month()).name();
        System.out.printf("%n        %s %d%n", monthName, monthYear.year());
        System.out.println("-----------------------------");
    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        int month = Integer.parseInt(args[0]);
        int year = Integer.parseInt(args[1]);
        MonthYear monthYear = new MonthYear(month, year);
        if (args.length == 0) {
            LocalDate now = LocalDate.now();
            monthYear = new MonthYear(now.getMonthValue(), now.getYear());
        }

        if (args.length != 2) {
            throw new Exception("Please provide exactly two arguments: <month> <year>");
        }

        if (month < 1 || month > 12) {
            throw new Exception("Month must be between 1 and 12.");
        }

        return monthYear;
    }

    private static int getFirstDayOfWeek(MonthYear monthYear) {
        LocalDate date = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return date.getDayOfWeek().getValue(); 
    }

    private static int getOffset(int firstWeekDay) {
        return (firstWeekDay % 7); 
    } 

    private static int getLastDayOfMonth(MonthYear monthYear) {
        LocalDate date = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return date.lengthOfMonth(); 
    }
}
