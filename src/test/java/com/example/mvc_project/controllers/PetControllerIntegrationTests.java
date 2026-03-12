package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.PetEntity;
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
public class PetControllerIntegrationTests {

    public MockMvc mockMvc;
    public ObjectMapper objectMapper;

    @Autowired
    public PetControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePetSuccesfullyReturnsHttp201Created() throws Exception {
        PetEntity pet = TestDataUtil.createTestPetA(null);
        pet.setPetId(null);
        String petJson = objectMapper.writeValueAsString(pet);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePetSuccesfullyReturnsSavedPet() throws Exception {
        PetEntity pet = TestDataUtil.createTestPetB(null);
        pet.setPetId(null);
        String petJson = objectMapper.writeValueAsString(pet);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petName").value("Doggo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.weight").value(15)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.gender").value("F")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adopted").value(true)
        );
    }
}
