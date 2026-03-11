package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
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
public class PetCategoryControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public PetCategoryControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePetCategorySuccessfullyReturnsHttp202Created() throws Exception {
        PetCategoryEntity petCategory = TestDataUtil.createTestPetCategoryA();
        petCategory.setPetCategoryId(null);
        String petCategoryJson = objectMapper.writeValueAsString(petCategory);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pet_categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePetCategorySuccessfullyReturnsSavedPetCategory() throws Exception {
        PetCategoryEntity petCategory = TestDataUtil.createTestPetCategoryA();
        petCategory.setPetCategoryId(null);
        String petCategoryJson = objectMapper.writeValueAsString(petCategory);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pet_categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petCategoryId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petType").value("cat")
        );
    }
}
