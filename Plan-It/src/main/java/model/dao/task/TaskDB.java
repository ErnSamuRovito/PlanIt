package model.dao.task;

public class TaskDB {
    private int idTask;
    private String title;
    private String description;
    private String dueDate;
    private int urgency;
    private int folder; //fk
    private int state;
    private int type;
    private String extraInfo;

    public TaskDB(String title, String description, String dueDate, int urgency, int folder, int state, int type, String extraInfo) {
        //this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.urgency = urgency;
        this.folder = folder;
        this.state = state;
        this.type = type;
        this.extraInfo = extraInfo;
    }

    public int getIdTask() {return idTask;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getDueDate() {return dueDate;}
    public int getUrgency() {return urgency;}
    public int getFolder() {return folder;}
    public int getState() {return state;}
    public int getType() {return type;}
    public String getExtraInfo() {return extraInfo;}

    public void setIdTask(int idTask) {this.idTask = idTask;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setDueDate(String dueDate) {this.dueDate = dueDate;}
    public void setUrgency(int urgency) {this.urgency = urgency;}
    public void setFolder(int folder) {this.folder = folder;}
    public void setState(int state) {this.state = state;}
    public void setType(int type) {this.type = type;}
    public void setExtraInfo(String extraInfo) {this.extraInfo = extraInfo;}
}
