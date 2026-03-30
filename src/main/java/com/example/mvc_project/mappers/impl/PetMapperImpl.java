package com.example.mvc_project.mappers.impl;

import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PetMapperImpl implements Mapper<PetEntity, PetDto> {

    private ModelMapper modelMapper;

    public PetMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PetDto mapTo(PetEntity petEntity) {
        return modelMapper.map(petEntity, PetDto.class);
    }

    @Override
    public PetEntity mapFrom(PetDto petDto) {
        return modelMapper.map(petDto, PetEntity.class);
    }

    public void updateEntityFromDto(PetDto dto, PetEntity entity) {
        entity.setPetName(dto.getPetName());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setWeight(dto.getWeight());
        entity.setAdopted(dto.getAdopted());
    }
}
