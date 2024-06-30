module it.univr.wordautoma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens it.univr.wordautoma to javafx.fxml;
    exports it.univr.wordautoma;
}