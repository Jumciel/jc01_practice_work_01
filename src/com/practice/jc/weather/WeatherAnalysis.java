package com.practice.jc.weather;

public class WeatherAnalysis {

    private WeatherAnalysis() {

    }

    public static double[] averageTemperature(double[] morning, double[] lunch, double[] evening) {

        if (morning == null || lunch == null || evening == null || morning.length != lunch.length
                || morning.length != evening.length) {
            printNoDataMessage();
            return null;
        }

        double[] averageTemp = new double[morning.length];

        for (int i = 0; i < averageTemp.length; i++) {
            averageTemp[i] = (morning[i] + lunch[i] + evening[i]) / 3;
        }

        return averageTemp;
    }

    public static void showWeather(String[] day, double[] averageTemp) {
        System.out.println("\t\tAverage temperature\n");
        for (int i = 0; i < day.length; i++) {
            System.out.println(day[i] + " ---> " + String.format("%.2f", averageTemp[i]) + "°C");
        }
    }

    private static Period findPeriodOfIncrease(double[] averageTemp) {

        if (averageTemp.length < 2) {
            printNoDataMessage();
        }

        int firstDayIncrease = 0;
        int lastDayIncrease = 0;
        int period = 0;

        for (int i = 0; i < averageTemp.length - 1; i++) {
            int currentPeriodLength = 0;
            if (averageTemp[i] < averageTemp[i + 1]) {
                int j = i;

                while (averageTemp[j] < averageTemp[j + 1]) {
                    j++;
                    currentPeriodLength++;
                    if (j == averageTemp.length - 1) {
                        break;
                    }
                }
                if (currentPeriodLength > period) {
                    period = currentPeriodLength;
                    firstDayIncrease = i;
                    lastDayIncrease = j;
                }
                i = j;
            }
        }
        return new Period(period, firstDayIncrease, lastDayIncrease);
    }

    private static Period[] findSamePeriod(double[] averageTemp) {

        if (averageTemp.length < 2) {
            printNoDataMessage();
        }

        int firstDayIncrease = 0;
        int lastDayIncrease = 0;
        int period = 0;
        int periodIndex = 0;

        int maxNumberOfPeriods = averageTemp.length / 2;
        Period[] periods = new Period[maxNumberOfPeriods];


        for (int i = 0; i < averageTemp.length - 1; i++) {
            int currentPeriodLength = 0;

            if (averageTemp[i] < averageTemp[i + 1]) {
                int j = i;

                while (averageTemp[j] < averageTemp[j + 1]) {
                    j++;
                    currentPeriodLength++;
                    if (j == averageTemp.length - 1) {
                        break;
                    }
                }
                if (currentPeriodLength == findPeriodOfIncrease(averageTemp).length && findPeriodOfIncrease(averageTemp).getStartIncrease() != i) {
                    period = currentPeriodLength;
                    firstDayIncrease = i;
                    lastDayIncrease = j;
                    periods[periodIndex] = new Period(period, firstDayIncrease, lastDayIncrease);
                    periodIndex++;
                }
                i = j;
            }
        }
        return periods;
    }

    public static void printPeriodOfIncrease(String[] day, double[] averageTemperature) {
        if (averageTemperature == null || day == null || averageTemperature.length < 2 || day.length != averageTemperature.length) {
            printNoDataMessage();
            return;
        }

        Period increasePeriod = findPeriodOfIncrease(averageTemperature);
        Period[] samePeriodOfIncrease = findSamePeriod(averageTemperature);

        if (increasePeriod.length > 0) {
            printPeriodOfIncrease(increasePeriod.getLength(), day[increasePeriod.getStartIncrease()],
                    day[increasePeriod.getEndIncrease()]);
        } else {
            printNoIncreasePeriod();
        }
        for (Period period : samePeriodOfIncrease) {
            if (period != null) {
                printSamePeriodOfIncrease(day[period.getStartIncrease()], day[period.getEndIncrease()]);
            } else {
                return;
            }
        }
    }

    private static void printNoDataMessage() {
        throw new IllegalArgumentException("Not enough data");
    }

    private static void printNoIncreasePeriod() {
        throw new RuntimeException("No increase period");
    }

    private static void printPeriodOfIncrease(int period, String startIncrease, String endIncrease) {
        System.out.println("The longest period of increase: " + period + " days");
        System.out.println("From " + startIncrease + " to " + endIncrease);
    }

    private static void printSamePeriodOfIncrease(String startIncrease, String endIncrease) {
        System.out.println("The same longest period of increase:");
        System.out.println("From " + startIncrease + " to " + endIncrease);
    }

    private static class Period {
        private final int length;
        private final int startIncrease;
        private final int endIncrease;

        Period(int length, int startIncrease, int endIncrease) {
            this.length = length;
            this.startIncrease = startIncrease;
            this.endIncrease = endIncrease;
        }

        public int getLength() {
            return length;
        }

        public int getStartIncrease() {
            return startIncrease;
        }

        public int getEndIncrease() {
            return endIncrease;
        }

    }
}
