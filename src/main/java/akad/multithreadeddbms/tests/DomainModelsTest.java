package akad.multithreadeddbms.tests;

import akad.multithreadeddbms.model.domainmodels.GenericEntryObject;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;
public class DomainModelsTest {


    public static boolean testTeacherEntryObject() {
        TeacherEntryObject teacher = new TeacherEntryObject("John Doe", "Math");
        if (teacher.getName().equals("John Doe") && teacher.getCourse().equals("Math")) {
            return true;
        }
        return false;
    }

    public static boolean testGenericEntryObject() {
        GenericEntryObject generic = new GenericEntryObject("John Doe");
        if (generic.getName().equals("John Doe")) {
            return true;
        }
        return false;
    }
}
