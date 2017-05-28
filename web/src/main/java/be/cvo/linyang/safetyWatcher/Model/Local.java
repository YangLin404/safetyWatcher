

package be.cvo.linyang.safetyWatcher.Model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by yanglin on 26/05/17.
 */
@Entity
public class Local {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
    private Set<LocalSafety> safeties;

    public Local()
    {

    }

    public Local(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Local(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LocalSafety> getSafeties() {
        return safeties;
    }

    public void setSafeties(Set<LocalSafety> safeties) {
        this.safeties = safeties;
    }
}
