package romain.mourouvin.tp4_da1.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
}
