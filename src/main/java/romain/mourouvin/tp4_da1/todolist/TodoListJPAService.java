package romain.mourouvin.tp4_da1.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TodoListJPAService implements TodoListService{
    @Autowired
    private TodoListRepository todoListRepository;

    @Override
    public List<TodoList> getAll() {
        return todoListRepository.findAll();
    }

    @Override
    public TodoList getById(Long l) {
        Optional<TodoList> todoList = todoListRepository.findById(l);
        if(todoList.isPresent()){
            return todoList.get();
        }
        else{
            throw new ResourceNotFoundException("TodoList", l);
        }
    }

    @Override
    public TodoList create(TodoList todoList) throws ResourceAlreadyExistsException {
        return null;
    }

    @Override
    public void update(Long id, TodoList updatedList) throws ResourceNotFoundException {

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

    }
}
