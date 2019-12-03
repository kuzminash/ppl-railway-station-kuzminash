package com.spbu.mcs.ppl;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class App {
    private static Random random = new Random();

    public static void main(String[] args) {
        JSONObject data = ScheduleReader.getAllData("schedule.json");
        HashMap<String, Train> trains = ScheduleReader.getTrains(data);
        HashMap<String, Station> stations = ScheduleReader.getStations(data);
        for (int hour = 0; hour <= 23; hour++) {
            for (int minute = 0; minute <= 59; minute++) {
                System.out.println(String.format("%02d:%02d", hour, minute));
                printEventsByTime(hour * 60 + minute, trains, stations);
            }
        }
    }

    public static String getLateTime(String time) {
        if (random.nextDouble() >= 0.15) {
            return time;
        }
        String[] t = time.split(":");
        int timestamp = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]) + random.nextInt(7);
        return String.format("%02d:%02d", timestamp / 60, timestamp % 60);
    }

    private static void printEventsByTime(int timestamp,
                                          HashMap<String,Train> trains, HashMap<String, Station> stations) {
        for (Map.Entry<String, Station> e : stations.entrySet()) {
            // назначение путей для прибывающих поездов
            printArrivingTrains(e.getValue(), trains, timestamp + 10);
            // назначение путей для формируемых поездов
            printStartingTrains(e.getValue(), trains, timestamp + 30);
            // прибытие поездов
            printArrivedTrains(e.getValue(), trains, timestamp);
            // отправка поездов
            printStartedTrains(e.getValue(), trains, timestamp);
            // прицепление вагонов
            printAddingCarriage(e.getValue(), trains, timestamp);
            // отцепление вагонов
            printRemovingCarriage(e.getValue(), trains, timestamp);
        }
    }

    private static void printRemovingCarriage(Station station, HashMap<String, Train> trains, int timestamp) {
        String time = String.format("%02d:%02d", timestamp / 60, timestamp % 60);
        for (Map.Entry<String, Train> train : trains.entrySet()) {
            Train t = train.getValue();
            String[] route = t.getRoute();
            String[] aTimes = t.getArrivingTime();
            for (int i = 0; i < route.length; i++) {
                if (route[i].equals(station.getName()) && aTimes[i].equals(time)) {
                    int[] removeCarriage = station.removeCarriage(t);
                    if (removeCarriage != null && removeCarriage.length != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(removeCarriage[0]);
                        for (int j = 1; j < removeCarriage.length; j++) {
                            sb.append(", ").append(removeCarriage[j]);
                        }
                        System.out.println("Станция: " + station.getName());
                        System.out.println("- Отцепление вагонов: От поезда " + train.getKey() + " отцеплены вагоны " + sb);
                    }
                }
            }
        }
    }

    private static void printAddingCarriage(Station station, HashMap<String, Train> trains, int timestamp) {
        String time = String.format("%02d:%02d", timestamp / 60, timestamp % 60);
        for (Map.Entry<String, Train> train : trains.entrySet()) {
            Train t = train.getValue();
            String[] route = t.getRoute();
            String[] aTimes = t.getArrivingTime();
            for (int i = 0; i < route.length; i++) {
                if (route[i].equals(station.getName()) && aTimes[i].equals(time)) {
                    int[] addCarriage = station.addCarriage(t);
                    if (addCarriage != null && addCarriage.length != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(addCarriage[0]);
                        for (int j = 1; j < addCarriage.length; j++) {
                            sb.append(", ").append(addCarriage[j]);
                        }
                        System.out.println("Станция: " + station.getName());
                        System.out.println("- Прицепление вагонов: К поезду " + train.getKey() + " прицеплены вагоны " + sb);
                    }
                }
            }
        }
    }

    private static void printStartedTrains(Station station, HashMap<String, Train> trains, int timestamp) {
        String time = String.format("%02d:%02d", timestamp / 60, timestamp % 60);
        for (Map.Entry<String, Train> train : trains.entrySet()) {
            Train t = train.getValue();
            String[] route = t.getRoute();
            String[] sTimes = t.getStartTime();
            String[] fsTimes = t.getFactStartTime();
            for (int i = 0; i < route.length; i++) {
                if (route[i].equals(station.getName())) {
                    int sCmp = sTimes[i].compareTo(time);
                    int fsCmp = fsTimes[i].compareTo(time);
                    if (sCmp <= 0 && fsCmp > 0 || fsCmp == 0) {
                        int[] way = station.getWayByTrain(t);
                        System.out.println("Станция: " + station.getName());
                        if (fsCmp == 0) {
                            System.out.println("- Поезд отправляется: " + t.getTrainType() + " " + train.getKey() + " отправляется с пути " +
                                    way[1] + " (платформа " + way[0] + ")");
                            station.free(t);
                        }
                        else {
                            System.out.println("- Задержка отправки: " + t.getTrainType() + " " + train.getKey() + " не отправился с пути " +
                                    way[1] + " (платформа " + way[0] + ")");
                        }
                    }
                }
            }
        }
    }

    private static void printArrivedTrains(Station station, HashMap<String, Train> trains, int timestamp) {
        String time = String.format("%02d:%02d", timestamp / 60, timestamp % 60);
        for (Map.Entry<String, Train> train : trains.entrySet()) {
            Train t = train.getValue();
            String[] route = t.getRoute();
            String[] aTimes = t.getArrivingTime();
            String[] faTimes = t.getFactArrivingTime();
            for (int i = 0; i < route.length; i++) {
                if (route[i].equals(station.getName())) {
                    int aCmp = aTimes[i].compareTo(time);
                    int faCmp = faTimes[i].compareTo(time);
                    if (aCmp <= 0 && faCmp > 0 || faCmp == 0) {
                        int[] way = station.getWayByTrain(t);
                        System.out.println("Станция: " + station.getName());
                        if (faCmp == 0) {
                            System.out.println("- Прибыл поезд: " + t.getTrainType() + " " + train.getKey() + " прибыл на путь " +
                                    way[1] + " (платформа " + way[0] + ")");
                        }
                        else {
                            System.out.println("- Задержка прибытия: " + t.getTrainType() + " " + train.getKey() + " не прибыл на путь " +
                                    way[1] + " (платформа " + way[0] + ")");
                        }
                    }
                }
            }
        }
    }

    private static void printArrivingTrains(Station station, HashMap<String,Train> trains, int timestamp) {
        String time = String.format("%02d:%02d", timestamp / 60, timestamp % 60);
        for (Map.Entry<String, Train> train : trains.entrySet()) {
            Train t = train.getValue();
            String[] route = t.getRoute();
            String[] aTimes = t.getArrivingTime();
            for (int i = 1; i < route.length; i++) {
                if (route[i].equals(station.getName()) && aTimes[i].equals(time)) {
                    int[] freeWay = station.getFreeWay(t);
                    System.out.println("Станция: " + station.getName());
                    System.out.println("- Назначен путь: " + t.getTrainType() + " " + train.getKey() + " прибудет в " + time +
                            " на путь " + freeWay[1] + " (платформа " + freeWay[0] + ")");
                }
            }
        }
    }
    private static void printStartingTrains(Station station, HashMap<String,Train> trains, int timestamp) {
        String time = String.format("%02d:%02d", timestamp / 60, timestamp % 60);
        for (Map.Entry<String, Train> train : trains.entrySet()) {
            Train t = train.getValue();
            if (t.getRoute()[0].equals(station.getName()) && t.getArrivingTime()[0].equals(time)) {
                int[] freeWay = station.getFreeWay(t);
                System.out.println("Станция: " + station.getName());
                System.out.println("- Назначен путь: " + t.getTrainType() + " " + train.getKey() + " стартует в " + time + " на пути " +
                        freeWay[1] + " (платформа " + freeWay[0] + ")");
            }
        }
    }
}
