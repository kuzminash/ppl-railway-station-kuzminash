package com.spbu.mcs.ppl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static List<String> getCommands(String filename) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            return null;
        }
        LinkedList<String> commands = new LinkedList<>();
        while (sc.hasNext()) {
            commands.add(sc.nextLine());
        }
        sc.close();
        return commands;
    }

    public static void main(String[] args) {
        List<String> commands = getCommands("input.txt");

        if (commands == null) {
            System.out.println("Не удалось прочитать файл с командами");
            return;
        }

        IronRoad ironRoad = new IronRoad(commands);

        Map<Time, List<Event>> events = ironRoad.getEvents();

        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                Time time = new Time(h, m);
                System.out.println(time);
                List<Event> timeEvents = events.get(time);
                if (timeEvents != null) {
                    for (Event e : timeEvents) {
                        e.emulate();
                        System.out.println(e);
                    }
                }
            }
        }
    }
}
