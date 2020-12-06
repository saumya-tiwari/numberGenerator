package com.assignment.numberGenerator.service;

import com.assignment.numberGenerator.domain.*;
import com.assignment.numberGenerator.exception.ApiException;
import com.assignment.numberGenerator.exception.NoDataFoundException;
import com.assignment.numberGenerator.repository.NumberGeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.assignment.numberGenerator.constants.Constants.API_ERROR_CODE;
import static com.assignment.numberGenerator.constants.Constants.API_ERROR_CODE_MSG;

@Service
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

    @Autowired
    private NumberGeneratorRepository numberGeneratorRepository;

    @Override
    public NumberGeneratorResponse generate(NumberGeneratorRequest numberGeneratorRequest) throws ApiException {
        try {
            NumberGeneratorRequestWrapper generatorRequestWrapper = new NumberGeneratorRequestWrapper();
            generatorRequestWrapper.setStep(numberGeneratorRequest.getStep());
            generatorRequestWrapper.setGoal(numberGeneratorRequest.getGoal());
            NumberGeneratorResponse numberGeneratorResponse = new NumberGeneratorResponse();
            List<Integer> numList = new ArrayList<>();

            getNumber(generatorRequestWrapper, numList);

            generatorRequestWrapper.setNumList(numList);
            generatorRequestWrapper.setStatusEnum(NumberGeneratorRequestWrapper.StatusEnum.SUCCESS);
            NumberGeneratorRequestWrapper numberGeneratorRequest1 = numberGeneratorRepository.save(generatorRequestWrapper);

            numberGeneratorResponse.setTask(String.valueOf(numberGeneratorRequest1.getId()));

            return numberGeneratorResponse;
        }catch (Exception ex){
            throw new ApiException(API_ERROR_CODE_MSG, API_ERROR_CODE,ex);
        }
    }

    @Override
    public NumberGeneratorBulkResponse bulkGenerate(List<NumberGeneratorRequest> numberGeneratorRequest) throws ApiException {
        try {
            List<NumberGeneratorRequestWrapper> numberGeneratorRequestWrappers = new ArrayList<>();
            NumberGeneratorBulkResponse numberGeneratorResponse = new NumberGeneratorBulkResponse();
            List<NumberGeneratorResponse> numberGeneratorResponses = new ArrayList<>();

            for (NumberGeneratorRequest generatorRequest : numberGeneratorRequest) {
                NumberGeneratorRequestWrapper numberGeneratorRequestWrapper = new NumberGeneratorRequestWrapper();
                numberGeneratorRequestWrapper.setGoal(generatorRequest.getGoal());
                numberGeneratorRequestWrapper.setStep(generatorRequest.getStep());
                List<Integer> numList = new ArrayList<>();
                getNumber(numberGeneratorRequestWrapper, numList);
                numberGeneratorRequestWrapper.setNumList(numList);
                numberGeneratorRequestWrapper.setStatusEnum(NumberGeneratorRequestWrapper.StatusEnum.SUCCESS);
                numberGeneratorRequestWrappers.add(numberGeneratorRequestWrapper);
            }

            List<NumberGeneratorRequestWrapper> requests = numberGeneratorRepository.saveAll(numberGeneratorRequestWrappers);
            for (NumberGeneratorRequestWrapper generatorRequest : requests) {
                NumberGeneratorResponse response = new NumberGeneratorResponse();
                numberGeneratorResponses.add(response);
                response.setTask(String.valueOf(generatorRequest.getId()));
            }
            numberGeneratorResponse.setGeneratorResponses(numberGeneratorResponses);
            return numberGeneratorResponse;
        }catch (Exception ex){
            throw new ApiException(API_ERROR_CODE_MSG, API_ERROR_CODE,ex);
        }
    }

    private void getNumber(NumberGeneratorRequestWrapper numberGeneratorRequest, List<Integer> numList) {
        int number = Integer.parseInt(numberGeneratorRequest.getGoal());

        int step = Integer.parseInt(numberGeneratorRequest.getStep());

        numList.add(number);

        while (number > 0) {
            number -= step;
            if(number >= 0)
                numList.add(number);
        }
    }

    @Override
    public NumberGeneratorStatusResponse getStatus(String uuid) throws ApiException {
        try {
            NumberGeneratorStatusResponse numberGeneratorResponse = new NumberGeneratorStatusResponse();
            NumberGeneratorRequestWrapper numberGeneratorRequest = numberGeneratorRepository.findById(UUID.fromString(uuid)).orElseThrow(NoDataFoundException::new);
            if (Objects.nonNull(numberGeneratorRequest))
                numberGeneratorResponse.setResult(numberGeneratorRequest.getStatusEnum().name());
            else
                throw new NoDataFoundException();
            return numberGeneratorResponse;
        }catch (Exception ex){
            throw new ApiException(API_ERROR_CODE_MSG, API_ERROR_CODE,ex);
        }
    }

    @Override
    public NumberGeneratorStatusResponse getNumberList(String uuid) throws ApiException {
        try {
            NumberGeneratorStatusResponse numberGeneratorResponse = new NumberGeneratorStatusResponse();
            NumberGeneratorRequestWrapper numberGeneratorRequest = numberGeneratorRepository.findById(UUID.fromString(uuid)).orElseThrow(NoDataFoundException::new);
            if (Objects.nonNull(numberGeneratorRequest))
                numberGeneratorResponse.setResult(numberGeneratorRequest.getNumList().toString());
            else
                throw new NoDataFoundException();
            return numberGeneratorResponse;
        }catch (Exception ex){
            throw new ApiException(API_ERROR_CODE_MSG, API_ERROR_CODE,ex);
        }
    }

    @Override
    public NumberGeneratorBulkStatusResponse getBulkNumberList(String uuidList) throws ApiException {
        try {
            NumberGeneratorBulkStatusResponse numberGeneratorBulkStatusResponse = new NumberGeneratorBulkStatusResponse();
            List<UUID> uuidLst = new ArrayList<>();
            Arrays.stream(uuidList.split(",")).forEach(s -> uuidLst.add(UUID.fromString(s)));
            List<NumberGeneratorRequestWrapper> numberGeneratorRequestList = numberGeneratorRepository.findAllById(uuidLst);

            List<String> resultList = new ArrayList<>();
            for (NumberGeneratorRequestWrapper numberGeneratorRequestWrapper : numberGeneratorRequestList) {
                resultList.add(numberGeneratorRequestWrapper.getNumList().toString());
            }
            if (CollectionUtils.isEmpty(numberGeneratorRequestList))
                throw new NoDataFoundException();
            numberGeneratorBulkStatusResponse.setResult(resultList);
            return numberGeneratorBulkStatusResponse;
        }catch (Exception ex){
            throw new ApiException(API_ERROR_CODE_MSG, API_ERROR_CODE,ex);
        }
    }
}
