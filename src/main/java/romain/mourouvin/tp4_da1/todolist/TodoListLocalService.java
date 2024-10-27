package romain.mourouvin.tp4_da1.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;
import romain.mourouvin.tp4_da1.utils.LocalService;

import java.util.List;

@Service
public class TodoListLocalService extends LocalService<TodoList, Long> implements TodoListService{

    public TodoListLocalService(){
        super();
    }

    @Autowired
    public TodoListLocalService(List<TodoList> todoLists){
        super(todoLists);
    }

    @Override
    protected String getIdentifier() {
        return "id";
    }

    @Override
    public List<TodoList> getAll() {
        return super.getAll();
    }

    @Override
    public TodoList getById(Long l) {
        return this.getByIdentifier(l);
    }

    @Override
    public TodoList create(TodoList todoList) throws ResourceAlreadyExistsException {
        try{
            this.findById(todoList.getId());
            throw new ResourceAlreadyExistsException("TodoList", todoList.getId());
        }
        catch(ResourceNotFoundException e){
            this.allValues.add(todoList);
            return todoList;
        }
    }

    @Override
    public void update(Long id, TodoList updatedList) throws ResourceNotFoundException {
        IndexAndValue<TodoList> found = this.findById(id);
        this.allValues.remove(found.index());
        this.allValues.add(found.index(), updatedList);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        IndexAndValue<TodoList> found = this.findById(id);
        this.allValues.remove(found.index());
    }

    public IndexAndValue<TodoList> findById(Long l){
        return super.findByProperty(l);
    }
}
