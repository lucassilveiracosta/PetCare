module com.example.testejavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.validator;
    requires java.desktop;

    opens com.example.testejavafx to javafx.fxml;
    exports com.example.testejavafx;

    opens gui to javafx.fxml;
}