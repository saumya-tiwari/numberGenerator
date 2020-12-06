package com.assignment.numberGenerator.domain;

import java.util.ArrayList;
import java.util.List;

public class NumberGeneratorBulkStatusResponse {

    private List<String> result = new ArrayList<>();

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
