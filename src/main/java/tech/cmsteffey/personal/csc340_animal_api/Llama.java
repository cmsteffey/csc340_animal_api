package tech.cmsteffey.personal.csc340_animal_api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.*;

@Entity
@Table(name = "llama")
@JsonIgnoreProperties(value = {"llamaId"}, allowSetters = false, allowGetters = true)
public class Llama {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long llamaId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String description;

    public Long getLlamaId() {
        return llamaId;
    }

    public void setLlamaId(Long llamaId) {
        this.llamaId = llamaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
