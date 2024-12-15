package com.example.dentistapplication;

import java.util.List;

public class Doctor {
    private String name;
    private List<String> times;

    public Doctor(String name, List<String> times) {
        this.name = name;
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public List<String> getTimes() {
        return times;
    }

}
