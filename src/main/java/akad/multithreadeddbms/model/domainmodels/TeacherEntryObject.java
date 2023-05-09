package akad.multithreadeddbms.model.domainmodels;

public class TeacherEntryObject extends GenericEntryObject {

    String subject;

    public TeacherEntryObject(String name, String subject) {
        super(name);
        this.subject = subject;
    }

    public String getCourse() { return subject; }
    public void setCourseName(String subject) { this.subject = subject; }


}


