package com.spbu.mcs.ppl;

public class TrainStartedEvent extends Event {
    private Road road;

    TrainStartedEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public void emulate() {
        road = station.getRoad(train);
    }

    @Override
    public String toString() {
        return train + " отправлен со станции " + station + " - " + road;
    }
}
