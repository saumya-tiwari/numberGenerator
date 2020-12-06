package com.assignment.numberGenerator.domain;

import java.util.ArrayList;
import java.util.List;

public class NumberGeneratorBulkRequest {

    private List<NumberGeneratorRequest> numberGeneratorRequests = new ArrayList<>();

    public List<NumberGeneratorRequest> getNumberGeneratorRequests() {
        return numberGeneratorRequests;
    }

    public void setNumberGeneratorRequests(List<NumberGeneratorRequest> numberGeneratorRequests) {
        this.numberGeneratorRequests = numberGeneratorRequests;
    }
}
