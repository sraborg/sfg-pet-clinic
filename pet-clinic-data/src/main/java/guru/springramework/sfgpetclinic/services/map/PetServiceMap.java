package guru.springramework.sfgpetclinic.services.map;

import guru.springramework.sfgpetclinic.model.Pet;
import guru.springramework.sfgpetclinic.services.PetService;

import java.util.Set;

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
        return super.save(owner.getId(), owner);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
