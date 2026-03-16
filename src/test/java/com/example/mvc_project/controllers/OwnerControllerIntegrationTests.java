package com.example.mvc_project.controllers;

import com.example.mvc_project.TestDataUtil;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.services.OwnerService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class OwnerControllerIntegrationTests {

    private OwnerService ownerService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public OwnerControllerIntegrationTests(MockMvc mockMvc, OwnerService ownerService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.ownerService = ownerService;
    }

    @Test
    public void testThatCreateOwnerSuccessfullyReturnsHttp201Created() throws Exception {
        OwnerEntity owner = TestDataUtil.createTestOwnerA();
        owner.setOwnerId(null);
        String ownerJson = objectMapper.writeValueAsString(owner);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ownerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateOwnerSuccessfullyReturnsSavedOwner() throws Exception {
        OwnerEntity owner = TestDataUtil.createTestOwnerA();
        owner.setOwnerId(null);
        String ownerJson = objectMapper.writeValueAsString(owner);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ownerJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.ownerId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("John Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("test@email.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.role").value("user")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        );
    }

    @Test
    public void testThatListAllOwnersSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAllOwnersSuccessfullyReturnsListOfOwners() throws Exception {
        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerA();
        ownerService.createOwner(testOwnerEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].ownerId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value("test@email.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].role").value("user")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].password").value("password")
        );
    }

    @Test
    public void testThatGetOwnerSuccessfullyReturnsHttp200WhenOwnerExists() throws Exception {
        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerA();
        ownerService.createOwner(testOwnerEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetOwnerSuccessfullyReturnsHttp404WhenNoOwnerExists() throws Exception {
        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerA();
        ownerService.createOwner(testOwnerEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/owners/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetOwnerSuccessfullyReturnsOwnerWhenOwnerExists() throws Exception {
        OwnerEntity testOwnerEntity = TestDataUtil.createTestOwnerA();
        ownerService.createOwner(testOwnerEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.ownerId").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("John Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("test@email.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.role").value("user")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        );
    }
}
