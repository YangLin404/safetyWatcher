package be.linyang.safetyWatcher.Services;

import be.linyang.safetyWatcher.model.Local;
import be.linyang.safetyWatcher.model.LocalSafety;
import be.linyang.safetyWatcher.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanglin on 28/05/17.
 */

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;


    public LocalService()
    {

    }


    public List<String> getAllLocalName()
    {
        List<String> localNames = new ArrayList<>();
        localRepository.findAll().forEach(l -> {
            localNames.add(l.getName());
        });
        return localNames;
    }

    public Local getLocalByName(String name)
    {
        return localRepository.findByName(name);
    }

    public Local saveLocal(Local local)
    {
        return localRepository.save(local);
    }

    public Local removeSafety(LocalSafety safety)
    {
        Local local = safety.getLocal();
        local.getSafeties().remove(safety);
        return saveLocal(local);
    }

    public Local addSafety(LocalSafety safety)
    {
        safety.getLocal().getSafeties().add(safety);
        return saveLocal(safety.getLocal());
    }

    public void updateSafety(LocalSafety safety)
    {
        saveLocal(safety.getLocal());
    }
}
