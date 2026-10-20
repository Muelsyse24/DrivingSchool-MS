package abcd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** 科目设置表的数据访问对象 */
public class SubjectDao extends DaoSupport {

    public List<Subject> loadAll() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT subject_number, subject_name FROM subject ORDER BY id";
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Subject(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("读取科目列表失败: " + e.getMessage(), e);
        }
        return list;
    }

    public void replaceAll(List<Subject> list) {
        Connection c = Db.get();
        try {
            begin(c);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE FROM subject");
            }
            String sql = "INSERT INTO subject(subject_number, subject_name) VALUES (?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (Subject x : list) {
                    ps.setString(1, x.getSubjectNumber());
                    ps.setString(2, x.getSubjectName());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            commit(c);
        } catch (SQLException e) {
            rollback(c);
            throw new IllegalStateException("保存科目列表失败: " + e.getMessage(), e);
        }
    }
}
