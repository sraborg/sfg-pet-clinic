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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void findOwner() throws Exception {

        String url = "/owners/find";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);

    }

    @Test
    void processFindFormReturnMany() throws Exception{
        given(ownerService.findAllLastNameLike(anyString())).willReturn(Arrays.asList(
                owner_1,
                Owner.builder().id(2L).build()
        ));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception{
        given(ownerService.findAllLastNameLike(anyString())).willReturn(Arrays.asList(
                owner_1
        ));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/"+OWNER_ID));
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


    @Test
    void initCreationOwnerForm() throws Exception {

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationOwnerForm() throws Exception {

        given(ownerService.save(any())).willReturn(owner_1);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID))
                .andExpect(model().attributeExists("owner"));

        then(ownerService).should().save(any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        given(ownerService.findById(any())).willReturn(owner_1);

        mockMvc.perform(get("/owners/" + OWNER_ID + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        then(ownerService).should().findById(OWNER_ID);
    }

    @Test
    void processUpdateOwnerForm() throws Exception {

        given(ownerService.save(any())).willReturn(owner_1);

        mockMvc.perform(post("/owners/" + OWNER_ID + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID))
                .andExpect(model().attributeExists("owner"));

        then(ownerService).should().save(any());
    }
}