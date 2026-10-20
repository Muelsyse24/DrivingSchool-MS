package abcd;

/** 毕业信息实体 */
public class Graduation {
    private int id;
    private String studentName;
    private String licenseType;
    private String graduationDate;

    public Graduation() {
    }

    public Graduation(String studentName, String licenseType, String graduationDate) {
        this.studentName = studentName;
        this.licenseType = licenseType;
        this.graduationDate = graduationDate;
    }

    public Graduation(int id, String studentName, String licenseType, String graduationDate) {
        this.id = id;
        this.studentName = studentName;
        this.licenseType = licenseType;
        this.graduationDate = graduationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    @Override
    public String toString() {
        return studentName == null ? "" : studentName;
    }
}
