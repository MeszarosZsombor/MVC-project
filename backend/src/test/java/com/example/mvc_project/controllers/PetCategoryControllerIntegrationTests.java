package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.dto.PetCategoryDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.services.PetCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PetCategoryControllerIntegrationTests {

    private PetCategoryService petCategoryService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public PetCategoryControllerIntegrationTests(MockMvc mockMvc, PetCategoryService petCategoryService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.petCategoryService = petCategoryService;
    }

    @Test
    public void testThatCreatePetCategorySuccessfullyReturnsHttp201Created() throws Exception {
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
                MockMvcResultMatchers.jsonPath("$.petType").value("Cat")
        );
    }

    @Test
    public void testThatSamePetCategoryTypeCannotBeCreated() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity petCategoryEntityB = TestDataUtil.createTestPetCategoryA();

        petCategoryService.save(petCategoryEntityA);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> petCategoryService.save(petCategoryEntityB)
        );

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    public void testThatSamePetCategoryTypeAsUppercaseCannotBeCreated() {
        PetCategoryEntity petCategoryEntityA = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity petCategoryEntityB = TestDataUtil.createTestPetCategoryA();

        petCategoryEntityB.setPetType("cAt");

        petCategoryService.save(petCategoryEntityA);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> petCategoryService.save(petCategoryEntityB)
        );

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    public void testThatListAllPetCategoriesSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pet_categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAllPetCategorySuccessfullyReturnsListOfPetCategory() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        petCategoryService.save(testPetCategory);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pet_categories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].petCategoryId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].petType").value("Cat")
        );
    }

    @Test
    public void testThatGetPetCategorySuccessfullyReturnsHttp200WhenPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        petCategoryService.save(testPetCategory);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pet_categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetPetCategorySuccessfullyReturnsHttp404WhenNoPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        petCategoryService.save(testPetCategory);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pet_categories/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetPetCategorySuccessfullyReturnsPetCategoryWhenPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        petCategoryService.save(testPetCategory);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pet_categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petCategoryId").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petType").value("Cat")
        );
    }

    @Test
    public void testThatFullUpdatePetCategorySuccessfullyReturnsHttp200WhenPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategory = petCategoryService.save(testPetCategory);

        PetCategoryDto testPetCategoryDto = TestDataUtil.createTestPetCategoryDtoA();
        String petCategoryJson = objectMapper.writeValueAsString(testPetCategoryDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pet_categories/" + savedPetCategory.getPetCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdatePetCategorySuccessfullyReturnsHttp404WhenNoPetCategoryExists() throws Exception {
        PetCategoryDto testPetCategoryDto = TestDataUtil.createTestPetCategoryDtoA();
        String petCategoryJson = objectMapper.writeValueAsString(testPetCategoryDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pet_categories/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdatePetCategorySuccessfullyUpdatesPetCategoryWhenPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategory = petCategoryService.save(testPetCategory);

        PetCategoryDto testPetCategoryDto = TestDataUtil.createTestPetCategoryDtoB();
        testPetCategoryDto.setPetCategoryId(savedPetCategory.getPetCategoryId());
        String petCategoryJson = objectMapper.writeValueAsString(testPetCategoryDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pet_categories/" + savedPetCategory.getPetCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petCategoryId").value(savedPetCategory.getPetCategoryId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petType").value(testPetCategoryDto.getPetType())
        );
    }

    @Test
    public void testThatPartialUpdatePetCategorySuccessfullyReturnsHttp200WhenPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategory = petCategoryService.save(testPetCategory);

        PetCategoryDto testPetCategoryDto = TestDataUtil.createTestPetCategoryDtoA();
        testPetCategoryDto.setPetType("UPDATED");
        String petCategoryJson = objectMapper.writeValueAsString(testPetCategoryDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/pet_categories/" + savedPetCategory.getPetCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdatePetCategorySuccessfullyReturnsHttp400WhenNoPetCategoryExists() throws Exception {
        PetCategoryDto testPetCategoryDto = TestDataUtil.createTestPetCategoryDtoA();
        testPetCategoryDto.setPetType("UPDATED");
        String petCategoryJson = objectMapper.writeValueAsString(testPetCategoryDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/pet_categories/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdatePetCategorySuccessfullyReturnsUpdatedPetCategoryWhenPetCategoryExists() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategory = petCategoryService.save(testPetCategory);

        PetCategoryDto testPetCategoryDto = TestDataUtil.createTestPetCategoryDtoA();
        testPetCategoryDto.setPetType("UPDATED");
        String petCategoryJson = objectMapper.writeValueAsString(testPetCategoryDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/pet_categories/" + savedPetCategory.getPetCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petCategoryJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petCategoryId").value(savedPetCategory.getPetCategoryId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petType").value("UPDATED")
        );
    }

    @Test
    public void testThatDeletePetCategorySuccessfullyReturnsHttp204ForNonExistentPetCategory() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/pet_categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeletePetCategorySuccessfullyReturnsHttp204ForExistentPetCategory() throws Exception {
        PetCategoryEntity testPetCategory = TestDataUtil.createTestPetCategoryA();
        PetCategoryEntity savedPetCategory = petCategoryService.save(testPetCategory);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/pet_categories/" + savedPetCategory.getPetCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
