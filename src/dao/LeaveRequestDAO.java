package dao;

import model.LeaveRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {

    public boolean applyLeave(LeaveRequest lr) {
        String sql = "INSERT INTO leave_requests (student_id, from_date, to_date, reason, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, lr.getStudentId());
            ps.setDate(2, lr.getFromDate());
            ps.setDate(3, lr.getToDate());
            ps.setString(4, lr.getReason());
            ps.setString(5, lr.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error applying leave: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStatus(int leaveId, String status) {
        String sql = "UPDATE leave_requests SET status = ? WHERE leave_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
            return false;
        }
    }

    public List<LeaveRequest> getAllRequests() {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests ORDER BY leave_id DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching requests: " + e.getMessage());
        }
        return list;
    }

    public List<LeaveRequest> getRequestsByStudent(int studentId) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE student_id = ? ORDER BY leave_id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching student requests: " + e.getMessage());
        }
        return list;
    }

    public List<LeaveRequest> getPendingRequests() {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE status = 'PENDING' ORDER BY leave_id";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching pending requests: " + e.getMessage());
        }
        return list;
    }

    private LeaveRequest mapRow(ResultSet rs) throws SQLException {
        return new LeaveRequest(
                rs.getInt("leave_id"),
                rs.getInt("student_id"),
                rs.getDate("from_date"),
                rs.getDate("to_date"),
                rs.getString("reason"),
                rs.getString("status")
        );
    }
}
