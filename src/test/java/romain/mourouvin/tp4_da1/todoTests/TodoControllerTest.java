package romain.mourouvin.tp4_da1.todoTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import romain.mourouvin.tp4_da1.exceptions.ExceptionHandlingAdvice;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;
import romain.mourouvin.tp4_da1.todo.Todo;
import romain.mourouvin.tp4_da1.todo.TodoController;
import romain.mourouvin.tp4_da1.todo.TodoService;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = TodoController.class)
@Import(ExceptionHandlingAdvice.class)
public class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
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

        when(todoService.getAll()).thenReturn(todoList);
        when(todoService.getById(5L)).thenReturn(todoList.get(4));
        when(todoService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAllShouldGet5AndBe200() throws Exception{
        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(5))
        ).andDo(print());
    }

    @Test
    void WhenGettingId5LShouldReturnSame() throws Exception{
        mockMvc.perform(get("/todos/5")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(5))
        ).andExpect(jsonPath("$.contenu", is("Faire le lave-vaisselle"))
        ).andExpect(jsonPath("$.statut", is(false))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingIdShouldBe404() throws Exception{
        mockMvc.perform(get("/todos/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNewTodoShouldReturnLinkAndShouldBeStatusCreated() throws Exception{
        Todo newTodo = new Todo(89L, "Faire un truc", true);
        ArgumentCaptor<Todo> received = ArgumentCaptor.forClass(Todo.class);
        when(todoService.create(any())).thenReturn(newTodo);

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newTodo))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/todos/"+newTodo.getId())
        ).andDo(print());

        verify(todoService).create(received.capture());
        assertEquals(newTodo, received.getValue());
    }

    @Test
    void whenCreatingWithExistingIdShould404() throws Exception{
        when(todoService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.todoList.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdatingShouldReceiveUserToUpdateAndReturnNoContent() throws Exception{
        Todo initialTodo = todoList.get(1);
        Todo updated = new Todo(initialTodo.getId(), "Se peser", true);

        ArgumentCaptor<Todo> received = ArgumentCaptor.forClass(Todo.class);

        mockMvc.perform(put("/todos/"+initialTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated))
        ).andExpect(status().isNoContent());

        verify(todoService).update(anyLong(), received.capture());
        assertEquals(updated, received.getValue());
    }

    @Test
    void whenDeletingExistingShouldCallServiceWithCorrectIdAndSendNoContent() throws Exception{
        Long id = 5L;

        mockMvc.perform(delete("/todos/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> received = ArgumentCaptor.forClass(Long.class);
        verify(todoService).delete(received.capture());
        assertEquals(id, received.getValue());
    }
}
