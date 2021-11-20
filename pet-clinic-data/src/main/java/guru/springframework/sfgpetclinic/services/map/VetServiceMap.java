package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.model.Vet;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {
    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);

    }

    @Override
    public void delete(Vet owner) {
        super.delete(owner);
    }

    @Override
    public Vet save(Vet owner) {
        return super.save(owner);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
