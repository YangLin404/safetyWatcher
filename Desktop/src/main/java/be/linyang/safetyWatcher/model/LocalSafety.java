package be.linyang.safetyWatcher.model;

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
        if (!this.available)
            this.setExpiryDate(null);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalSafety that = (LocalSafety) o;

        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (local != null ? local.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        return result;
    }
}
