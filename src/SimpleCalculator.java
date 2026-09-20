import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        CGPACalculator calculator = new CGPACalculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== CGPA CALCULATOR ===\n");

        System.out.println("SEMESTER I - Enter grades for 10 subjects:\n");
        java.util.List<Subject> sem1 = calculator.getSemesterI();
        for (int i = 0; i < sem1.size(); i++) {
            Subject s = sem1.get(i);
            System.out.print((i + 1) + ". " + s.getSubjectName() + " - Enter grade (S/A+/A/B+/B/C+/C/U): ");
            String grade = scanner.nextLine().trim().toUpperCase();
            calculator.updateGrade(1, i, grade);
        }

        System.out.println("\nSEMESTER II - Enter grades for 11 subjects:\n");
        java.util.List<Subject> sem2 = calculator.getSemesterII();
        for (int i = 0; i < sem2.size(); i++) {
            Subject s = sem2.get(i);
            System.out.print((i + 1) + ". " + s.getSubjectName() + " - Enter grade (S/A+/A/B+/B/C+/C/U): ");
            String grade = scanner.nextLine().trim().toUpperCase();
            calculator.updateGrade(2, i, grade);
        }

        System.out.println("\n=== YOUR RESULTS ===\n");
        System.out.println("Semester I GPA: " + String.format("%.2f", calculator.calculateSemesterGPA(1)));
        System.out.println("Semester II GPA: " + String.format("%.2f", calculator.calculateSemesterGPA(2)));
        System.out.println("YOUR CGPA: " + String.format("%.2f", calculator.calculateCGPA()));
        System.out.println("\n=== END ===\n");

        scanner.close();
    }
}
