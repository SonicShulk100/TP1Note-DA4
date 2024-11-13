package romain.mourouvin.tp4_da1.TodoListTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;
import romain.mourouvin.tp4_da1.todolist.TodoList;
import romain.mourouvin.tp4_da1.todolist.TodoListController;
import romain.mourouvin.tp4_da1.todolist.TodoListService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoListController.class)
public class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoListService todoListService;

    private TodoList sampleTodoList;

    @BeforeEach
    void setUp() {
        sampleTodoList = new TodoList(1L, "Sample List", new ArrayList<>());
    }

    @Test
    public void testGetAll_ShouldReturnTodoListArray() throws Exception {
        List<TodoList> todoLists = List.of(
                new TodoList(1L, "List 1", new ArrayList<>()),
                new TodoList(2L, "List 2", new ArrayList<>())
        );

        when(todoListService.getAll()).thenReturn(todoLists);

        mockMvc.perform(get("/todoLists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nom", is("List 1")))
                .andExpect(jsonPath("$[1].nom", is("List 2")));
    }

    @Test
    public void testGetById_ShouldReturnTodoList_WhenExists() throws Exception {
        when(todoListService.getById(1L)).thenReturn(sampleTodoList);

        mockMvc.perform(get("/todoLists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Sample List")));
    }

    @Test
    public void testGetById_ShouldReturnNotFound_WhenNotExists() throws Exception {
        when(todoListService.getById(1L)).thenThrow(new ResourceNotFoundException("TodoList", 1L));

        mockMvc.perform(get("/todoLists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate_ShouldReturnCreatedStatus_WhenTodoListIsCreated() throws Exception {
        TodoList newList = new TodoList(null, "New List", new ArrayList<>());  // Ce n'est pas réellement utilisé pour le matching
        TodoList createdList = new TodoList(1L, "New List", new ArrayList<>());

        // Utilisation de any() pour matcher n'importe quel TodoList
        when(todoListService.create(any(TodoList.class))).thenReturn(createdList);

        mockMvc.perform(post("/todoLists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"New List\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/todoLists/1"));
    }


    @Test
    public void testCreate_ShouldReturnConflict_WhenTodoListAlreadyExists() throws Exception {
        TodoList duplicateList = new TodoList(1L, "Duplicate List", new ArrayList<>());

        when(todoListService.create(any(TodoList.class))).thenThrow(new ResourceAlreadyExistsException("TodoList", 1L));

        mockMvc.perform(post("/todoLists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Duplicate List\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdate_ShouldReturnNoContent_WhenTodoListIsUpdated() throws Exception {
        doNothing().when(todoListService).update(eq(1L), any(TodoList.class));

        mockMvc.perform(put("/todoLists/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Updated List\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdate_ShouldReturnNotFound_WhenTodoListDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException("TodoList", 1L)).when(todoListService).update(eq(1L), any(TodoList.class));

        mockMvc.perform(put("/todoLists/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Non-existent List\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete_ShouldReturnNoContent_WhenTodoListIsDeleted() throws Exception {
        doNothing().when(todoListService).delete(1L);

        mockMvc.perform(delete("/todoLists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDelete_ShouldReturnNotFound_WhenTodoListDoesNotExist() throws Exception {
        doThrow(new ResourceNotFoundException("TodoList", 1L)).when(todoListService).delete(1L);

        mockMvc.perform(delete("/todoLists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}