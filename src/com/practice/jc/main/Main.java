package com.practice.jc.main;

import java.util.Random;

import com.practice.jc.weather.WeatherAnalysis;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();

        String[] day = new String[]{"2010-05-10", "2010-05-11", "2010-05-12", "2010-05-13", "2010-05-14",
                "2010-05-15", "2010-05-16"};

        double[] morningWeather = new double[day.length];
        double[] lunchtimeWeather = new double[day.length];
        double[] eveningWeather = new double[day.length];

        double[] averageTemp;

        for (int i = 0; i < day.length; i++) {
            morningWeather[i] = rand.nextInt(15);
            lunchtimeWeather[i] = rand.nextInt(25);
            eveningWeather[i] = rand.nextInt(19);
        }

        averageTemp = WeatherAnalysis.averageTemperature(morningWeather, lunchtimeWeather, eveningWeather);
        WeatherAnalysis.showWeather(day, averageTemp);
        WeatherAnalysis.printPeriodOfIncrease(day, averageTemp);
    }

}
