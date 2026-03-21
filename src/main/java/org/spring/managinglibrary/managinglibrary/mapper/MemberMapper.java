package org.spring.managinglibrary.managinglibrary.mapper;

import org.mapstruct.Mapper;
import org.spring.managinglibrary.managinglibrary.dto.MemberRequestDTO;
import org.spring.managinglibrary.managinglibrary.dto.MemberResponseDTO;
import org.spring.managinglibrary.managinglibrary.model.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member toEntity(MemberRequestDTO memberRequestDTO);
    MemberResponseDTO toResponseDTO(Member member);
}
