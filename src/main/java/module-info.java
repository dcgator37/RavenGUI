module com.example.wordcountravengui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.wordcountravengui to javafx.fxml;
    exports com.example.wordcountravengui;
}