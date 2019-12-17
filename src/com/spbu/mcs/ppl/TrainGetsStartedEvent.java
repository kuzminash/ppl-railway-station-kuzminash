package com.spbu.mcs.ppl;


public class TrainGetsStartedEvent extends Event {
    private Road road;

    TrainGetsStartedEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public void emulate() {
        road = station.getRoad(train);
    }

    @Override
    public String toString() {
        return train + " отправляется со станции " + station +
               " - " + road + " через 10 мин";
    }
}
