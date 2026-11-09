package SwimTeamManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SwimmerFlowTests extends FileStorageTestSupport {
    @Autowired
    private WebApplicationContext context;

    @Test
    void savesSwimmersRendersDashboardAndRejectsInvalidInput() throws Exception {
        var mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc.perform(get("/swimmers/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Save Swimmer")));
        mvc.perform(post("/swimmers").param("name", " Tony ")
                .param("age", "15").param("group", " Gold "))
                .andExpect(redirectedUrl("/"));
        mvc.perform(post("/swimmers").param("name", "Alex")
                .param("age", "12").param("group", "gold"))
                .andExpect(redirectedUrl("/"));
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groupCount", 1L))
                .andExpect(content().string(containsString("<td>Tony</td>")))
                .andExpect(content().string(containsString("<td>15</td>")));
        for (String age : new String[] {"0", "-1", "abc", "1.5", ""}) {
            mvc.perform(post("/swimmers").param("name", "Sam")
                    .param("age", age).param("group", "Silver"))
                    .andExpect(view().name("swimmer-form"))
                    .andExpect(model().attributeExists("error"));
        }
        mvc.perform(post("/swimmers").param("name", " ")
                .param("age", "15").param("group", "Gold"))
                .andExpect(model().attributeExists("error"));
        mvc.perform(post("/swimmers").param("name", "Sam")
                .param("age", "15").param("group", " "))
                .andExpect(model().attributeExists("error"));
        mvc.perform(get("/"))
                .andExpect(model().attribute("swimmers", org.hamcrest.Matchers.hasSize(2)));
    }
}
