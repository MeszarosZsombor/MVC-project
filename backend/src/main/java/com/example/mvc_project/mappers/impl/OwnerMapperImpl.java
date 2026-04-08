package com.example.mvc_project.mappers.impl;

import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapperImpl implements Mapper<OwnerEntity, OwnerDto> {

    private ModelMapper modelMapper;

    public OwnerMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OwnerDto mapTo(OwnerEntity ownerEntity) {
        return modelMapper.map(ownerEntity, OwnerDto.class);
    }

    @Override
    public OwnerEntity mapFrom(OwnerDto ownerDto) {
        return modelMapper.map(ownerDto, OwnerEntity.class);
    }
}
