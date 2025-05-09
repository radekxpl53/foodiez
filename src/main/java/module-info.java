module org.radek.restauracja {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;

    opens org.radek.restauracja to javafx.fxml, org.hibernate.orm.core;
    exports org.radek.restauracja;
}