package abcd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** 训练记录表的数据访问对象 */
public class TrainingDao extends DaoSupport {

    public List<Training> loadAll() {
        List<Training> list = new ArrayList<>();
        String sql = "SELECT id, student_name, subject_type, study_hours, practice_date FROM training ORDER BY id";
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Training(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("读取训练记录失败: " + e.getMessage(), e);
        }
        return list;
    }

    public void replaceAll(List<Training> list) {
        Connection c = Db.get();
        try {
            begin(c);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE FROM training");
            }
            String sql = "INSERT INTO training(student_name, subject_type, study_hours, practice_date)"
                    + " VALUES (?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (Training x : list) {
                    ps.setString(1, x.getStudentName());
                    ps.setString(2, x.getSubjectType());
                    ps.setInt(3, x.getStudyHours());
                    ps.setString(4, x.getPracticeDate());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            commit(c);
        } catch (SQLException e) {
            rollback(c);
            throw new IllegalStateException("保存训练记录失败: " + e.getMessage(), e);
        }
    }
}
