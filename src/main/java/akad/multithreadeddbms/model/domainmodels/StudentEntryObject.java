package akad.multithreadeddbms.model.domainmodels;

public class StudentEntryObject extends GenericEntryObject {

    int courseNr;

    public StudentEntryObject(String name, int courseNr) {
        super(name);
        this.courseNr = courseNr;
    }
}
