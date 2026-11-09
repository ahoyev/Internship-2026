package SwimTeamManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.application.name=prefix-test")
class ContextPathTests extends FileStorageTestSupport {
    @Autowired private WebApplicationContext context;

    @Test
    void navigationFormsAndRedirectsKeepPrefix() throws Exception {
        var mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc.perform(get("/swimteam-web/").contextPath("/swimteam-web"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("href=\"/swimteam-web/css/style.css\"")))
                .andExpect(content().string(containsString("href=\"/swimteam-web/swimmers/search\"")));
        mvc.perform(get("/swimteam-web/swimmers/search").contextPath("/swimteam-web"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("action=\"/swimteam-web/swimmers/search\"")));
        mvc.perform(get("/swimteam-web/swimmers/new").contextPath("/swimteam-web"))
                .andExpect(content().string(containsString("action=\"/swimteam-web/swimmers\"")));
        mvc.perform(post("/swimteam-web/swimmers").contextPath("/swimteam-web")
                .param("name", "Demo").param("age", "15").param("group", "Gold"))
                .andExpect(redirectedUrl("/swimteam-web/"));
        mvc.perform(get("/swimteam-web/attendance").contextPath("/swimteam-web"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("action=\"/swimteam-web/attendance\"")));
    }
}
