package org.spring.managinglibrary.managinglibrary;


import org.junit.jupiter.api.Test;
import org.spring.managinglibrary.managinglibrary.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc() {
        return MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    void testGetAllMembers_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc().perform(get("/api/members"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void testDeleteMember_WithMemberRole_ShouldReturn403() throws Exception {
        mockMvc().perform(delete("/api/members/1")
                        .with(user("member@library.com").roles("MEMBER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteMember_WithAdminRole_ShouldReturn204() throws Exception {
        mockMvc().perform(delete("/api/members/1")
                        .with(user("admin@library.com").roles("ADMIN")))
                .andExpect(status().isNoContent());
    }
}