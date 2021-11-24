package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.model.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);

    }

    @Override
    public void delete(Pet owner) {
        super.delete(owner);
    }

    @Override
    public Pet save(Pet owner) {
        return super.save(owner);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
