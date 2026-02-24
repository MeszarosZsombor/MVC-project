package com.example.mvc_project;


import com.example.mvc_project.domain.Owner;

import java.sql.Time;
import java.time.LocalTime;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Owner createTestOwner() {
        return Owner.builder()
                .ownerId(1L)
                .role("user")
                .email("test@email.com")
                .password("password")
                .name("John Doe")
                .build();
    }
}
