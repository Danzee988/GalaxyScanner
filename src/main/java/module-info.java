module galaxy.galaxyscanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jmh.core;


    opens galaxy.galaxyscanner to javafx.fxml;
    exports galaxy.galaxyscanner;
    exports Methods;
    opens Methods to javafx.fxml;
}