package com.spbu.mcs.ppl;

public class TrainArrivedEvent extends Event {
    private Road road;

    TrainArrivedEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public void emulate() {
        road = station.getRoad(train);
    }

    @Override
    public String toString() {
        return train + " прибыл на станцию " + station + " - " + road;
    }
}
