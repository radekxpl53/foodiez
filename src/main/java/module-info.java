module org.radek.restauracja {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;
    requires bcrypt;
    requires org.postgresql.jdbc;
    requires org.apache.commons.validator;

    opens org.radek.foodiez to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.foodiez;
    exports org.radek.foodiez.controllers;
    opens org.radek.foodiez.controllers to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.foodiez.classes;
    opens org.radek.foodiez.classes to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.foodiez.exceptions;
    opens org.radek.foodiez.exceptions to javafx.fxml, org.hibernate.orm.core;
}