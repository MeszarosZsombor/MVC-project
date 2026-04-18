package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.services.AdoptionService;
import com.example.mvc_project.services.OwnerService;
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
@AutoConfigureMockMvc(addFilters = false)
public class AdoptionControllerIntegrationTests {

    private AdoptionService adoptionService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private PetService petService;
    @Autowired
    private PetCategoryService petCategoryService;

    @Autowired
    public AdoptionControllerIntegrationTests(MockMvc mockMvc, AdoptionService adoptionService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.adoptionService = adoptionService;
    }

    @Test
    public void testThatCreateAdoptionSuccessfullyReturnsHttp201Created() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        String ownerJson = objectMapper.writeValueAsString(testAdoptionDto);
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
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        String ownerJson = objectMapper.writeValueAsString(testAdoptionDto);
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
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        adoptionService.save(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].adoptionId").isNumber()
        );
    }

    @Test
    public void testThatGetAdoptionSuccessfullyReturnsHttp200WhenAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        adoptionService.save(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAdoptionSuccessfullyReturnsHttp404WhenNoAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        adoptionService.save(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAdoptionSuccessfullyReturnsAdoptionWhenAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        adoptionService.save(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").value(1)
        );
    }

    @Test
    public void testThatFullUpdateAdoptionSuccessfullyReturnsHttp200WhenAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionDto);

        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAdoptionSuccessfullyReturnsHttp404WhenNoAdoptionExists() throws Exception {
        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/adoptions/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAdoptionSuccessfullyUpdatesAdoptionWhenAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDtoA = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDtoA.setOwnerId(owner.getOwnerId());
        testAdoptionDtoA.setPetId(pet.getPetId());
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionDtoA);

        AdoptionDto testAdoptionDtoB = TestDataUtil.createTestAdoptionDtoB();
        testAdoptionDtoB.setAdoptionId(savedAdoption.getAdoptionId());
        testAdoptionDtoB.setOwnerId(owner.getOwnerId());
        testAdoptionDtoB.setPetId(pet.getPetId());
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDtoB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").value(savedAdoption.getAdoptionId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.petId").value(testAdoptionDtoA.getPetId())
        );
    }

    @Test
    public void testThatPartialUpdateAdoptionSuccessfullyReturnsHttp200WhenAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionDto);

        OwnerEntity ownerB = TestDataUtil.createTestOwnerB();
        OwnerEntity savedOwnerB = ownerService.save(ownerB);

        testAdoptionDto.setOwnerId(savedOwnerB.getOwnerId());
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAdoptionSuccessfullyReturnsHttp400WhenNoAdoptionExists() throws Exception {
        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerB();
        ownerService.save(testOwnerEntity);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(testOwnerEntity.getOwnerId());
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/adoptions/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateAdoptionSuccessfullyUpdatesAdoptionWhenAdoptionExists() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionDto);

        OwnerEntity ownerB = TestDataUtil.createTestOwnerB();
        OwnerEntity savedOwnerB = ownerService.save(ownerB);

        testAdoptionDto.setOwnerId(savedOwnerB.getOwnerId());
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").value(savedAdoption.getAdoptionId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.ownerId").value(testAdoptionDto.getOwnerId())
        );
    }

    @Test
    public void testThatDeleteAdoptionSuccessfullyReturnsHttp402ForNonExistentAdoption() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/adoptions/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAdoptionSuccessfullyReturnsHttp402ForExistentAdoption() throws Exception {
        OwnerEntity owner = ownerService.save(TestDataUtil.createTestOwnerA());
        PetCategoryEntity petCategory = petCategoryService.save(TestDataUtil.createTestPetCategoryA());
        PetDto petDto = TestDataUtil.createTestPetDtoA();
        petDto.setPetCategoryId(petCategory.getPetCategoryId());
        PetEntity pet = petService.save(petDto);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA();
        testAdoptionDto.setOwnerId(owner.getOwnerId());
        testAdoptionDto.setPetId(pet.getPetId());
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
