package com.spbu.mcs.ppl;

import java.util.Objects;

public class Time {
    private int h;
    private int m;

    Time(int h, int m) {
        this.h = h;
        this.m = m;
    }
    Time(String s) {
        String[] time = s.split(":");
        this.h = Integer.parseInt(time[0]);
        this.m = Integer.parseInt(time[1]);
    }
    Time minus(int minutes) {
        return plus(-minutes);
    }
    Time plus(int minutes) {
        int t = h * 60 + m + minutes;
        return new Time(t / 60, t % 60);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", h, m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return h == time.h &&
                m == time.m;
    }

    @Override
    public int hashCode() {
        return Objects.hash(h, m);
    }
}
