package be.cvo.linyang.safetyWatcher.Controllers;

import be.cvo.linyang.safetyWatcher.Model.Local;
import be.cvo.linyang.safetyWatcher.Model.LocalSafety;
import be.cvo.linyang.safetyWatcher.Repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by yanglin on 26/05/17.
 */
@Controller
public class HomeController {

    private final String[] locals = {"Groep 1 en 2", "Groep 3, 4 en 5", "Groep 6, 7 en 8",
                                    "Magazijn 1", "Directie kamer", "WC 1", "Gang", "Koffiekamer",
                                    "Magazijn 2", "WC 2", "Kast", "Kantoor team", "WC 3"};

    @Autowired
    private LocalRepository localRepository;


    @PostConstruct
    private void init()
    {
        Random random = new Random(System.currentTimeMillis());
        Arrays.stream(locals)
                .forEach(l -> {
                    Local local = new Local(l);
                    Set<LocalSafety> safeties = new HashSet<>();
                    int num1 = random.nextInt(2);
                    safeties.add(new LocalSafety("brandblusser",LocalDate.now().plusYears(2),local, num1 == 0));
                    num1 = random.nextInt(2);
                    safeties.add(new LocalSafety("rookmelder",LocalDate.now().plusYears(3),local,num1 == 0));
                    num1 = random.nextInt(2);
                    safeties.add(new LocalSafety("ehbo kit",LocalDate.now().plusYears(3),local,num1 == 0));
                    local.setSafeties(safeties);
                    localRepository.save(local);
                });
    }


    @RequestMapping("/")
    public String index(Model model) {
        List<Local> locals = localRepository.findAll();
        locals.forEach(e -> System.out.println(e.getName()));
        model.addAttribute("locals",localRepository.findAll());

        return "index";
    }

}
