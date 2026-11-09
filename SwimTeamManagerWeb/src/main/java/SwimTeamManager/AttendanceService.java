package SwimTeamManager;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    public enum Status { PRESENT, ABSENT }

    private final SwimmerService swimmerService;
    private final TeamFileStore store;

    public AttendanceService(SwimmerService swimmerService, TeamFileStore store) {
        this.swimmerService = swimmerService;
        this.store = store;
    }

    public synchronized Map<Long, Status> forDate(LocalDate date) {
        return store.attendanceFor(date);
    }

    public synchronized void removeSwimmer(long swimmerId) {
        swimmerService.remove(swimmerId);
    }

    public synchronized void mark(LocalDate date, long swimmerId, String statusText) {
        if (swimmerService.findAll().stream().noneMatch(swimmer -> swimmer.getId() == swimmerId)) {
            throw new IllegalArgumentException("That swimmer no longer exists. Please refresh the page.");
        }
        Status status;
        try {
            status = Status.valueOf(statusText);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Choose Present or Absent.");
        }
        // Replaces this swimmer's previous mark for this date, avoiding duplicates.
        store.saveAttendance(date, swimmerId, status);
    }
}
