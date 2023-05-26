package akad.multithreadeddbms.controller.applicationlayer;

import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;

public class InputHandler {

    // Diese Methode Validiert die Eingabe und gibt einen Boolean Wert zurueck.
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

    // Diese Methode konvertiert die Eingabe in ein TeacherEntryObject.
    public static TeacherEntryObject convertInputToTeacherObject(String name, String course) {

        return new TeacherEntryObject(name, course);
    }



}
