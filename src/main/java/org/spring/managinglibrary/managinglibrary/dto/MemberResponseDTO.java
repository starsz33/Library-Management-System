package org.spring.managinglibrary.managinglibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.managinglibrary.managinglibrary.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
}

