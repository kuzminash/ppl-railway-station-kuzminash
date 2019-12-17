package com.spbu.mcs.ppl;

public class TrainPreparingEvent extends Event {
    TrainPreparingEvent(Train train, Station station) {
        super(train, station);
    }

    @Override
    public String toString() {
        return train + " отправляется со станции " + station + " через 30 мин";
    }
}
