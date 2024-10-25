package romain.mourouvin.tp4_da1.todoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;
import romain.mourouvin.tp4_da1.todo.Todo;
import romain.mourouvin.tp4_da1.todo.TodoLocalService;
import romain.mourouvin.tp4_da1.todo.TodoService;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TodoServiceTest {
    private TodoService todoService;
    private List<Todo> todoList;

    @BeforeEach
    void setUp(){
        todoList = new ArrayList<>(){{
            add(new Todo(1L, "Faire les devoirs", false));
            add(new Todo(2L, "Faire le ménage", false));
            add(new Todo(3L, "Prendre la douche", false));
            add(new Todo(4L, "Coder", true));
            add(new Todo(5L, "Faire le lave-vaisselle", false));
        }};

        todoService = new TodoLocalService(todoList);
    }

    @Test
    void whenGettingAllShouldHave5Todos(){
        assertEquals(5, todoService.getAll().size(), "There should be 5 todos in total");
    }

    @Test
    void whenQueryingCodeShouldHaveSameTodo(){
        assertAll(
                () -> assertEquals(todoList.get(4), todoService.getById(5L)),
                () -> assertEquals(todoList.get(0), todoService.getById(1L))
        );
    }

    @Test
    void whenCreatingIssueShouldHaveIncreasedSizeAndShouldGetIt(){
        Todo newTodo = new Todo(6L, "Prendre soin du chat", false);
        int initialSize = todoList.size();

        assertAll(
                () -> assertEquals(newTodo, todoService.create(newTodo)),
                () -> assertEquals(initialSize + 1, todoList.size()),
                () -> assertEquals(newTodo, todoList.get(initialSize))
        );
    }

    @Test
    void whenCreatingWithSameIdShouldReturnEmptyAndNotIncrease(){
        Todo todo = todoList.get(0);

        int initialSize = todoList.size();

        assertAll(
            () -> assertThrows(ResourceAlreadyExistsException.class, ()->todoService.create(todo)),
            () -> assertEquals(initialSize, todoList.size())
        );
    }

    @Test
    void whenUpdatingShiuldContainModifiedTodo(){
        Todo todoToModify1 = todoList.get(2);
        String newContent = "Jouer";
        Boolean newStatus = true;

        Todo UpdatedTodo = new Todo(todoToModify1.getId(), newContent, newStatus);

        todoService.update(todoToModify1.getId(), UpdatedTodo);

        assertAll(
                () -> assertEquals(newContent, todoService.getById(todoToModify1.getId()).getContenu()),
                () -> assertEquals(newStatus, todoService.getById(todoToModify1.getId()).getStatut())
        );
    }

    @Test
    void whenDeletingShouldDecreaseSize(){
        int Expected = todoList.size() - 1;
        Long id = 4L;
        todoService.delete(id);
        assertAll(
                () -> assertEquals(Expected, todoService.getAll().size()),
                () -> assertThrows(ResourceNotFoundException.class, ()->todoService.getById(id))
        );
    }
}
