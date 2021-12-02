package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetTypeService petTypeService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;

    private final Owner owner = new Owner();
    private final Long OWNER_ID = 1L;
    private final Pet pet = new Pet();
    private final Long PET_ID = 2l;
    private Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner.setId(OWNER_ID);
        pet.setId(PET_ID);

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void initCreationForm() throws Exception {

        // Given
        given(ownerService.findById(ArgumentMatchers.anyLong())).willReturn(owner);
        given(petTypeService.findAll()).willReturn(petTypes);

        // When
        mockMvc.perform(get("/owners/" + OWNER_ID + "/pets/new"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processCreationForm() throws Exception {

        // Given
        given(ownerService.findById(ArgumentMatchers.anyLong())).willReturn(owner);
        given(petTypeService.findAll()).willReturn(petTypes);

        // When
        mockMvc.perform(post("/owners/" + OWNER_ID + "/pets/new"))
                // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID));

        then(petService).should().save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        // Given
        given(ownerService.findById(ArgumentMatchers.anyLong())).willReturn(owner);
        given(petTypeService.findAll()).willReturn(petTypes);
        given(petService.findById(PET_ID)).willReturn(pet);

        // When
        mockMvc.perform(get("/owners/" + OWNER_ID + "/pets/" + PET_ID +"/edit"))
                // Then
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processUpdateForm() throws Exception {

        // Given
        given(ownerService.findById(ArgumentMatchers.anyLong())).willReturn(owner);
        given(petTypeService.findAll()).willReturn(petTypes);

        // When
        mockMvc.perform(post("/owners/" + OWNER_ID + "/pets/" + PET_ID +"/edit"))
                // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + OWNER_ID));

        then(petService).should().save(any());
    }


}