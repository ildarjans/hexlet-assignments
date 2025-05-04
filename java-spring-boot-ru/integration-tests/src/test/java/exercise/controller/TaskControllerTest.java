package exercise.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
// END
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private Task createTaskInstance() {
        return Instancio.of(Task.class)
            .ignore(Select.field(Task::getId))
            .create();
    }

    private HashMap<String, String> createTaskBody() {
        String title = faker.lorem().sentence(3);
        String description = faker.lorem().sentence(15);

        HashMap<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);

        return data;
    }


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testShow() throws Exception {
        var task = createTaskInstance();
        taskRepository.save(task);

       var result = mockMvc.perform(get("/tasks/{id}", task.getId()))
           .andExpect(status().isOk())
           .andReturn();

       var body = result.getResponse().getContentAsString();

       assertThatJson(body).and(
       t -> t.node("id").isEqualTo(task.getId()),
       t -> t.node("title").isEqualTo(task.getTitle()),
       t -> t.node("description").isEqualTo(task.getDescription())
       );
    }

    @Test
    public void testCreate() throws Exception {
        var body = createTaskBody();

        var request = post("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(body));

        mockMvc.perform(request).andExpect(status().isCreated());

        var task = taskRepository.findByTitle(body.get("title")).get();

        assertThat(task.getDescription()).isEqualTo(body.get("description"));
    }

    @Test
    public void testUpdate() throws Exception {
        var task = createTaskInstance();
        var body = createTaskBody();

        taskRepository.save(task);

        var request = put("/tasks/{id}", task.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(body));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedTask = taskRepository.findById(task.getId()).get();

        assertThat(updatedTask.getTitle()).isEqualTo(body.get("title"));
        assertThat(updatedTask.getDescription()).isEqualTo(body.get("description"));
    }

    @Test
    public void deleteTask() throws Exception {
        var task = createTaskInstance();

        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/{id}",  task.getId()))
            .andExpect(status().isOk());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
    // END
}
