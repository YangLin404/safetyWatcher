package be.cvo.linyang.safetyWatcher.Controllers;

import be.cvo.linyang.safetyWatcher.Model.Local;
import be.cvo.linyang.safetyWatcher.Repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yanglin on 26/05/17.
 */

@Controller
public class DetailController {

    @Autowired
    private LocalRepository localRepository;

    @RequestMapping("/detail")
    public String detail(@RequestParam(value="localName", required=false, defaultValue="1") String localName, Model model) {
        //Local local = localRepository.findOne(Long.valueOf(localId));
        Local local = localRepository.findByName(localName);
        model.addAttribute("local", local);
        model.addAttribute("locals", localRepository.findAll());
        return "detail";
    }
}
