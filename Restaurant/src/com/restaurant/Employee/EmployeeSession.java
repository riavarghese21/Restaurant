package com.restaurant.Employee;

import com.restaurant.Employee.EmployeeSession;

public class EmployeeSession {
    private static EmployeeSession instance;
    private int EmployeeId;

    private EmployeeSession() {}

    public static EmployeeSession getInstance() {
        if (instance == null) {
            instance = new EmployeeSession();
        }
        return instance;
    }

    public void setEmployeeId(int EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }
}
