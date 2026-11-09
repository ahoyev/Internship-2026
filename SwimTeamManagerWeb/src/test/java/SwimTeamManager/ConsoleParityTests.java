package SwimTeamManager;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.application.name=console-parity-test")
@DirtiesContext
class ConsoleParityTests extends FileStorageTestSupport {
    @Autowired private WebApplicationContext context;
    @Autowired private SwimmerService swimmers;
    @Autowired private AttendanceService attendance;

    @Test
    void searchesEditsAndRemovesWithoutMixingSwimmersOrAttendance() throws Exception {
        var mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc.perform(get("/swimmers/search")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Search Swimmers")));
        mvc.perform(get("/swimmers/edit")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Swimmers")));
        mvc.perform(get("/swimmers/remove")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Remove Swimmers")));
        swimmers.add("Tony", "15", "Gold");
        swimmers.add("Tony", "12", "Silver");
        long id = swimmers.findAll().get(0).getId();
        long otherId = swimmers.findAll().get(1).getId();
        attendance.mark(LocalDate.now(), id, "PRESENT");
        attendance.mark(LocalDate.now().minusDays(1), id, "ABSENT");
        attendance.mark(LocalDate.now(), otherId, "ABSENT");
        mvc.perform(get("/swimmers").param("search", " tOnY "))
                .andExpect(status().isOk()).andExpect(model().attribute("swimmers", hasSize(2)));
        mvc.perform(get("/swimmers").param("search", "Missing"))
                .andExpect(content().string(containsString("No swimmers found")));
        mvc.perform(get("/swimmers/{id}/edit", id)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Tony")));
        mvc.perform(post("/swimmers/{id}/edit", id).param("age", "121").param("group", "Gold"))
                .andExpect(view().name("swimmer-edit")).andExpect(model().attributeExists("error"));
        assertEquals(15, swimmers.findById(id).getAge());
        mvc.perform(post("/swimmers/{id}/edit", id).param("age", "16").param("group", " Senior "))
                .andExpect(redirectedUrl("/swimmers"));
        assertEquals("Senior", swimmers.findById(id).getGroup());
        assertEquals(16, swimmers.findById(id).getAge());
        assertEquals(AttendanceService.Status.PRESENT, attendance.forDate(LocalDate.now()).get(id));
        mvc.perform(get("/swimmers/{id}/remove", id)).andExpect(status().isOk());
        assertEquals(2, swimmers.findAll().size());
        mvc.perform(post("/swimmers/{id}/remove", id)).andExpect(redirectedUrl("/swimmers"));
        assertEquals(1, swimmers.findAll().size());
        assertFalse(attendance.forDate(LocalDate.now()).containsKey(id));
        assertTrue(attendance.forDate(LocalDate.now().minusDays(1)).isEmpty());
        assertEquals(AttendanceService.Status.ABSENT, attendance.forDate(LocalDate.now()).get(otherId));
        mvc.perform(get("/swimmers/{id}/edit", id)).andExpect(status().isNotFound());
        mvc.perform(get("/")).andExpect(model().attribute("groupCount", 1L));
        assertThrows(IllegalArgumentException.class, () -> swimmers.add("Sam", "121", "Gold"));
        assertThrows(IllegalArgumentException.class, () -> swimmers.update(otherId, "15", " "));
        assertThrows(IllegalArgumentException.class, () -> swimmers.update(otherId, "abc", "Gold"));
    }
}
