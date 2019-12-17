package com.spbu.mcs.ppl;

public abstract class Event {
    Train train;
    Station station;

    public Event(Train train, Station station) {
        this.train = train;
        this.station = station;
    }

    public void emulate() {

    }

    @Override
    public abstract String toString();
}
