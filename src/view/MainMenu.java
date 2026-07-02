package view;

import controller.LeaveController;
import model.LeaveRequest;
import model.Student;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final LeaveController controller = new LeaveController();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("      LEAVESYNC - Leave Management       ");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": registerStudent(); break;
                case "2": listStudents(); break;
                case "3": applyLeave(); break;
                case "4": viewPendingRequests(); break;
                case "5": reviewLeave(); break;
                case "6": viewStudentHistory(); break;
                case "7": viewAllRequests(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option, try again.");
            }
        }
        System.out.println("Exiting LeaveSync. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Register Student");
        System.out.println("2. List All Students");
        System.out.println("3. Apply for Leave");
        System.out.println("4. View Pending Requests (Admin)");
        System.out.println("5. Approve/Reject Leave (Admin)");
        System.out.println("6. View Student Leave History");
        System.out.println("7. View All Leave Requests");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void registerStudent() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Roll No: ");
        String rollNo = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        int balance = readInt("Initial Leave Balance (days): ");

        boolean success = controller.registerStudent(name, rollNo, email, balance);
        System.out.println(success ? "Student registered successfully." : "Registration failed.");
    }

    private static void listStudents() {
        List<Student> students = controller.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(System.out::println);
    }

    private static void applyLeave() {
        int studentId = readInt("Student ID: ");
        Date from = readDate("From Date (YYYY-MM-DD): ");
        Date to = readDate("To Date (YYYY-MM-DD): ");
        System.out.print("Reason: ");
        String reason = sc.nextLine();

        String result = controller.applyForLeave(studentId, from, to, reason);
        System.out.println(result);
    }

    private static void viewPendingRequests() {
        List<LeaveRequest> pending = controller.viewPendingRequests();
        if (pending.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        pending.forEach(System.out::println);
    }

    private static void reviewLeave() {
        int leaveId = readInt("Leave ID to review: ");
        System.out.print("Approve? (y/n): ");
        boolean approve = sc.nextLine().trim().equalsIgnoreCase("y");
        System.out.println(controller.reviewLeave(leaveId, approve));
    }

    private static void viewStudentHistory() {
        int studentId = readInt("Student ID: ");
        List<LeaveRequest> history = controller.viewStudentHistory(studentId);
        if (history.isEmpty()) {
            System.out.println("No leave history found.");
            return;
        }
        history.forEach(System.out::println);
    }

    private static void viewAllRequests() {
        List<LeaveRequest> all = controller.viewAllRequests();
        if (all.isEmpty()) {
            System.out.println("No leave requests yet.");
            return;
        }
        all.forEach(System.out::println);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    private static Date readDate(String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return Date.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid format, use YYYY-MM-DD: ");
            }
        }
    }
}
