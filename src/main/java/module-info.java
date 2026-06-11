module app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.validator;
    requires java.desktop;

    // iText 7.2.5 jars have no Automatic-Module-Name; names are derived from the jar files.
    requires kernel;
    requires layout;

    opens app to javafx.fxml;
    exports app;

    opens gui to javafx.fxml;
    exports gui;
    exports gui.controllers;
    opens gui.controllers to javafx.fxml;
}
