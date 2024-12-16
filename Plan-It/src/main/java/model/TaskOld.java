package model;

public class TaskOld implements SystemComponent {
    private String name;

    public TaskOld(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void display() {
        System.out.println("Task : " + name);
    }
}
