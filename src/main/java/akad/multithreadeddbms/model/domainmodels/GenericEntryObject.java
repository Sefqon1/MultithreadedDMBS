package akad.multithreadeddbms.model.domainmodels;

public abstract class GenericEntryObject {

    int id;
    String name;

    public GenericEntryObject(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name;  }

}
