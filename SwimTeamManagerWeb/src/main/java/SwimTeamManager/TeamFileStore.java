package SwimTeamManager;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Stores a complete snapshot before reporting a change as successful. */
@Component
public class TeamFileStore {
    private final Path file;
    private Properties data = new Properties();

    public TeamFileStore(@Value("${swimteam.data-file}") String filename) {
        file = Path.of(filename).toAbsolutePath().normalize();
        if (Files.exists(file)) {
            try (var input = Files.newInputStream(file)) {
                data.loadFromXML(input);
                if (!"1".equals(data.getProperty("version"))) {
                    throw new IOException("Unsupported data version");
                }
                // Check saved values immediately; never replace an unreadable file with empty data.
                loadSwimmers();
                for (String key : data.stringPropertyNames()) {
                    if (key.startsWith("attendance.")) {
                        String[] parts = key.split("\\.");
                        if (parts.length != 3) throw new IOException("Invalid attendance key");
                        LocalDate.parse(parts[1]);
                        Long.parseLong(parts[2]);
                        AttendanceService.Status.valueOf(data.getProperty(key));
                    }
                }
            } catch (IOException | RuntimeException exception) {
                throw new IllegalStateException("Cannot read saved team data: " + file
                        + ". The file has been left untouched.", exception);
            }
        }
    }

    public synchronized List<Swimmer> loadSwimmers() {
        List<Swimmer> result = new ArrayList<>();
        for (String idText : data.getProperty("swimmerIds", "").split(",")) {
            if (idText.isEmpty()) continue;
            long id = Long.parseLong(idText);
            String prefix = "swimmer." + id + ".";
            String name = Objects.requireNonNull(data.getProperty(prefix + "name"));
            String group = Objects.requireNonNull(data.getProperty(prefix + "group"));
            int age = Integer.parseInt(data.getProperty(prefix + "age"));
            if (id < 1 || name.isBlank() || group.isBlank() || age < 1 || age > 120
                    || result.stream().anyMatch(swimmer -> swimmer.getId() == id)) {
                throw new IllegalStateException("Invalid saved swimmer");
            }
            result.add(new Swimmer(id, name, age, group));
        }
        return result;
    }

    public synchronized long nextId() {
        return Long.parseLong(data.getProperty("nextId", "1"));
    }

    public synchronized void saveSwimmers(List<Swimmer> swimmers, long nextId) {
        Properties updated = snapshot();
        updated.keySet().removeIf(key -> key.toString().startsWith("swimmer."));
        Set<Long> ids = new HashSet<>();
        StringJoiner joined = new StringJoiner(",");
        for (Swimmer swimmer : swimmers) {
            ids.add(swimmer.getId());
            joined.add(Long.toString(swimmer.getId()));
            String prefix = "swimmer." + swimmer.getId() + ".";
            updated.setProperty(prefix + "name", swimmer.getName());
            updated.setProperty(prefix + "age", Integer.toString(swimmer.getAge()));
            updated.setProperty(prefix + "group", swimmer.getGroup());
        }
        updated.setProperty("swimmerIds", joined.toString());
        updated.setProperty("nextId", Long.toString(nextId));
        updated.keySet().removeIf(key -> key.toString().startsWith("attendance.")
                && !ids.contains(Long.parseLong(key.toString().split("\\.")[2])));
        write(updated);
    }

    public synchronized Map<Long, AttendanceService.Status> attendanceFor(LocalDate date) {
        Map<Long, AttendanceService.Status> result = new HashMap<>();
        String prefix = "attendance." + date + ".";
        for (String key : data.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                result.put(Long.parseLong(key.substring(prefix.length())),
                        AttendanceService.Status.valueOf(data.getProperty(key)));
            }
        }
        return Map.copyOf(result);
    }

    public synchronized void saveAttendance(LocalDate date, long id, AttendanceService.Status status) {
        Properties updated = snapshot();
        updated.setProperty("attendance." + date + "." + id, status.name());
        write(updated);
    }

    private Properties snapshot() {
        Properties updated = new Properties();
        updated.putAll(data);
        updated.setProperty("version", "1");
        return updated;
    }

    private void write(Properties updated) {
        Path temporary = null;
        try {
            Files.createDirectories(file.getParent());
            temporary = Files.createTempFile(file.getParent(), "team-data-", ".tmp");
            try (var output = Files.newOutputStream(temporary)) {
                updated.storeToXML(output, "SwimTeamManager swimmers, groups and attendance", "UTF-8");
            }
            if (Files.exists(file)) {
                Files.copy(file, file.resolveSibling(file.getFileName() + ".bak"), StandardCopyOption.REPLACE_EXISTING);
            }
            // Keep the old file intact if the filesystem cannot replace it atomically.
            Files.move(temporary, file, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            data = updated;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not save team data to " + file + ". Change was not saved.", exception);
        } finally {
            if (temporary != null) {
                try { Files.deleteIfExists(temporary); } catch (IOException ignored) { }
            }
        }
    }
}
