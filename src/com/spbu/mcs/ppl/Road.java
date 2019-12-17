package com.spbu.mcs.ppl;

public class Road {
    private Train train = null;
    private int number;
    private int platform;

    Road(int number, int platform) {
        this.number = number;
        this.platform = platform;
    }

    boolean isFree() {
        return train == null;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public String toString() {
        return number + " путь (платформа " + platform + ")";
    }

    public Train getTrain() {
        return train;
    }
}
