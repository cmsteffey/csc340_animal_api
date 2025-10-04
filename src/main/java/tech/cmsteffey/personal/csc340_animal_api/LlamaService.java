package tech.cmsteffey.personal.csc340_animal_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LlamaService {
    @Autowired
    private LlamaRepository llamaRepository;

    public List<Llama> getAllLlamas(){
        return llamaRepository.getLlamas();
    }
}
