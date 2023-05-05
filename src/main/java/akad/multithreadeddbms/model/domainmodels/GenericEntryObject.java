package akad.multithreadeddbms.model.domainmodels;

public abstract class GenericEntryObject {

    public String name;
    int id;

    public GenericEntryObject(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name;  }

}
