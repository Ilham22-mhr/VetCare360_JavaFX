package vetcare360.models;

public class Veterinarian {
    private String id;
    private String name;
    private String specialties;

    public Veterinarian() {
    }

    public Veterinarian(String id, String name, String specialties) {
        this.id = id;
        this.name = name;
        this.specialties = specialties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }
}
