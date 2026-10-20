package abcd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** 毕业信息表的数据访问对象 */
public class GraduationDao extends DaoSupport {

    public List<Graduation> loadAll() {
        List<Graduation> list = new ArrayList<>();
        String sql = "SELECT id, student_name, license_type, graduation_date FROM graduation ORDER BY id";
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Graduation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("读取毕业信息失败: " + e.getMessage(), e);
        }
        return list;
    }

    public void replaceAll(List<Graduation> list) {
        Connection c = Db.get();
        try {
            begin(c);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE FROM graduation");
            }
            String sql = "INSERT INTO graduation(student_name, license_type, graduation_date) VALUES (?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (Graduation x : list) {
                    ps.setString(1, x.getStudentName());
                    ps.setString(2, x.getLicenseType());
                    ps.setString(3, x.getGraduationDate());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            commit(c);
        } catch (SQLException e) {
            rollback(c);
            throw new IllegalStateException("保存毕业信息失败: " + e.getMessage(), e);
        }
    }
}
