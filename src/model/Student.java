package model;

public class Student {
    private int studentId;
    private String name;
    private String rollNo;
    private String email;
    private int leaveBalance;

    public Student(int studentId, String name, String rollNo, String email, int leaveBalance) {
        this.studentId = studentId;
        this.name = name;
        this.rollNo = rollNo;
        this.email = email;
        this.leaveBalance = leaveBalance;
    }

    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public String getEmail() { return email; }
    public int getLeaveBalance() { return leaveBalance; }

    public void setLeaveBalance(int leaveBalance) { this.leaveBalance = leaveBalance; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %s | Leave Balance: %d",
                studentId, name, rollNo, email, leaveBalance);
    }
}
