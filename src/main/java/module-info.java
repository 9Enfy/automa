module it.univr.wordautoma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens it.univr.wordautoma to javafx.fxml;
    exports it.univr.wordautoma;
    exports it.univr.wordautoma.controllers;
    opens it.univr.wordautoma.controllers to javafx.fxml;
    exports it.univr.wordautoma.ComponentiAutoma;
    opens it.univr.wordautoma.ComponentiAutoma to javafx.fxml;
}