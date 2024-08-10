module com.peterestephan.chattingclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.peterestephan.chattingclient to javafx.fxml;
    exports com.peterestephan.chattingclient;
}