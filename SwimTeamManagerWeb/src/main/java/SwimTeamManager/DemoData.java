package SwimTeamManager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("demo")
public class DemoData implements CommandLineRunner {
    private final SwimmerService swimmers;

    public DemoData(SwimmerService swimmers) {
        this.swimmers = swimmers;
    }

    @Override
    public void run(String... args) {
        if (swimmers.findAll().isEmpty()) {
            swimmers.add("Alex Demo", "15", "Gold");
            swimmers.add("Jordan Sample", "12", "Silver");
            swimmers.add("Taylor Example", "16", "Gold");
        }
    }
}
