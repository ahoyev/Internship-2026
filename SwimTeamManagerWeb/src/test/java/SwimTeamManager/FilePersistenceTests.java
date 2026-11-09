package SwimTeamManager;

import java.nio.file.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class FilePersistenceTests {
    @TempDir Path directory;

    @Test
    void reloadsSwimmersGroupsAndAttendanceThenPersistsEditsAndRemoval() throws Exception {
        String file = directory.resolve("team-data.xml").toString();
        var store = new TeamFileStore(file);
        var swimmers = new SwimmerService(store);
        var attendance = new AttendanceService(swimmers, store);
        swimmers.add("Zoë & <Tony>", "15", "Gold & Silver");
        long id = swimmers.findAll().get(0).getId();
        attendance.mark(LocalDate.now(), id, "PRESENT");
        attendance.mark(LocalDate.now().minusDays(1), id, "ABSENT");

        var reloaded = new TeamFileStore(file);
        var restartedSwimmers = new SwimmerService(reloaded);
        var restartedAttendance = new AttendanceService(restartedSwimmers, reloaded);
        assertEquals("Zoë & <Tony>", restartedSwimmers.findById(id).getName());
        assertEquals("Gold & Silver", restartedSwimmers.findById(id).getGroup());
        assertEquals(AttendanceService.Status.PRESENT, restartedAttendance.forDate(LocalDate.now()).get(id));
        assertEquals(AttendanceService.Status.ABSENT, restartedAttendance.forDate(LocalDate.now().minusDays(1)).get(id));
        restartedSwimmers.update(id, "16", "Senior");
        assertEquals("Senior", new SwimmerService(new TeamFileStore(file)).findById(id).getGroup());
        restartedAttendance.removeSwimmer(id);
        var afterRemoval = new TeamFileStore(file);
        assertTrue(afterRemoval.loadSwimmers().isEmpty());
        assertTrue(afterRemoval.attendanceFor(LocalDate.now()).isEmpty());
        restartedSwimmers.add("Next", "12", "Gold");
        assertTrue(restartedSwimmers.findAll().get(0).getId() > id);
        assertTrue(Files.exists(Path.of(file + ".bak")));
    }

    @Test
    void unreadableFileIsNeverOverwritten() throws Exception {
        Path file = directory.resolve("bad.xml");
        Files.writeString(file, "broken data");
        assertThrows(IllegalStateException.class, () -> new TeamFileStore(file.toString()));
        assertEquals("broken data", Files.readString(file));
    }

    @Test
    void failedSaveDoesNotChangeInMemoryRoster() throws Exception {
        Path parent = directory.resolve("blocked");
        var store = new TeamFileStore(parent.resolve("team.xml").toString());
        var swimmers = new SwimmerService(store);
        Files.writeString(parent, "This is a file, not a folder");
        assertThrows(IllegalStateException.class, () -> swimmers.add("Tony", "15", "Gold"));
        assertTrue(swimmers.findAll().isEmpty());
    }
}
