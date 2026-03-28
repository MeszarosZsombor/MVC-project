package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.dto.PetDto;
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
        petService.save(testPetEntity);
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

    @Test
    public void testThatGetPetSuccesfullyReturnsHttp200WhenPetExists() throws Exception {
        PetEntity testPetEntity = TestDataUtil.createTestPetB(null);
        petService.save(testPetEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetPetSuccesfullyReturnsHttp404WhenNoPetExists() throws Exception {
        PetEntity testPetEntity = TestDataUtil.createTestPetB(null);
        petService.save(testPetEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/pets/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetPetSuccesfullyReturnsPetWhenPetExists() throws Exception {
        PetEntity testPetEntity = TestDataUtil.createTestPetB(null);
        petService.save(testPetEntity);
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
        PetEntity testPetEntity = TestDataUtil.createTestPetA(null);
        PetEntity savedPetEntity = petService.save(testPetEntity);

        PetDto testPetDto = TestDataUtil.createTestPetDtoA(null);
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/" + savedPetEntity.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdatePetSuccesfullyReturnsHttp404WhenNoPetExists() throws Exception {
        PetDto testPetDto = TestDataUtil.createTestPetDtoB(null);
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
        PetEntity testPetEntityA = TestDataUtil.createTestPetA(null);
        PetEntity savedPetEntity = petService.save(testPetEntityA);

        PetDto testPetDto = TestDataUtil.createTestPetDtoA(null);
        testPetDto.setPetId(savedPetEntity.getPetId());
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/" + savedPetEntity.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petId").value(savedPetEntity.getPetId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petName").value(testPetDto.getPetName())
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
    public void testThatPartialUpdatePetSuccesfullyReturnsHttp200WhenPetExists() throws Exception {
        PetEntity testPetEntity = TestDataUtil.createTestPetA(null);
        PetEntity savedPetEntity = petService.save(testPetEntity);

        PetDto testPetDto = TestDataUtil.createTestPetDtoA(null);
        testPetDto.setPetName("UPDATED");
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/pets/" + savedPetEntity.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdatePetSuccesfullyReturnsHttp400WhenNoPetExists() throws Exception {
        PetDto testPetDto = TestDataUtil.createTestPetDtoA(null);
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
        PetEntity testPetEntityA = TestDataUtil.createTestPetA(null);
        PetEntity savedPetEntity = petService.save(testPetEntityA);

        PetDto testPetDto = TestDataUtil.createTestPetDtoA(null);
        testPetDto.setPetName("UPDATED");
        String petJson = objectMapper.writeValueAsString(testPetDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/pets/" + savedPetEntity.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petId").value(savedPetEntity.getPetId())
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
        PetEntity testPetEntity = TestDataUtil.createTestPetA(null);
        PetEntity savedPetEntity = petService.save(testPetEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/pets/" + savedPetEntity.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
