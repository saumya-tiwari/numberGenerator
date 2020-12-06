package com.assignment.numberGenerator.controller;


import com.assignment.numberGenerator.domain.*;
import com.assignment.numberGenerator.exception.ApiException;
import com.assignment.numberGenerator.service.NumberGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "NumberGenerator", description = "the number Generator API")
public class NumberGeneratorController {

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    @Operation(summary = "generates a sequence of numbers", description = "generates a sequence of numbers in the decreasing order till 0", tags = { "NumberGenerator" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = NumberGeneratorResponse.class)))})
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generate(@Valid @RequestBody NumberGeneratorRequest numberGeneratorRequest) throws ApiException {
        NumberGeneratorResponse numberGeneratorResponse = numberGeneratorService.generate(numberGeneratorRequest);
        return ResponseEntity.accepted().body(numberGeneratorResponse);
    }

    @Operation(summary = "gets status of the task", description = "returns the status of the task if success, in progress or error and list of generated numbers" +
            "if query param action is provided", tags = { "NumberGenerator" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = NumberGeneratorStatusResponse.class)))})
    @GetMapping(value = "/tasks/{taskUUID}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getStatus(@PathVariable("taskUUID")String taskUUID,@RequestParam(required = false) String action) throws ApiException {
        NumberGeneratorStatusResponse numberGeneratorResponse = null;
        if(action != null && action.equalsIgnoreCase("get_numlist"))
            numberGeneratorResponse = numberGeneratorService.getNumberList(taskUUID);
        else
            numberGeneratorResponse = numberGeneratorService.getStatus(taskUUID);
        return ResponseEntity.accepted().body(numberGeneratorResponse);
    }

    @Operation(summary = "for bulk generation of sequence of numbers", description = "for bulk generation of sequence of numbers", tags = { "NumberGenerator" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = NumberGeneratorBulkResponse.class)))})
    @PostMapping(value = "/bulkGenerate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bulkGenerate(@Valid @RequestBody final List<NumberGeneratorRequest> numberGeneratorRequest) throws ApiException {
        NumberGeneratorBulkResponse numberGeneratorResponse = numberGeneratorService.bulkGenerate(numberGeneratorRequest);
        return ResponseEntity.accepted().body(numberGeneratorResponse);
    }

    @Operation(summary = "to get status of bulk generation", description = "returns the list of generated numbers for the bulk insert task", tags = { "NumberGenerator" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = NumberGeneratorBulkResponse.class)))})
    @GetMapping(value = "/bulktasks/{taskUUID}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity submission(@PathVariable("taskUUID")String taskUUID,@RequestParam String action) throws ApiException {
        NumberGeneratorBulkStatusResponse numberGeneratorResponse;
        numberGeneratorResponse = numberGeneratorService.getBulkNumberList(taskUUID);

        return ResponseEntity.accepted().body(numberGeneratorResponse);
    }

}
