package romain.mourouvin.tp4_da1.todo;

import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TodoService {
    List<Todo> getAll();

    Todo getById(Long id);

    Todo create(Todo newTodo) throws ResourceAlreadyExistsException;

    void update (Long id, Todo updateTodo) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
