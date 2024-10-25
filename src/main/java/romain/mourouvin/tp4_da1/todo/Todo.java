package romain.mourouvin.tp4_da1.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    private Boolean statut;

    //Constructeurs

    public Todo(Long id, String contenu, Boolean statut) {
        this.id = id;
        this.contenu = contenu;
        this.statut = statut;
    }

    public Todo(){}

    //Getters

    public Long getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public Boolean getStatut() {
        return statut;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    //Equals and Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(contenu, todo.contenu) && Objects.equals(statut, todo.statut);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(contenu);
        result = 31 * result + Objects.hashCode(statut);
        return result;
    }
}
