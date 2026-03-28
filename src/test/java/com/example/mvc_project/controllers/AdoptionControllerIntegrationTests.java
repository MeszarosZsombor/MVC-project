package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.services.AdoptionService;
import com.example.mvc_project.services.OwnerService;
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
    private OwnerService ownerService;

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

    @Test
    public void testThatFullUpdateAdoptionSuccessfullyReturnsHttp200WhenAdoptionExists() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionEntity);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA(null, null);
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
        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA(null, null);
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
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionEntity);

        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoB(null, null);
        testAdoptionDto.setAdoptionId(savedAdoption.getAdoptionId());
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").value(savedAdoption.getAdoptionId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.pet").value(testAdoptionDto.getPet())
        );
    }

    @Test
    public void testThatPartialUpdateAdoptionSuccessfullyReturnsHttp200WhenAdoptionExists() throws Exception {
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionEntity);

//        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerB();
//        ownerService.save(testOwnerEntity);
//        OwnerDto testOwnerDto = TestDataUtil.createTestOwnerDtoB();
//        testOwnerDto.setName("UPDATED");


        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA(null, null);
       // testAdoptionDto.setOwner(testOwnerDto);
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
//        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerB();
//        ownerService.save(testOwnerEntity);
//        OwnerDto testOwnerDto = TestDataUtil.createTestOwnerDtoB();
//        testOwnerDto.setName("UPDATED");


        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA(null, null);
        // testAdoptionDto.setOwner(testOwnerDto);
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
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionEntity);

//        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerB();
//        ownerService.save(testOwnerEntity);
//        OwnerDto testOwnerDto = TestDataUtil.createTestOwnerDtoB();
//        testOwnerDto.setName("UPDATED");


        AdoptionDto testAdoptionDto = TestDataUtil.createTestAdoptionDtoA(null, null);
        // testAdoptionDto.setOwner(testOwnerDto);
        String adoptionJson = objectMapper.writeValueAsString(testAdoptionDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adoptionJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.adoptionId").value(savedAdoption.getAdoptionId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.pet").value(testAdoptionDto.getPet())
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
        AdoptionEntity testAdoptionEntity = TestDataUtil.createTestAdoptionA(null, null);
        AdoptionEntity savedAdoption = adoptionService.save(testAdoptionEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/adoptions/" + savedAdoption.getAdoptionId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
