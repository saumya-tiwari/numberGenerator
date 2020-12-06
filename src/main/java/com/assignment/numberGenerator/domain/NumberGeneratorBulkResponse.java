package com.assignment.numberGenerator.domain;

import java.util.ArrayList;
import java.util.List;

public class NumberGeneratorBulkResponse {
    private List<NumberGeneratorResponse> generatorResponses = new ArrayList<>();

    public List<NumberGeneratorResponse> getGeneratorResponses() {
        return generatorResponses;
    }

    public void setGeneratorResponses(List<NumberGeneratorResponse> generatorResponses) {
        this.generatorResponses = generatorResponses;
    }
}
