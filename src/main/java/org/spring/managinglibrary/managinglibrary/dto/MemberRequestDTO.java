package org.spring.managinglibrary.managinglibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
