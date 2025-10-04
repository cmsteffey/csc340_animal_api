package tech.cmsteffey.personal.csc340_animal_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LlamaRepository extends JpaRepository<Llama, Long> {
    List<Llama> getAllBy();

    List<Llama> getLlamasByColorIgnoreCase(String color);

    List<Llama> getLlamasByNameContainingIgnoreCase(String name);
}
