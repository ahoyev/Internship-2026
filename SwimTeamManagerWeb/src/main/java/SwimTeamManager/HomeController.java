package SwimTeamManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;

@Controller
public class HomeController {
    private final SwimmerService swimmerService;
    private final AttendanceService attendanceService;

    public HomeController(SwimmerService swimmerService, AttendanceService attendanceService) {
        this.swimmerService = swimmerService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/")
    public String home(Model model) {
        var swimmers = swimmerService.findAll();
        model.addAttribute("swimmers", swimmers);
        model.addAttribute("presentToday", attendanceService.forDate(java.time.LocalDate.now())
                .values().stream().filter(status -> status == AttendanceService.Status.PRESENT).count());
        model.addAttribute("groupCount", swimmers.stream()
                .map(swimmer -> swimmer.getGroup().toLowerCase(Locale.ROOT))
                .distinct().count());
        return "index";
    }

    @GetMapping("/swimmers/new")
    public String newSwimmer() {
        return "swimmer-form";
    }

    @GetMapping("/swimmers")
    public String swimmers(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("swimmers", swimmerService.search(search));
        model.addAttribute("search", search);
        model.addAttribute("mode", "all");
        return "swimmers";
    }

    @GetMapping("/swimmers/search")
    public String searchSwimmers(@RequestParam(defaultValue = "") String search, Model model) {
        swimmers(search, model);
        model.addAttribute("mode", "search");
        return "swimmers";
    }

    @GetMapping("/swimmers/edit")
    public String chooseSwimmerToEdit(Model model) {
        swimmers("", model);
        model.addAttribute("mode", "edit");
        return "swimmers";
    }

    @GetMapping("/swimmers/remove")
    public String chooseSwimmerToRemove(Model model) {
        swimmers("", model);
        model.addAttribute("mode", "remove");
        return "swimmers";
    }

    @GetMapping("/swimmers/{id}/edit")
    public String editSwimmer(@PathVariable long id, Model model) {
        var swimmer = swimmerService.findById(id);
        model.addAttribute("swimmer", swimmer);
        model.addAttribute("age", swimmer.getAge());
        model.addAttribute("group", swimmer.getGroup());
        return "swimmer-edit";
    }

    @PostMapping("/swimmers/{id}/edit")
    public String updateSwimmer(@PathVariable long id,
            @RequestParam(defaultValue = "") String age,
            @RequestParam(defaultValue = "") String group, Model model, RedirectAttributes redirect) {
        var swimmer = swimmerService.findById(id);
        try {
            swimmerService.update(id, age, group);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("swimmer", swimmer);
            model.addAttribute("age", age);
            model.addAttribute("group", group);
            model.addAttribute("error", exception.getMessage());
            return "swimmer-edit";
        }
        redirect.addFlashAttribute("message", "Swimmer updated.");
        return "redirect:/swimmers";
    }

    @GetMapping("/swimmers/{id}/remove")
    public String confirmRemoval(@PathVariable long id, Model model) {
        model.addAttribute("swimmer", swimmerService.findById(id));
        return "swimmer-remove";
    }

    @PostMapping("/swimmers/{id}/remove")
    public String removeSwimmer(@PathVariable long id, RedirectAttributes redirect) {
        attendanceService.removeSwimmer(id);
        redirect.addFlashAttribute("message", "Swimmer and their attendance records removed.");
        return "redirect:/swimmers";
    }

    @PostMapping("/swimmers")
    public String saveSwimmer(@RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String age,
            @RequestParam(defaultValue = "") String group, Model model) {
        try {
            swimmerService.add(name, age, group);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("name", name);
            model.addAttribute("age", age);
            model.addAttribute("group", group);
            return "swimmer-form";
        }
        // Redirect so refreshing the dashboard does not submit the form again.
        return "redirect:/";
    }
}
