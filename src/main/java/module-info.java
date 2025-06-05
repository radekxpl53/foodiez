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
    requires mail;

    opens org.foodiez to javafx.fxml, org.hibernate.orm.core;
    exports org.foodiez;
    exports org.foodiez.controllers;
    opens org.foodiez.controllers to javafx.fxml, org.hibernate.orm.core;
    exports org.foodiez.models;
    opens org.foodiez.models to javafx.fxml, org.hibernate.orm.core;
    exports org.foodiez.exceptions;
    opens org.foodiez.exceptions to javafx.fxml, org.hibernate.orm.core;
    exports org.foodiez.util;
    opens org.foodiez.util to javafx.fxml, org.hibernate.orm.core;
}