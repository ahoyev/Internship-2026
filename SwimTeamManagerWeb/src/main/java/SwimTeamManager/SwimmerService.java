package SwimTeamManager;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SwimmerService {
    private final List<Swimmer> swimmers;
    private long nextId;
    private final TeamFileStore store;

    public SwimmerService(TeamFileStore store) {
        this.store = store;
        swimmers = new ArrayList<>(store.loadSwimmers());
        nextId = Math.max(store.nextId(), swimmers.stream().mapToLong(Swimmer::getId).max().orElse(0) + 1);
    }

    private void save(List<Swimmer> updated, long updatedNextId) {
        store.saveSwimmers(updated, updatedNextId);
        swimmers.clear();
        swimmers.addAll(updated);
        nextId = updatedNextId;
    }

    public synchronized List<Swimmer> findAll() {
        return List.copyOf(swimmers);
    }

    public synchronized List<Swimmer> search(String name) {
        if (name == null || name.isBlank()) {
            return findAll();
        }
        return swimmers.stream()
                .filter(swimmer -> swimmer.getName().equalsIgnoreCase(name.strip())).toList();
    }

    public synchronized Swimmer findById(long id) {
        return swimmers.stream().filter(swimmer -> swimmer.getId() == id).findFirst()
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Swimmer not found"));
    }

    public synchronized void update(long id, String ageText, String group) {
        Swimmer current = findById(id);
        int age = validate(current.getName(), ageText, group);
        var updated = new ArrayList<>(swimmers);
        updated.set(swimmers.indexOf(current), new Swimmer(id, current.getName(), age, group.strip()));
        save(updated, nextId);
    }

    public synchronized void remove(long id) {
        var updated = new ArrayList<>(swimmers);
        updated.remove(findById(id));
        save(updated, nextId);
    }

    public synchronized void add(String name, String ageText, String group) {
        int age = validate(name, ageText, group);
        var updated = new ArrayList<>(swimmers);
        updated.add(new Swimmer(nextId, name.strip(), age, group.strip()));
        save(updated, nextId + 1);
    }

    private int validate(String name, String ageText, String group) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Please enter a swimmer name.");
        }
        if (group == null || group.isBlank()) {
            throw new IllegalArgumentException("Please enter a swim group.");
        }
        int age;
        try {
            age = Integer.parseInt(ageText == null ? "" : ageText.strip());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please enter a whole-number age between 1 and 120.");
        }
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Please enter a whole-number age between 1 and 120.");
        }
        return age;
    }
}
