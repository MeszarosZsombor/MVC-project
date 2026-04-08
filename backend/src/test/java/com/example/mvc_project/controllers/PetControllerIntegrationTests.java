package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.services.PetCategoryService;
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
    private PetCategoryService petCategoryService;

    @Autowired
    public PetControllerIntegrationTests(MockMvc mockMvc, PetService petService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.petService = petService;
    }

    @Test
    public void testThatCreatePetSuccesfullyReturnsHttp201Created() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto pet = TestDataUtil.createTestPetDtoA();
        pet.setPetCategoryId(petCategory.getPetCategoryId());
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
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto pet = TestDataUtil.createTestPetDtoB();
        pet.setPetCategoryId(petCategory.getPetCategoryId());
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
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoB();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        petService.save(petDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].petId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].petName").value("Doggo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].weight").value(15)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].age").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].gender").value("F")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].adopted").value(true)
        );
    }

    @Test
    public void testThatGetPetSuccesfullyReturnsHttp200WhenPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        petService.save(petDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetPetSuccesfullyReturnsHttp404WhenNoPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        petService.save(petDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetPetSuccesfullyReturnsPetWhenPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoB();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        petService.save(petDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
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
    public void testThatFullUpdatePetSuccesfullyReturnsHttp200WhenPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity savedPet = petService.save(petDto);

        PetDto petDtoB = TestDataUtil.createTestPetDtoB();
        petDtoB.setPetCategoryId(petCategory.getPetCategoryId());
        String petJson = objectMapper.writeValueAsString(petDtoB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/" + savedPet.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdatePetSuccesfullyReturnsHttp404WhenNoPetExists() throws Exception {
        PetDto testPetDto = TestDataUtil.createTestPetDtoB();
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdatePetSuccesfullyUpdatesPetWhenPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity savedPet = petService.save(petDto);

        PetDto petDtoB = TestDataUtil.createTestPetDtoB();
        petDtoB.setPetId(savedPet.getPetId());
        petDtoB.setPetCategoryId(petCategory.getPetCategoryId());
        String petJson = objectMapper.writeValueAsString(petDtoB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/" + savedPet.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petId").value(savedPet.getPetId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petName").value(petDtoB.getPetName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.weight").value(petDtoB.getWeight())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(petDtoB.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.gender").value(petDtoB.getGender())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adopted").value(petDtoB.getAdopted())
        );
    }

    @Test
    public void testThatPartialUpdatePetSuccesfullyReturnsHttp200WhenPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity savedPet = petService.save(petDto);

        PetDto petDtoB = TestDataUtil.createTestPetDtoB();
        petDtoB.setPetName("UPDATED");
        petDtoB.setPetCategoryId(petCategory.getPetCategoryId());
        String petJson = objectMapper.writeValueAsString(petDtoB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/" + savedPet.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdatePetSuccesfullyReturnsHttp400WhenNoPetExists() throws Exception {
        PetDto testPetDto = TestDataUtil.createTestPetDtoA();
        testPetDto.setPetName("UPDATED");
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdatePetSuccesfullyUpdatesPetWhenPetExists() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity savedPet = petService.save(petDto);

        PetDto testPetDto = TestDataUtil.createTestPetDtoA();
        testPetDto.setPetName("UPDATED");
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/pets/" + savedPet.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petId").value(savedPet.getPetId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petName").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.weight").value(testPetDto.getWeight())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testPetDto.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.gender").value(testPetDto.getGender())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adopted").value(testPetDto.getAdopted())
        );
    }

    @Test
    public void testThatDeletePetSuccesfullyReturnsHttp204ForNonExistentPet() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/pets/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeletePetSuccesfullyReturnsHttp204ForExistentPet() throws Exception {
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity savedPet = petService.save(petDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/pets/" + savedPet.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
