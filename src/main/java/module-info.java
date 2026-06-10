module app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.validator;
    requires java.desktop;

    opens app to javafx.fxml;
    exports app;

    opens gui to javafx.fxml;
    exports gui;
    exports gui.controllers;
    opens gui.controllers to javafx.fxml;
}
