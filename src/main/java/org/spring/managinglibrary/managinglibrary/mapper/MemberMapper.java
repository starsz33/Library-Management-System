package org.spring.managinglibrary.managinglibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.spring.managinglibrary.managinglibrary.dto.MemberRequestDTO;
import org.spring.managinglibrary.managinglibrary.dto.MemberResponseDTO;
import org.spring.managinglibrary.managinglibrary.model.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(target = "id", ignore = true)
    Member toEntity(MemberRequestDTO memberRequestDTO);

    MemberResponseDTO toResponseDTO(Member member);

    void updateEntity(MemberRequestDTO memberRequestDTO, @MappingTarget Member member);
}
