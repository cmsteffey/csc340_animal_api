package tech.cmsteffey.personal.csc340_animal_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LlamaService {
    @Autowired
    private LlamaRepository llamaRepository;

    public List<Llama> getAllLlamas(){
        return llamaRepository.getAllLlamas();
    }
    public List<Llama> getLlamasByColor(String color){
        return llamaRepository.getLlamasByColorIgnoreCase(color);
    }
    public List<Llama> getLlamasByName(String name){
        return llamaRepository.getLlamasByNameContainingIgnoreCase(name);
    }
    public Llama saveLlama(Llama llama){
        return llamaRepository.save(llama);
    }
    public Optional<Llama> getLlamaById(Long id){
        return llamaRepository.getLlamaByLlamaId(id);
    }
    public boolean deleteLlamaById(Long id){
        try {
            llamaRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}
