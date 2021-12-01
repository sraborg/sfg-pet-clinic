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

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
    private final Long OWNER_ID = 1L;
    private final Owner owner_1 = Owner.builder().id(OWNER_ID).build();

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(owner_1);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/owners", "/owners/", "/owners/index","/owners/index.html"})
    void listOwners(String url) throws Exception {
        owners.add(Owner.builder().id(2L).build());
        given(ownerService.findAll()).willReturn(owners);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwner() throws Exception {

        String url = "/owners/find";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("notImplemented"));

        verifyNoInteractions(ownerService);

    }

    @Test
    void displayOwner() throws Exception {

        given(ownerService.findById(OWNER_ID)).willReturn(owner_1);
        final String url = "/owners/" + OWNER_ID;

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(OWNER_ID))));
    }


}