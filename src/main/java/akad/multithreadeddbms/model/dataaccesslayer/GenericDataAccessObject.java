package akad.multithreadeddbms.model.dataaccesslayer;

import akad.multithreadeddbms.model.domainmodels.*;

import java.sql.*;

public class GenericDataAccessObject {
    Connection connection;
    GenericEntryObject newObject;
    public GenericDataAccessObject(Connection connection) {
        this.connection = connection;
    }

}
