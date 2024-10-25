package romain.mourouvin.tp4_da1.todolist;

import jakarta.persistence.*;
import org.hibernate.property.access.internal.AbstractSetterMethodSerialForm;
import romain.mourouvin.tp4_da1.todo.Todo;

import java.util.List;

@Entity
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Todo> todos;

    //Constructeurs

    public TodoList(Long id, String nom, List<Todo> todos) {
        this.id = id;
        this.nom = nom;
        this.todos = todos;
    }

    public TodoList(){}

    //Getters

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
