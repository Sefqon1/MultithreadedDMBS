package akad.multithreadeddbms.model.domainmodels;

public class TeacherEntryObject extends GenericEntryObject {

    String courseName;

    public TeacherEntryObject(String name, String courseName) {
        super(name);
        this.courseName = courseName;
    }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }


}


