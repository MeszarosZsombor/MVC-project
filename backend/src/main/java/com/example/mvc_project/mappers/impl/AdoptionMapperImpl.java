package com.example.mvc_project.mappers.impl;

import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdoptionMapperImpl implements Mapper<AdoptionEntity, AdoptionDto> {

    private ModelMapper modelMapper;

    public AdoptionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AdoptionDto mapTo(AdoptionEntity adoptionEntity) {
        return modelMapper.map(adoptionEntity, AdoptionDto.class);
    }

    @Override
    public AdoptionEntity mapFrom(AdoptionDto dto) {
        return AdoptionEntity.builder()
                .adoptionId(dto.getAdoptionId())
                .build();
    }
}
