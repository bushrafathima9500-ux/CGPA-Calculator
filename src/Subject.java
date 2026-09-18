/**
 * Subject Class - Represents a subject with credits and grade
 * Anna University 2025 Regulation
 */
public class Subject {
    private String subjectCode;
    private String subjectName;
    private int credits;
    private String grade;  // User selects from dropdown
    private double gradePoint;

    public Subject(String subjectCode, String subjectName, int credits, String grade) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.grade = grade != null ? grade : "S";  // Default to S
        this.gradePoint = getGradePoint(this.grade);
    }

    /**
     * Get grade point for the selected grade
     * Anna University 2025 System
     */
    public static double getGradePointValue(String grade) {
        if (grade == null) return 10.0;
        
        switch (grade.trim().toUpperCase()) {
            case "S":   return 10.0;  // Superior
            case "A+":  return 9.0;   // Excellent
            case "A":   return 8.0;   // Very Good
            case "B+":  return 7.0;   // Good
            case "B":   return 6.5;   // Satisfactory
            case "C+":  return 6.0;   // Adequate
            case "C":   return 5.0;   // Pass
            case "U":   return 0.0;   // Fail
            default:    return 10.0;
        }
    }

    private double getGradePoint(String grade) {
        return getGradePointValue(grade);
    }

    // Getters and Setters
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public int getCredits() { return credits; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) {
        this.grade = grade != null ? grade : "S";
        this.gradePoint = getGradePoint(this.grade);
    }
    
    public double getGradePoint() { return gradePoint; }
    public void updateGradePoint() { this.gradePoint = getGradePoint(this.grade); }

    /**
     * Quality Points = Credits × Grade Point
     */
    public double getQualityPoints() {
        return credits * gradePoint;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | Credits: %d | Grade: %s | GP: %.1f | QP: %.1f",
                subjectCode, subjectName, credits, grade, gradePoint, getQualityPoints());
    }
}