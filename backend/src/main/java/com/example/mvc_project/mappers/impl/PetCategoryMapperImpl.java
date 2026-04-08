package com.example.mvc_project.mappers.impl;

import com.example.mvc_project.domain.dto.PetCategoryDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PetCategoryMapperImpl implements Mapper<PetCategoryEntity, PetCategoryDto> {

    private ModelMapper modelMapper;

    public PetCategoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PetCategoryDto mapTo(PetCategoryEntity petCategoryEntity) {
        return modelMapper.map(petCategoryEntity, PetCategoryDto.class);
    }

    @Override
    public PetCategoryEntity mapFrom(PetCategoryDto petCategoryDto) {
        return modelMapper.map(petCategoryDto, PetCategoryEntity.class);
    }
}
