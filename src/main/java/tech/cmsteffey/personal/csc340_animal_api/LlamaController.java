package tech.cmsteffey.personal.csc340_animal_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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

    @PatchMapping("/llamas/{id}")
    public ResponseEntity updateLlama(@PathVariable Long id, @RequestBody JsonNode llamaNode) throws IOException {
        Optional<Llama> found = llamaService.getLlamaById(id);
        if(found.isEmpty())
            return ResponseEntity.notFound().build();
        Llama existingLlama = found.get();
        Iterator<Map.Entry<String, JsonNode>> values = llamaNode.fields();
        String[] fieldNames = Arrays.stream(Llama.class.getDeclaredFields()).map(x->x.getName()).toArray(String[]::new);
        while(values.hasNext()){
            Map.Entry<String, JsonNode> value = values.next();
            if(!Arrays.asList(fieldNames).contains(value.getKey()))
                return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("JSON key " + value.getKey() + " does not exist in object definition of " + Llama.class.getSimpleName());
        }
        new ObjectMapper().readerForUpdating(existingLlama).readValue(llamaNode);
        return ResponseEntity.ok(llamaService.saveLlama(existingLlama));
    }

    @DeleteMapping("/llamas/{id}")
    public ResponseEntity deleteLlama(@PathVariable Long id){
        if(llamaService.deleteLlamaById(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
