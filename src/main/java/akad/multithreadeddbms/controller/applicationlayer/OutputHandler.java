package akad.multithreadeddbms.controller.applicationlayer;

public class OutputHandler {

    public static int parseAndValidateQuery(String query) {

        int id;
        try {
            id = Integer.parseInt(query);
        } catch (NumberFormatException e) {
            return -1;
        }
        return id;
    }

}
