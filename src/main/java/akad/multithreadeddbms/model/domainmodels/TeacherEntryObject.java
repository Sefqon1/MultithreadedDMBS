package akad.multithreadeddbms.model.domainmodels;

public class TeacherEntryObject extends GenericEntryObject {

    String course;

    //Hier wird ein neues Objekt erstellt, welches die Daten eines Lehrers enthält
    public TeacherEntryObject(String name, String course) {
        super(name);
        this.course = course;
    }

    //Hier wird der Kurs zurückgegeben
    public String getCourse() { return course; }
    public void setCourseName(String course) { this.course = course; }


}


