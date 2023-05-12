package akad.multithreadeddbms.model.domainmodels;

public class GenericEntryObject {

    public String name;

    //Hier wird ein neues Objekt erstellt
    public GenericEntryObject(String name) {
        this.name = name;
    }

    //Hier wird der Name zurückgegeben
    public String getName() { return name; }
    //Hier wird der Name gesetzt
    public void setName(String name) { this.name = name;  }

}
