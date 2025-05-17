package vetcare360.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private final SimpleStringProperty id = new SimpleStringProperty(this, "id");
    private final SimpleStringProperty name = new SimpleStringProperty(this, "name");
    private final SimpleObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>(this, "birthDate");
    private final SimpleStringProperty type = new SimpleStringProperty(this, "type");

    private List<Visit> visits;
    private Owner owner;

    public Pet() {
        this.visits = new ArrayList<>();
    }

    public Pet(String id, String name, LocalDate birthDate, String type) {
        this();
        setId(id);
        setName(name);
        setBirthDate(birthDate);
        setType(type);
    }

    public final String getId() { return id.get(); }
    public final void setId(String value) { id.set(value); }
    public SimpleStringProperty idProperty() { return id; }

    public final String getName() { return name.get(); }
    public final void setName(String value) { name.set(value); }
    public SimpleStringProperty nameProperty() { return name; }

    public final LocalDate getBirthDate() { return birthDate.get(); }
    public final void setBirthDate(LocalDate value) { birthDate.set(value); }
    public SimpleObjectProperty<LocalDate> birthDateProperty() { return birthDate; }

    public final String getType() { return type.get(); }
    public final void setType(String value) { type.set(value); }
    public SimpleStringProperty typeProperty() { return type; }

    public List<Visit> getVisits() {
        if (visits == null) {
            visits = new ArrayList<>();
        }
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits != null ? visits : new ArrayList<>();
    }

    public void addVisit(Visit visit) {
        if (visits == null) {
            visits = new ArrayList<>();
        }
        if (visit != null && !visits.contains(visit)) {
            visits.add(visit);
            visit.setPet(this);
        }
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        if (owner != null && !owner.getPets().contains(this)) {
            owner.addPet(this);
        }
    }
}