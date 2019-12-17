package com.spbu.mcs.ppl;

public class RoadReservationEvent extends Event {
    private Road road;

    RoadReservationEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public void emulate() {
        road = station.reserveRoad(train);
    }

    @Override
    public String toString() {
        return "назначен путь " + road + ": " + train + " на станции " + station;
    }
}
