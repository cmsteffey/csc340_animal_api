package tech.cmsteffey.personal.csc340_animal_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LlamaRepository extends JpaRepository<Llama, Long> {
    @Query(value = "select l.llama_id as llama_id, l.name as name, l.color as color, l.age as age from llama l", nativeQuery = true)
    List<Llama> getLlamas();
}
