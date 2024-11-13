package romain.mourouvin.tp4_da1.TodoListTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import romain.mourouvin.tp4_da1.exceptions.ResourceAlreadyExistsException;
import romain.mourouvin.tp4_da1.exceptions.ResourceNotFoundException;
import romain.mourouvin.tp4_da1.todolist.TodoList;
import romain.mourouvin.tp4_da1.todolist.TodoListLocalService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListServiceTest {
    @InjectMocks
    private TodoListLocalService todoListService;

    @Mock
    private List<TodoList> todoLists;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoListService = new TodoListLocalService(new ArrayList<>());
    }

    @Test
    public void testGetAll_ShouldReturnAllTodoLists() {
        TodoList list1 = new TodoList(1L, "Liste 1", new ArrayList<>());
        TodoList list2 = new TodoList(2L, "Liste 2", new ArrayList<>());
        todoListService.create(list1);
        todoListService.create(list2);

        List<TodoList> result = todoListService.getAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(list1));
        assertTrue(result.contains(list2));
    }

    @Test
    public void testGetById_ShouldReturnTodoList_WhenExists() {
        TodoList list = new TodoList(1L, "Liste Test", new ArrayList<>());
        todoListService.create(list);

        TodoList result = todoListService.getById(1L);

        assertNotNull(result);
        assertEquals("Liste Test", result.getNom());
    }

    @Test
    public void testGetById_ShouldThrowException_WhenNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> todoListService.getById(999L));
    }

    @Test
    public void testCreate_ShouldAddTodoList_WhenNotExists() throws ResourceAlreadyExistsException {
        TodoList list = new TodoList(1L, "Nouvelle Liste", new ArrayList<>());

        TodoList result = todoListService.create(list);

        assertEquals("Nouvelle Liste", result.getNom());
        assertEquals(1, todoListService.getAll().size());
    }

    @Test
    public void testCreate_ShouldThrowException_WhenAlreadyExists() {
        TodoList list = new TodoList(1L, "Liste Unique", new ArrayList<>());
        todoListService.create(list);

        assertThrows(ResourceAlreadyExistsException.class, () -> todoListService.create(list));
    }

    @Test
    public void testUpdate_ShouldUpdateTodoList_WhenExists() throws ResourceNotFoundException {
        TodoList originalList = new TodoList(1L, "Ancienne Liste", new ArrayList<>());
        todoListService.create(originalList);

        TodoList updatedList = new TodoList(1L, "Liste Mise à Jour", new ArrayList<>());
        todoListService.update(1L, updatedList);

        TodoList result = todoListService.getById(1L);
        assertEquals("Liste Mise à Jour", result.getNom());
    }

    @Test
    public void testUpdate_ShouldThrowException_WhenNotExists() {
        TodoList updatedList = new TodoList(1L, "Liste Inexistante", new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> todoListService.update(1L, updatedList));
    }

    @Test
    public void testDelete_ShouldRemoveTodoList_WhenExists() throws ResourceNotFoundException {
        TodoList list = new TodoList(1L, "Liste à Supprimer", new ArrayList<>());
        todoListService.create(list);

        todoListService.delete(1L);

        assertThrows(ResourceNotFoundException.class, () -> todoListService.getById(1L));
    }

    @Test
    public void testDelete_ShouldThrowException_WhenNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> todoListService.delete(999L));
    }
}
