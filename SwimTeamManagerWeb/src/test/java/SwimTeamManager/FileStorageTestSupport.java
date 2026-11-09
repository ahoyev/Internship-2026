package SwimTeamManager;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

abstract class FileStorageTestSupport {
    @DynamicPropertySource
    static void temporaryStorage(DynamicPropertyRegistry registry) throws IOException {
        var file = Files.createTempDirectory("swimteam-test-").resolve("team-data.xml");
        registry.add("swimteam.data-file", () -> file.toString());
    }
}
