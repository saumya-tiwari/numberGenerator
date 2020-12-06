package com.assignment.numberGenerator.domain;

public class NumberGeneratorRequest {
    private String step;
    private String goal;


    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
