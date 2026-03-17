package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.services.AdoptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AdoptionControllerIntegrationTests {

    private AdoptionService adoptionService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AdoptionControllerIntegrationTests(MockMvc mockMvc, AdoptionService adoptionService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.adoptionService = adoptionService;
    }

    @Test
    public void testThatCreateAdoptionSuccessfullyReturnsHttp201Created() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        testAdoptionEntity.setAdoptionId(null);
        String ownerJson = objectMapper.writeValueAsString(testAdoptionEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ownerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAdoptionSuccessfullyReturnsSavedAdoption() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        testAdoptionEntity.setAdoptionId(null);
        String ownerJson = objectMapper.writeValueAsString(testAdoptionEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ownerJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").isNumber()
        );
    }

    @Test
    public void testThatListAllAdoptionsSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAllAdoptionsSuccessfullyReturnsListOfAdoptions() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        adoptionService.save(testAdoptionEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].adoptionId").isNumber()
        );
    }

    @Test
    public void testThatGetAdoptionSuccessfullyReturnsHttp200WhenAdoptionExists() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        adoptionService.save(testAdoptionEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAdoptionSuccessfullyReturnsHttp404WhenNoAdoptionExists() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        adoptionService.save(testAdoptionEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAdoptionSuccessfullyReturnsAdoptionWhenAdoptionExists() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        adoptionService.save(testAdoptionEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").value(1)
        );
    }
}
