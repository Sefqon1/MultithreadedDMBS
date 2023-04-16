module akad.multithreadeddbms {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                        
    opens akad.multithreadeddbms to javafx.fxml;
    exports akad.multithreadeddbms;
}