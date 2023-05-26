module akad.multithreadeddbms {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
    requires java.sql;

    opens akad.multithreadeddbms to javafx.fxml;
    exports akad.multithreadeddbms;
    exports akad.multithreadeddbms.view.presentationlayer;
    opens akad.multithreadeddbms.view.presentationlayer to javafx.fxml;
}