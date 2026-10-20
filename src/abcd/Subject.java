package abcd;

/** 科目设置实体：科目编号 + 科目名称 */
public class Subject {
    private String subjectNumber;
    private String subjectName;

    public Subject() {
    }

    public Subject(String subjectNumber, String subjectName) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
    }

    public String getSubjectNumber() {
        return subjectNumber;
    }

    public void setSubjectNumber(String subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return subjectName == null ? "" : subjectName;
    }
}
