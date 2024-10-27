package romain.mourouvin.tp4_da1.todolist;

import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TodoListService {
    List<TodoList> getAll();

    TodoList getById(Long l);

    TodoList create(TodoList todoList) throws ResourceAlreadyExistsException;

    void update(Long id, TodoList updatedList) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
