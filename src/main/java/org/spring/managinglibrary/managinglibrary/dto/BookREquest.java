package org.spring.managinglibrary.managinglibrary.dto;

import jakarta.persistence.Column;

public class BookREquest {
    private String name;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
}
