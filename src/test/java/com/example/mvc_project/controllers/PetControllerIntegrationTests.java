package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.services.PetService;
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

    private PetService petService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public PetControllerIntegrationTests(MockMvc mockMvc, PetService petService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.petService = petService;
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

    @Test
    public void testThatListAllPetsSuccesfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAllPetsSuccesfullyReturnsListOfPets() throws Exception {
        PetEntity testPetEntity = TestDataUtil.createTestPetB(null);
        petService.createPet(testPetEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].petId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].petName").value("Doggo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].weight").value(15)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].gender").value("F")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].adopted").value(true)
        );
    }
}
