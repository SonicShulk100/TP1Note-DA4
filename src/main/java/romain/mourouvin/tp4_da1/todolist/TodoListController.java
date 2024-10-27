package romain.mourouvin.tp4_da1.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todoLists")
public class TodoListController {
    private TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public List<TodoList> getAll(){
        return todoListService.getAll();
    }

    @GetMapping("/{id}")
    public TodoList getById(@PathVariable("id") Long id){
        return todoListService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody TodoList todoList){
        TodoList created = todoListService.create(todoList);
        return ResponseEntity.created(URI.create("/todoLists/"+created.getId().toString())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TodoList todoList){
        todoListService.update(id, todoList);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        todoListService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
