package model;

import java.sql.Date;

public class LeaveRequest {
    private int leaveId;
    private int studentId;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String status; // PENDING, APPROVED, REJECTED

    public LeaveRequest(int leaveId, int studentId, Date fromDate, Date toDate, String reason, String status) {
        this.leaveId = leaveId;
        this.studentId = studentId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
    }

    public int getLeaveId() { return leaveId; }
    public int getStudentId() { return studentId; }
    public Date getFromDate() { return fromDate; }
    public Date getToDate() { return toDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getDaysRequested() {
        long diff = toDate.getTime() - fromDate.getTime();
        return (diff / (1000 * 60 * 60 * 24)) + 1;
    }

    @Override
    public String toString() {
        return String.format("LeaveID: %d | StudentID: %d | %s to %s | Days: %d | Reason: %s | Status: %s",
                leaveId, studentId, fromDate, toDate, getDaysRequested(), reason, status);
    }
}
