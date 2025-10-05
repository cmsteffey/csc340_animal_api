package tech.cmsteffey.personal.csc340_animal_api;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class LlamaController {
    @Autowired
    private LlamaService llamaService;
    @GetMapping("/llamas")
    public List<Llama> getAllLlamas(){
        return llamaService.getAllLlamas();
    }
    @GetMapping("/llamas/color/{color}")
    public List<Llama> getLlamasByColor(@PathVariable String color){
        return llamaService.getLlamasByColor(color);
    }

    @GetMapping("/llamas/search")
    public ResponseEntity getLlamasByName(@RequestParam(required = false) String name) {
        if (name == null)
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Needs 'name' parameter");
        return ResponseEntity.ok(llamaService.getLlamasByName(name));
    }

    @PostMapping("/llamas")
    public ResponseEntity createLlama(@RequestBody Llama llama){
        try{
            return ResponseEntity.ok(llamaService.saveLlama(llama));
        } catch (Exception e){
            LoggerFactory.getLogger(LlamaController.class).warn("Llama post error: ", e);
            return ResponseEntity.badRequest().body("Malformed body");
        }
    }
}
