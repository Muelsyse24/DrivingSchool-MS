package abcd;

/** 训练记录实体：某个学员某个科目练了多少学时、哪一天练的 */
public class Training {
    private int id;
    private String studentName;
    private String subjectType;
    private int studyHours;
    private String practiceDate;

    public Training() {
    }

    public Training(String studentName, String subjectType, int studyHours, String practiceDate) {
        this.studentName = studentName;
        this.subjectType = subjectType;
        this.studyHours = studyHours;
        this.practiceDate = practiceDate;
    }

    public Training(int id, String studentName, String subjectType, int studyHours, String practiceDate) {
        this.id = id;
        this.studentName = studentName;
        this.subjectType = subjectType;
        this.studyHours = studyHours;
        this.practiceDate = practiceDate;
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

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public int getStudyHours() {
        return studyHours;
    }

    public void setStudyHours(int studyHours) {
        this.studyHours = studyHours;
    }

    public String getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) {
        this.practiceDate = practiceDate;
    }

    @Override
    public String toString() {
        return studentName == null ? "" : studentName;
    }
}
