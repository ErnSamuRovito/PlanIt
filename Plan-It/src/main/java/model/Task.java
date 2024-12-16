package model;

public class Task implements SystemComponent {
    private String name;

    public Task(String name) {
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
