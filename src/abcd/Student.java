package abcd;

/** 学员实体 */
public class Student {
    private String studentNumber;
    private String name;
    private String idCard;
    private String idCardImage;
    private String mobile;
    private String address;
    private String applicationType;
    private String enrollmentTime;
    private String coachName;

    public Student() {
    }

    public Student(String studentNumber, String name, String idCard, String mobile,
            String address, String applicationType, String enrollmentTime, String coachName) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.idCard = idCard;
        this.mobile = mobile;
        this.address = address;
        this.applicationType = applicationType;
        this.enrollmentTime = enrollmentTime;
        this.coachName = coachName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardImage() {
        return idCardImage;
    }

    public void setIdCardImage(String idCardImage) {
        this.idCardImage = idCardImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(String enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    @Override
    public String toString() {
        return name == null ? "" : name;
    }
}
