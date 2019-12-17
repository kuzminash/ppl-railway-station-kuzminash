package com.spbu.mcs.ppl;

public class TrainIsArrivingEvent extends Event {
    private Road road;

    TrainIsArrivingEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public void emulate() {
        road = station.getRoad(train);
    }

    @Override
    public String toString() {
        return train + " прибудет на станцию " + station + " - " + road +
               " через 10 минут";
    }
}
