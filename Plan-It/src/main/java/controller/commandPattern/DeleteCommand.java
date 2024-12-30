package controller.commandPattern;

public class DeleteCommand implements ActionCommand{
    @Override
    public void execute() {
        System.out.println("Delete command executed");
    }
}
