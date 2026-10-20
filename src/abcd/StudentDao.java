package abcd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** 学员表的数据访问对象 */
public class StudentDao extends DaoSupport {

    public List<Student> loadAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT student_number, name, id_card, id_card_image, mobile, address,"
                + " application_type, enrollment_time, coach_name FROM student ORDER BY id";
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                s.setIdCardImage(rs.getString(4));
                list.add(s);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("读取学员列表失败: " + e.getMessage(), e);
        }
        return list;
    }

    public void replaceAll(List<Student> list) {
        Connection c = Db.get();
        try {
            begin(c);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE FROM student");
            }
            String sql = "INSERT INTO student(student_number, name, id_card, id_card_image, mobile,"
                    + " address, application_type, enrollment_time, coach_name) VALUES (?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (Student x : list) {
                    ps.setString(1, x.getStudentNumber());
                    ps.setString(2, x.getName());
                    ps.setString(3, x.getIdCard());
                    ps.setString(4, x.getIdCardImage());
                    ps.setString(5, x.getMobile());
                    ps.setString(6, x.getAddress());
                    ps.setString(7, x.getApplicationType());
                    ps.setString(8, x.getEnrollmentTime());
                    ps.setString(9, x.getCoachName());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            commit(c);
        } catch (SQLException e) {
            rollback(c);
            throw new IllegalStateException("保存学员列表失败: " + e.getMessage(), e);
        }
    }
}
