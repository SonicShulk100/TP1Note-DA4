package romain.mourouvin.tp4_da1.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TodoJPAService implements TodoService{
    @Autowired
    private TodoRepository todoRepository;
    @Override
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            return todo.get();
        }
        else{
            throw new ResourceNotFoundException("Todo", id);
        }
    }

    @Override
    public Todo create(Todo newTodo) throws ResourceAlreadyExistsException {
        return null;
    }

    @Override
    public void update(Long id, Todo updateTodo) throws ResourceNotFoundException {

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

    }
}
