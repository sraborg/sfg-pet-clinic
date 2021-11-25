package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    public OwnerService ownerService;

    @InjectMocks
    public OwnerController ownerController;

    public MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listOwners() throws Exception {

        when(ownerService.findAll()).thenReturn(owners);

        // When
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

//    @ParameterizedTest
//    @ValueSource(strings = {"", "/", "/index","/index.html"})
//    void listOwnersP(String url) throws Exception {
//
//        // Given
//        given(ownerService.findAll()).willReturn(owners);
//
//        // When
//        mockMvc.perform(get(url))
//                .andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//    }

    @Test
    void findOwner() {
    }
}