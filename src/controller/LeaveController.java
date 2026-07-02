package controller;

import dao.LeaveRequestDAO;
import dao.StudentDAO;
import model.LeaveRequest;
import model.Student;

import java.sql.Date;
import java.util.List;

public class LeaveController {

    private final StudentDAO studentDAO = new StudentDAO();
    private final LeaveRequestDAO leaveDAO = new LeaveRequestDAO();

    public boolean registerStudent(String name, String rollNo, String email, int initialBalance) {
        Student s = new Student(0, name, rollNo, email, initialBalance);
        return studentDAO.addStudent(s);
    }

    public List<Student> listStudents() {
        return studentDAO.getAllStudents();
    }

    public String applyForLeave(int studentId, Date from, Date to, String reason) {
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            return "Student not found.";
        }

        LeaveRequest tempRequest = new LeaveRequest(0, studentId, from, to, reason, "PENDING");
        long daysRequested = tempRequest.getDaysRequested();

        if (daysRequested <= 0) {
            return "Invalid date range.";
        }
        if (daysRequested > student.getLeaveBalance()) {
            return "Insufficient leave balance. Available: " + student.getLeaveBalance() + " day(s).";
        }

        boolean success = leaveDAO.applyLeave(tempRequest);
        return success ? "Leave request submitted successfully (PENDING approval)." : "Failed to submit leave request.";
    }

    public String reviewLeave(int leaveId, boolean approve) {
        List<LeaveRequest> pending = leaveDAO.getPendingRequests();
        LeaveRequest target = pending.stream()
                .filter(r -> r.getLeaveId() == leaveId)
                .findFirst()
                .orElse(null);

        if (target == null) {
            return "No pending request found with that ID.";
        }

        String newStatus = approve ? "APPROVED" : "REJECTED";
        boolean updated = leaveDAO.updateStatus(leaveId, newStatus);

        if (updated && approve) {
            Student s = studentDAO.getStudentById(target.getStudentId());
            if (s != null) {
                int newBalance = s.getLeaveBalance() - (int) target.getDaysRequested();
                studentDAO.updateLeaveBalance(s.getStudentId(), Math.max(newBalance, 0));
            }
        }

        return updated ? "Leave request " + newStatus + "." : "Failed to update leave request.";
    }

    public List<LeaveRequest> viewPendingRequests() {
        return leaveDAO.getPendingRequests();
    }

    public List<LeaveRequest> viewStudentHistory(int studentId) {
        return leaveDAO.getRequestsByStudent(studentId);
    }

    public List<LeaveRequest> viewAllRequests() {
        return leaveDAO.getAllRequests();
    }
}
