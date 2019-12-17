package com.spbu.mcs.ppl;

public class Train {
    private String name;
    private String type;
    private int carriages;

    public Train(String name, String type) {
        this.name = name;
        this.type = type;
    }

    void setCarriages(int count) {
        if (carriages < 0) {
            throw new RuntimeException("Количество вагонов не может быть отрицательным");
        }
        carriages = count;
    }

    @Override
    public String toString() {
        return type + " поезд " + name;
    }

    int getCarriages() {
        return carriages;
    }
}
