package tech.cmsteffey.personal.csc340_animal_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class LlamaController {
    @Autowired
    private LlamaService llamaService;

    @GetMapping("/")
    public String index(Model model)
    {
        return "redirect:/llamas";
    }
    @GetMapping("/llamas")
    public String getAllLlamas(Model model){
        model.addAttribute("llamas", llamaService.getAllLlamas());
        return "animal-list";
    }
    @GetMapping("/llamas/{id}")
    public String getLlamaById(@PathVariable Long id, Model model){
        Optional<Llama> llama = llamaService.getLlamaById(id);
        if(llama.isEmpty())
            return "404";
        model.addAttribute("llama", llama.orElseThrow());
        return "animal-details";
    }
    @GetMapping("/llamas/color/{color}")
    public String getLlamasByColor(@PathVariable(required = false) String color, Model model){
        if(color == null || color.isEmpty())
            return "400";
        model.addAttribute("llamas", llamaService.getLlamasByColor(color));
        return "animal-list";
    }

    @GetMapping("/llamas/search")
    public String getLlamasByName(@RequestParam(required = false) String name, Model model) {
        if (name == null || name.isEmpty())
            return "400";
        model.addAttribute("llamas", llamaService.getLlamasByName(name));
        return "animal-list";
    }

    @PostMapping("/llamas")
    public String createLlama(@ModelAttribute Llama llama, @RequestParam MultipartFile pic){
        try{
            LoggerFactory.getLogger(LlamaController.class).warn("Empty: {}", pic.isEmpty());
            if(pic.isEmpty())
                return "400";
            llama.setLlamaId(null);
            llama.setImageContentType(pic.getContentType());
            Llama saved = llamaService.saveLlama(llama);
            llamaService.savePictureFile(saved.getLlamaId(), pic);
            return "redirect:/llamas/" + saved.getLlamaId();
        } catch (DataIntegrityViolationException e){
            return "404";
        }
    }

    @PostMapping("/llamas/update/{id}")
    public String putLlama(@PathVariable Long id, @ModelAttribute Llama llama, @RequestParam MultipartFile pic, Model model){
        LoggerFactory.getLogger(LlamaController.class).warn("Update endpoint called!");

        Optional<Llama> dbLlama = llamaService.getLlamaById(id);
        if(dbLlama.isEmpty())
            return "400";
        try {
            llama.setLlamaId(id);
            if(!pic.isEmpty()){
                LoggerFactory.getLogger(LlamaController.class).warn("NOT EMPTY!");
                llama.setImageContentType(pic.getContentType());
                llamaService.savePictureFile(id, pic);
            } else {
                llama.setImageContentType(dbLlama.orElseThrow().getImageContentType());
            }
            llama.setLlamaId(dbLlama.orElseThrow().getLlamaId());
            llamaService.saveLlama(llama);
            return "redirect:/llamas/" + id;
        }catch(DataIntegrityViolationException e){
            LoggerFactory.getLogger(LlamaController.class).warn("Save failed: ", e);
            return "400";
        }
    }

    @GetMapping("/llamas/delete/{id}")
    public String deleteLlama(@PathVariable Long id, Model model){
        if(llamaService.deleteLlamaById(id)){
            return "redirect:/llamas";
        } else {
            return "404";
        }
    }

    @GetMapping("/new-animal")
    public String newAnimalForm(){
        return "animal-create";
    }
    @GetMapping("/update-animal/{id}")
    public String updateAnimalForm(@PathVariable Long id, Model model){
        Optional<Llama> llama = llamaService.getLlamaById(id);
        if(llama.isEmpty()){
            return "404";
        }
        model.addAttribute("llama", llama.orElseThrow());
        return "animal-update";
    }

    @GetMapping("/profile_pictures/{llamaId}")
    public ResponseEntity pfp(@PathVariable long llamaId){
        Optional<Llama> llama = llamaService.getLlamaById(llamaId);
        if(llama.isEmpty())
            return ResponseEntity.notFound().build();
        File profilePictureFile = new File("src/main/resources/static/profile_pictures/llama" + llamaId);
        if(!profilePictureFile.exists())
            return ResponseEntity.notFound().build();
        try (InputStream stream = new FileInputStream(profilePictureFile.getAbsolutePath())){
            InputStreamResource isr = new InputStreamResource(stream);
            return ResponseEntity.ok().contentType(MediaType.valueOf(llama.orElseThrow().getImageContentType())).body(isr.getContentAsByteArray());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/static/{fn}")
    public ResponseEntity staticFile(@PathVariable String fn){
        InputStream file = getClass().getResourceAsStream("/static/" + fn);
        if(file == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().contentType(MediaType.valueOf(fn.endsWith(".css") ? "text/css" :
                fn.endsWith(".html") ? "text/html" :
                fn.endsWith(".jpg") || fn.endsWith(".jpeg") ? "image/jpeg" :
                fn.endsWith(".png") ? "image/png" :
                fn.endsWith(".js") ? "text/javascript" : "application/octet-stream")).body(new InputStreamResource(file));
    }
}
