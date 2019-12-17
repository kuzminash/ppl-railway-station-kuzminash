package com.spbu.mcs.ppl;

public class Station {
    private String name;
    private Road[] roads;

    public Station(String name, int roadsCount) {
        this.name = name;
        this.roads = new Road[roadsCount];
        for (int i = 0; i < roadsCount; i++) {
            roads[i] = new Road(i + 1, (i + 2) / 2);
        }
    }

    Road reserveRoad(Train train) {
        for (Road road : roads) {
            if (road.isFree()) {
                road.setTrain(train);
                return road;
            }
        }
        throw new RuntimeException("Нет свободных дорог");
    }

    Road freeRoad(Train train) {
        Road road = getRoad(train);
        road.setTrain(null);
        return road;
    }

    @Override
    public String toString() {
        return name;
    }

    Road getRoad(Train train) {
        for (Road road : roads) {
            if (road.getTrain().equals(train)) {
                return road;
            }
        }
        throw new RuntimeException("Поезд не занимал этот путь");
    }
}
