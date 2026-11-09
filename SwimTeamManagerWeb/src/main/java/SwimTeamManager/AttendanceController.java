package SwimTeamManager;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AttendanceController {
    private final SwimmerService swimmers;
    private final AttendanceService attendance;

    public AttendanceController(SwimmerService swimmers, AttendanceService attendance) {
        this.swimmers = swimmers;
        this.attendance = attendance;
    }

    @GetMapping("/attendance")
    public String attendance(@RequestParam(required = false) LocalDate date, Model model) {
        LocalDate selectedDate = date == null ? LocalDate.now() : date;
        var records = attendance.forDate(selectedDate);
        model.addAttribute("date", selectedDate);
        model.addAttribute("swimmers", swimmers.findAll());
        model.addAttribute("records", records);
        model.addAttribute("presentCount", records.values().stream()
                .filter(status -> status == AttendanceService.Status.PRESENT).count());
        model.addAttribute("absentCount", records.values().stream()
                .filter(status -> status == AttendanceService.Status.ABSENT).count());
        return "attendance";
    }

    @PostMapping("/attendance")
    public String mark(@RequestParam LocalDate date, @RequestParam long swimmerId,
            @RequestParam(defaultValue = "") String status, RedirectAttributes redirect) {
        try {
            attendance.mark(date, swimmerId, status);
            redirect.addFlashAttribute("message", "Attendance saved.");
        } catch (IllegalArgumentException exception) {
            redirect.addFlashAttribute("error", exception.getMessage());
        }
        redirect.addAttribute("date", date.toString());
        return "redirect:/attendance";
    }
}
