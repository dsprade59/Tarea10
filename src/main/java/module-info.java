module org.example {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.jfree.jfreechart;
    requires java.desktop;
    requires org.apache.pdfbox;

    opens org.example to javafx.fxml;
    exports org.example;
}
