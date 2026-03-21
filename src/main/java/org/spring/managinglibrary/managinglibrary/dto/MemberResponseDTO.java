package org.spring.managinglibrary.managinglibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    Long Id;
    private String name;
    private String email;
    private String phoneNumber;
}

