package com.assignment.numberGenerator.service;

import com.assignment.numberGenerator.domain.*;
import com.assignment.numberGenerator.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NumberGeneratorService {

    NumberGeneratorResponse generate(NumberGeneratorRequest numberGeneratorRequest) throws ApiException;

    NumberGeneratorBulkResponse bulkGenerate(List<NumberGeneratorRequest> numberGeneratorRequest) throws ApiException;

    NumberGeneratorStatusResponse getStatus(String uuid) throws ApiException;

    NumberGeneratorStatusResponse getNumberList(String uuid) throws ApiException;

    NumberGeneratorBulkStatusResponse getBulkNumberList(String uuidList) throws ApiException;

}
