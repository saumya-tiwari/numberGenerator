package com.assignment.numberGenerator;

import com.assignment.numberGenerator.controller.NumberGeneratorController;
import com.assignment.numberGenerator.controller.NumberGeneratorControllerAdvice;
import com.assignment.numberGenerator.domain.NumberGeneratorBulkRequest;
import com.assignment.numberGenerator.domain.NumberGeneratorRequest;
import com.assignment.numberGenerator.repository.NumberGeneratorRepository;
import com.assignment.numberGenerator.service.NumberGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class NumberGeneratorApplicationTests {

    private MockMvc mockMvc;

    @InjectMocks
    private NumberGeneratorController numberGeneratorController;
    @Spy
    private NumberGeneratorService numberGeneratorService;
    @Autowired
    private NumberGeneratorControllerAdvice numberGeneratorControllerAdvice;
    @Autowired
    private MappingJackson2HttpMessageConverter jackson;


    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(numberGeneratorController,"numberGeneratorService", numberGeneratorService);
        mockMvc = standaloneSetup(numberGeneratorController)
                .setControllerAdvice(numberGeneratorControllerAdvice).setMessageConverters(jackson).build();
    }

    @Test
    public void testGenerate() throws Exception {
        NumberGeneratorRequest numberGeneratorRequest = new NumberGeneratorRequest();
        numberGeneratorRequest.setGoal("10");
        numberGeneratorRequest.setStep("2");
        mockMvc.perform(
                post("/api/generate")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(numberGeneratorRequest)))
                .andExpect(status().isAccepted());

    }

    @Test
    public void testBulkGenerate() throws Exception {

        List<NumberGeneratorRequest> requests = new ArrayList<>();
        NumberGeneratorRequest request1 = new NumberGeneratorRequest();
        request1.setGoal("10");
        request1.setStep("2");
        NumberGeneratorRequest request2 = new NumberGeneratorRequest();
        request1.setGoal("100");
        request1.setStep("2");
        requests.add(request1);
        requests.add(request2);

        mockMvc.perform(
                post("/api/bulkGenerate")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsBytes(requests)))
                .andExpect(status().isAccepted());

    }

    @Test
    public void testGetStatus() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/tasks/{taskUUID}/status", "2a7b2a9f-6353-45fb-9c8e-a4c080a8003")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testGetBulkStatus() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/api/bulktasks/{taskUUID}/status", "fdd83375-2b34-440d-a085-98c2723c66ca,5d9de953-18d7-4c4e-8317-ab08f01c0ece")
                .accept(MediaType.APPLICATION_JSON).queryParam("action","get_numlist"))
                .andExpect(status().isAccepted());
    }

}
