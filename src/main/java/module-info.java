module org.radek.restauracja {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;

    opens org.radek.restauracja to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.restauracja;
    exports org.radek.restauracja.controllers;
    opens org.radek.restauracja.controllers to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.restauracja.classes;
    opens org.radek.restauracja.classes to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.restauracja.exceptions;
    opens org.radek.restauracja.exceptions to javafx.fxml, org.hibernate.orm.core;
}