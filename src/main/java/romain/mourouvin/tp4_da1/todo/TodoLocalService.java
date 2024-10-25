package romain.mourouvin.tp4_da1.todo;

import org.springframework.beans.factory.annotation.Autowired;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;
import romain.mourouvin.tp4_da1.utils.LocalService;

import java.util.List;

public class TodoLocalService extends LocalService<Todo, Long> implements TodoService{
    @Autowired
    public TodoLocalService(){
        super();
    }

    public TodoLocalService(List<Todo> todoList){
        super(todoList);
    }

    @Override
    protected String getIdentifier() {
        return "id";
    }

    @Override
    public List<Todo> getAll() {
        return super.getAll();
    }

    @Override
    public Todo getById(Long id) {
        return this.getByIdentifier(id);
    }

    @Override
    public Todo create(Todo newTodo) throws ResourceAlreadyExistsException {
        try{
            this.findByProperty(newTodo.getId());
            throw new ResourceAlreadyExistsException("Todo", newTodo.getId());
        }
        catch(ResourceNotFoundException e){
            this.allValues.add(newTodo);
            return newTodo;
        }
    }

    @Override
    public void update(Long id, Todo updateTodo) throws ResourceNotFoundException {
        IndexAndValue<Todo> found = this.findById(id);
        this.allValues.remove(found.index());
        this.allValues.add(found.index(), updateTodo);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        IndexAndValue<Todo> found = this.findById(id);
        this.allValues.remove(found.value());
    }

    public IndexAndValue<Todo> findById(Long id){
        return super.findByProperty(id);
    }
}
