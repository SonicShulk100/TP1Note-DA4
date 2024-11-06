package romain.mourouvin.tp4_da1.todoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TodoListServiceTest{
    private TodoListService todoListService;
    private List<TodoList> todoListList;

    private Todo todo1, todo2, todo3, todo4;

    @BeforeEach
    void setUp(){
        todo1 = Mockito.mock(Todo.class);
        todo2 = Mockito.mock(Todo.class);
        todo3 = Mockito.mock(Todo.class);
        todo4 = Mockito.mock(Todo.class);

        todos = new ArrayList<>(){{
            add(todo1);
            add(todo2);
            add(todo3);
            add(todo4);
        }};

        todoListList = new ArrayList<>(){{
            add(new TodoList(1L, "GitHub", todos));
            add(new TodoList(2L, "GitLab", todos));
        }};

        todoListService = new TodoListLocalService(todoListList);
    }
    @Test
    @Order(0)
    void whenGettingAllShouldHave2(){
        assertEquals(2, todoListService.getAll().size());
    }
}