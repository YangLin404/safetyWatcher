package be.cvo.linyang.safetyWatcher.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class LocalSafety {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private Local local;
    private boolean available;

    public LocalSafety()
    {

    }

    public LocalSafety(long id, String name, LocalDate expiryDate, Local local, boolean available) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.local = local;
        this.available = available;
    }

    public LocalSafety(String name, LocalDate expiryDate, Local local, boolean available) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.local = local;
        this.available = available;
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

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isExpired()
    {
        return expiryDate.isBefore(LocalDate.now());
    }
}
