package com.spbu.mcs.ppl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class IronRoad {
    private Map<Time, List<Event>> events = new HashMap<>();
    private Function<Time, List<Event>> newList = t -> new LinkedList<>();

    IronRoad(List<String> commands) {
        Map<String, Train> trains = new HashMap<>();
        Map<String, Station> stations = new HashMap<>();
        for (String command : commands) {
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "добавить": {
                    switch (parts[1]) {
                        case "поезд": {
                            String name = parts[2];
                            String type = parts[3];
                            trains.put(name, new Train(name, type));
                            break;
                        }
                        case "станцию": {
                            String name = parts[2];
                            int roadsCount = Integer.parseInt(parts[3]);
                            stations.put(name, new Station(name, roadsCount));
                            break;
                        }
                    }
                    break;
                }
                case "установить": {
                    switch (parts[1]) {
                        case "вагоны": {
                            String trainName = parts[2];
                            int count = Integer.parseInt(parts[3]);
                            trains.get(trainName).setCarriages(count);
                            break;
                        }
                    }
                    break;
                }
                case "расписание": {
                    Train train = trains.get(parts[1]);
                    switch (parts[2]) {
                        case "старт": {
                            Station station = stations.get(parts[4]);
                            Time time = new Time(parts[3]);
                            addEvent(time.minus(30), new TrainPreparingEvent(train, station));
                            addEvent(time.minus(20), new RoadReservationEvent(train, station));
                            addEvent(time, new TrainStartedEvent(train, station));
                            addEvent(time.plus(5), new RoadFreeEvent(train, station));
                            break;
                        }
                        case "остановка": {
                            Station station = stations.get(parts[5]);
                            Time timeArrive = new Time(parts[3]);
                            Time timeStart = new Time(parts[4]);
                            addEvent(timeArrive.minus(20), new RoadReservationEvent(train, station));
                            addEvent(timeArrive.minus(10), new TrainIsArrivingEvent(train, station));
                            addEvent(timeArrive, new TrainArrivedEvent(train, station));
                            if (parts.length > 6) {
                                int deltaCarriages = Integer.parseInt(parts[6]);
                                addEvent(timeArrive, new ChangeCarriagesEvent(train, station, deltaCarriages));
                            }
                            addEvent(timeStart.minus(10), new TrainGetsStartedEvent(train, station));
                            addEvent(timeStart, new TrainStartedEvent(train, station));
                            addEvent(timeStart.plus(5), new RoadFreeEvent(train, station));
                            break;
                        }
                        case "конечная": {
                            Station station = stations.get(parts[4]);
                            Time time = new Time(parts[3]);
                            addEvent(time.minus(20), new RoadReservationEvent(train, station));
                            addEvent(time, new TrainArrivedEvent(train, station));
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    private void addEvent(Time time, Event event) {
        events.computeIfAbsent(time, newList).add(event);
    }

    Map<Time, List<Event>> getEvents() {
        return events;
    }
}
