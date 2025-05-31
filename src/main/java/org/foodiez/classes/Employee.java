package org.foodiez.classes;

import jakarta.persistence.*;

@Entity(name = "employees")
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;

    public static String[] roles = {"admin", "kierowca", "obsługa", "kucharz"};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", rola='" + role + '\'' +
                '}';
    }
}
