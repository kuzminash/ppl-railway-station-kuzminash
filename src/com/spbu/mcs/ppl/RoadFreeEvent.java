package com.spbu.mcs.ppl;

public class RoadFreeEvent extends Event {
    private Road road;

    RoadFreeEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public void emulate() {
        road = station.freeRoad(train);
    }

    @Override
    public String toString() {
        return train + " освободил " + road + " на станции " + station;
    }
}
