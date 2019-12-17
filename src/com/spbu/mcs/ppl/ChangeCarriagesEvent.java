package com.spbu.mcs.ppl;

public class ChangeCarriagesEvent extends Event {
    private int deltaCarriages;
    ChangeCarriagesEvent(Train train, Station station, int deltaCarriages) {
        super(train, station);
        this.deltaCarriages = deltaCarriages;
    }

    @Override
    public void emulate() {
        train.setCarriages(train.getCarriages() + deltaCarriages);
    }

    @Override
    public String toString() {
        return train + " - " +
               (deltaCarriages >= 0 ? "прикреплено" : "откреплено") +
                " вагонов: " + Math.abs(deltaCarriages);
    }
}
