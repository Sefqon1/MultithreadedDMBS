package akad.multithreadeddbms.model.domainmodels;

public class TeacherEntryObject extends GenericEntryObject {

    String subject;

    //Hier wird ein neues Objekt erstellt, welches die Daten eines Lehrers enthält
    public TeacherEntryObject(String name, String subject) {
        super(name);
        this.subject = subject;
    }

    //Hier wird der Kurs zurückgegeben
    public String getCourse() { return subject; }
    public void setCourseName(String subject) { this.subject = subject; }


}


