package org.foodiez.models;

public class CurrentUser {
    private static Customer customer = new Customer();
    private static Employee employee = new Employee();

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        CurrentUser.customer = customer;
    }


    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        CurrentUser.employee = employee;
    }
}
