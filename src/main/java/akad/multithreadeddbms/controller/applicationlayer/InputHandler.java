package akad.multithreadeddbms.controller.applicationlayer;

import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;

public class InputHandler {

    public static boolean validateInput(String name, String course) {
        if (name == null || name.isEmpty()) {
            System.out.println("Name is empty.");
            return false;
        } else if (course == null || course.isEmpty()) {
            System.out.println("Course is empty.");
            return false;
        } else {
            return true;
        }
    }

    public static TeacherEntryObject convertInputToTeacherObject(String name, String course) {

        return new TeacherEntryObject(name, course);
    }



}
