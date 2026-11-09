package SwimTeamManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.application.name=attendance-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AttendanceFlowTests extends FileStorageTestSupport {
    @Autowired private WebApplicationContext context;
    @Autowired private SwimmerService swimmers;
    @Autowired private AttendanceService attendance;

    @Test
    void listsNamesAndKeepsIndependentDailyAttendance() throws Exception {
        var mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc.perform(get("/attendance")).andExpect(status().isOk())
                .andExpect(content().string(containsString("No swimmers yet.")));
        swimmers.add("Tony", "15", "Gold");
        swimmers.add("Tony", "12", "Silver");
        long firstId = swimmers.findAll().get(0).getId();
        long secondId = swimmers.findAll().get(1).getId();
        String today = LocalDate.now().toString();
        mvc.perform(get("/swimmers")).andExpect(status().isOk())
                .andExpect(model().attribute("swimmers", hasSize(2)))
                .andExpect(content().string(containsString("Tony")));
        mvc.perform(get("/attendance")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Not marked")));
        mvc.perform(post("/attendance").param("date", today)
                .param("swimmerId", Long.toString(firstId)).param("status", "PRESENT"))
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/")).andExpect(model().attribute("presentToday", 1L));
        mvc.perform(post("/attendance").param("date", today)
                .param("swimmerId", Long.toString(secondId)).param("status", "ABSENT"))
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/attendance").param("date", today))
                .andExpect(model().attribute("presentCount", 1L))
                .andExpect(model().attribute("absentCount", 1L))
                .andExpect(content().string(containsString(">Present</td>")))
                .andExpect(content().string(containsString(">Absent</td>")));
        mvc.perform(post("/attendance").param("date", today)
                .param("swimmerId", Long.toString(firstId)).param("status", "ABSENT"))
                .andExpect(status().is3xxRedirection());
        assertEquals(2, attendance.forDate(LocalDate.now()).size());
        mvc.perform(get("/")).andExpect(model().attribute("presentToday", 0L));
        mvc.perform(get("/attendance").param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(model().attribute("presentCount", 0L))
                .andExpect(model().attribute("absentCount", 0L));
        assertThrows(IllegalArgumentException.class,
                () -> attendance.mark(LocalDate.now(), firstId, "INVALID"));
        assertThrows(IllegalArgumentException.class,
                () -> attendance.mark(LocalDate.now(), -1, "PRESENT"));
        mvc.perform(get("/attendance").param("date", "invalid"))
                .andExpect(status().isBadRequest());
    }
}
