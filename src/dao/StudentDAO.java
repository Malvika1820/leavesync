package dao;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students (name, roll_no, email, leave_balance) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRollNo());
            ps.setString(3, s.getEmail());
            ps.setInt(4, s.getLeaveBalance());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("email"),
                        rs.getInt("leave_balance")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("email"),
                        rs.getInt("leave_balance")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }
        return students;
    }

    public boolean updateLeaveBalance(int studentId, int newBalance) {
        String sql = "UPDATE students SET leave_balance = ? WHERE student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newBalance);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating leave balance: " + e.getMessage());
            return false;
        }
    }
}
