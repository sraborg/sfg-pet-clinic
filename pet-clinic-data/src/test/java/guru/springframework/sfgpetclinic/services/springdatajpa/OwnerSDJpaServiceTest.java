package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private final String LAST_NAME = "smith";
    private final Owner owner = Owner.builder().id(1L).lastName(LAST_NAME).build();

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private OwnerSDJpaService ownerSDJpaService;

    @Test
    void findAll() {

        // Given
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(owner);
        ownerSet.add(Owner.builder().id(2L).build());

        given(ownerRepository.findAll()).willReturn(ownerSet);

        // When
        Set<Owner> returnedOwners = ownerSDJpaService.findAll();

        // Then
        then(ownerRepository).should().findAll();
        assertNotNull(returnedOwners);
        assertThat(returnedOwners.size()).isEqualTo(2);


    }

    @Test
    void findById() {

        // given
        given(ownerRepository.findById(any())).willReturn(Optional.of(owner));

        // When
        Owner returnedOwner = ownerSDJpaService.findById(owner.getId());

        // Then
        then(ownerRepository).should().findById(owner.getId());
        assertNotNull(returnedOwner);
    }

    @Test
    void findByIdNotFound() {

        // given
        given(ownerRepository.findById(any())).willReturn(Optional.empty());

        // When
        Owner returnedOwner = ownerSDJpaService.findById(owner.getId());

        // Then
        then(ownerRepository).should().findById(owner.getId());
        assertNull(returnedOwner);
    }

    @Test
    void save() {

        // given
        given(ownerRepository.save(any())).willReturn(owner);

        // When
        Owner savedOwner = ownerSDJpaService.save(owner);

        // Then
        then(ownerRepository).should().save(owner);
        assertNotNull(savedOwner);

    }

    @Test
    void delete() {

        // When
        ownerSDJpaService.delete(owner);

        // Then
        then(ownerRepository).should().delete(any());
    }

    @Test
    void deleteById() {
        // When
        ownerSDJpaService.deleteById(owner.getId());

        // Then
        then(ownerRepository).should().deleteById(owner.getId());
    }

    @Test
    void findByLastName() {
        // given
        given(ownerRepository.findByLastName(any())).willReturn(owner);

        // when
        Owner smith = ownerSDJpaService.findByLastName(LAST_NAME);

        // Then
        then(ownerRepository).should().findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, smith.getLastName());
    }
}