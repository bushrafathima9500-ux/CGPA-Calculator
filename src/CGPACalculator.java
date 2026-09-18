import java.util.ArrayList;
import java.util.List;

/**
 * CGPA Calculator - Manages subjects and CGPA calculations
 * Anna University 2025 Regulation
 */
public class CGPACalculator {
    private List<Subject> semesterI;
    private List<Subject> semesterII;

    public CGPACalculator() {
        this.semesterI = new ArrayList<>();
        this.semesterII = new ArrayList<>();
        initializeSubjects();
    }

    /**
     * Initialize all subjects from your Excel template
     */
    private void initializeSubjects() {
        // SEMESTER I - 10 Subjects
        semesterI.add(new Subject("MA25C01", "Applied Calculus", 4, "S"));
        semesterI.add(new Subject("EN25C01", "English Essentials – I", 2, "S"));
        semesterI.add(new Subject("UC25H01", "Heritage of Tamils", 1, "S"));
        semesterI.add(new Subject("PH25C01", "Applied Physics – I", 3, "S"));
        semesterI.add(new Subject("CY25C01", "Applied Chemistry – I", 3, "S"));
        semesterI.add(new Subject("CS25C01", "Computer Programming: C", 3, "S"));
        semesterI.add(new Subject("CS25C03", "Essentials of Computing", 3, "S"));
        semesterI.add(new Subject("ME25C04", "Makerspace", 2, "S"));
        semesterI.add(new Subject("UC25A01", "Life Skills for Engineers – I", 1, "S"));
        semesterI.add(new Subject("UC25A02", "Physical Education – I", 1, "S"));

        // SEMESTER II - 11 Subjects
        semesterII.add(new Subject("MA25C02", "Linear Algebra", 4, "S"));
        semesterII.add(new Subject("EE25C01", "Basic Electrical and Electronics Engineering", 3, "S"));
        semesterII.add(new Subject("CS25C06", "Digital Principles and Computer Organization", 4, "S"));
        semesterII.add(new Subject("UC25H02", "Tamils and Technology", 1, "S"));
        semesterII.add(new Subject("PH25C03", "Applied Physics (CSIE) – II", 3, "S"));
        semesterII.add(new Subject("CS25C07", "Object Oriented Programming", 5, "S"));
        semesterII.add(new Subject("EN25C02", "English Essentials – II", 2, "S"));
        semesterII.add(new Subject("ME25C05", "Re-Engineering for Innovation", 2, "S"));
        semesterII.add(new Subject("UC25A03", "Life Skills for Engineers – II", 1, "S"));
        semesterII.add(new Subject("UC25A04", "Physical Education – II", 1, "S"));
        semesterII.add(new Subject("UC25F01/F02", "Foreign Language", 1, "S"));
    }

    // Getters
    public List<Subject> getSemesterI() { return semesterI; }
    public List<Subject> getSemesterII() { return semesterII; }
    public List<Subject> getAllSubjects() {
        List<Subject> all = new ArrayList<>();
        all.addAll(semesterI);
        all.addAll(semesterII);
        return all;
    }

    /**
     * Update grade for a specific subject in a semester
     */
    public void updateGrade(int semester, int index, String newGrade) {
        List<Subject> subjects = (semester == 1) ? semesterI : semesterII;
        if (index >= 0 && index < subjects.size()) {
            subjects.get(index).setGrade(newGrade);
        }
    }

    /**
     * Calculate GPA for a specific semester
     */
    public double calculateSemesterGPA(int semester) {
        List<Subject> subjects = (semester == 1) ? semesterI : semesterII;
        
        if (subjects.isEmpty()) {
            return 0.0;
        }

        double totalQualityPoints = 0;
        int totalCredits = 0;

        for (Subject subject : subjects) {
            if (!subject.getGrade().equals("U")) {  // Exclude failed subjects
                totalQualityPoints += subject.getQualityPoints();
                totalCredits += subject.getCredits();
            }
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        double gpa = totalQualityPoints / totalCredits;
        return Math.round(gpa * 100.0) / 100.0;  // Round to 2 decimal places
    }

    /**
     * Calculate overall CGPA (both semesters)
     */
    public double calculateCGPA() {
        double totalQualityPoints = 0;
        int totalCredits = 0;

        // Process Semester I
        for (Subject subject : semesterI) {
            if (!subject.getGrade().equals("U")) {
                totalQualityPoints += subject.getQualityPoints();
                totalCredits += subject.getCredits();
            }
        }

        // Process Semester II
        for (Subject subject : semesterII) {
            if (!subject.getGrade().equals("U")) {
                totalQualityPoints += subject.getQualityPoints();
                totalCredits += subject.getCredits();
            }
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        double cgpa = totalQualityPoints / totalCredits;
        return Math.round(cgpa * 100.0) / 100.0;  // Round to 2 decimal places
    }

    /**
     * Get total credits for a semester
     */
    public int getTotalCredits(int semester) {
        List<Subject> subjects = (semester == 1) ? semesterI : semesterII;
        int total = 0;
        for (Subject subject : subjects) {
            total += subject.getCredits();
        }
        return total;
    }

    /**
     * Get total quality points for a semester
     */
    public double getTotalQualityPoints(int semester) {
        List<Subject> subjects = (semester == 1) ? semesterI : semesterII;
        double total = 0;
        for (Subject subject : subjects) {
            if (!subject.getGrade().equals("U")) {
                total += subject.getQualityPoints();
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Print detailed report
     */
    public void printReport() {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("CGPA CALCULATOR - ANNA UNIVERSITY 2025 REGULATION");
        System.out.println("=".repeat(100));

        // Semester I
        System.out.println("\n📚 SEMESTER I");
        System.out.println("-".repeat(100));
        int i = 1;
        for (Subject subject : semesterI) {
            System.out.printf("[%2d] %s%n", i, subject);
            i++;
        }
        System.out.printf("Total Credits: %d | Total QP: %.2f | GPA: %.2f%n%n",
                getTotalCredits(1), getTotalQualityPoints(1), calculateSemesterGPA(1));

        // Semester II
        System.out.println("📚 SEMESTER II");
        System.out.println("-".repeat(100));
        i = 1;
        for (Subject subject : semesterII) {
            System.out.printf("[%2d] %s%n", i, subject);
            i++;
        }
        System.out.printf("Total Credits: %d | Total QP: %.2f | GPA: %.2f%n%n",
                getTotalCredits(2), getTotalQualityPoints(2), calculateSemesterGPA(2));

        // Overall CGPA
        System.out.println("=".repeat(100));
        System.out.printf("🎯 YOUR OVERALL CGPA: %.2f%n", calculateCGPA());
        System.out.println("=".repeat(100) + "\n");
    }
}