package app.model;

public class ToDo {

    private int id;
    private String name;
    private String description;
    private boolean isDone;

    public ToDo() {
        isDone = false;
    }

    public ToDo(String name, String description) {
        this();
        setName(name);
        setDescription(description);
    }

    public ToDo(int id, String name, String description, boolean isDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isDone() {
        return isDone ? "Finished" : "In progress";
    }

    public void changeStatus() {
        isDone = !isDone;
    }

}
