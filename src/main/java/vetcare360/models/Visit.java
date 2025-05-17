package vetcare360.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;

public class Visit {
    private SimpleStringProperty id = new SimpleStringProperty(this, "id");
    private SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date");
    private SimpleStringProperty description = new SimpleStringProperty(this, "description");
    private Pet pet;

    public Visit(String id, LocalDate date, String description, Pet pet) {
        this.id.set(id);
        this.date.set(date);
        this.description.set(description);
        this.pet = pet;
    }

    public final String getId() { return id.get(); }
    public final void setId(String value) { id.set(value); }
    public SimpleStringProperty idProperty() { return id; }

    public final LocalDate getDate() { return date.get(); }
    public final void setDate(LocalDate value) { date.set(value); }
    public SimpleObjectProperty<LocalDate> dateProperty() { return date; }

    public final String getDescription() { return description.get(); }
    public final void setDescription(String value) { description.set(value); }
    public SimpleStringProperty descriptionProperty() { return description; }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}