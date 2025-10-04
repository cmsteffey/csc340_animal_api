package tech.cmsteffey.personal.csc340_animal_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class LlamaController {
    @Autowired
    private LlamaService llamaService;
    @GetMapping("/llamas")
    public List<Llama> getAllLlamas(){
        return llamaService.getAllLlamas();
    }
}
