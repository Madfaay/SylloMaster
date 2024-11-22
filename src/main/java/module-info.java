module org.example.syllllogysme {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires kotlin.stdlib;
    requires org.testng;


    opens SyllogismeInterface to javafx.fxml;
    exports SyllogismeInterface;
}