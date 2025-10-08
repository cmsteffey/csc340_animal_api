package tech.cmsteffey.personal.csc340_animal_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LlamaRepository extends JpaRepository<Llama, Long> {
    @Query(value = "select l.llama_id, l.name, l.description, l.age, l.color from llama l", nativeQuery = true)
    List<Llama> getAllLlamas();

    List<Llama> getLlamasByColorIgnoreCase(String color);

    List<Llama> getLlamasByNameContainingIgnoreCase(String name);

    Optional<Llama> getLlamaByLlamaId(Long id);
}
