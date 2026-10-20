package abcd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** 教练表的数据访问对象 */
public class CoachDao extends DaoSupport {

    public List<Coach> loadAll() {
        List<Coach> list = new ArrayList<>();
        String sql = "SELECT name, mobile, id_card, license_plate, vehicle_model FROM coach ORDER BY id";
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Coach(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("读取教练列表失败: " + e.getMessage(), e);
        }
        return list;
    }

    /** 用内存列表整体覆盖数据库（数据量小，全量写最不容易出错） */
    public void replaceAll(List<Coach> list) {
        Connection c = Db.get();
        try {
            begin(c);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE FROM coach");
            }
            String sql = "INSERT INTO coach(name, mobile, id_card, license_plate, vehicle_model) VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (Coach x : list) {
                    ps.setString(1, x.getName());
                    ps.setString(2, x.getMobile());
                    ps.setString(3, x.getIdCard());
                    ps.setString(4, x.getLicensePlate());
                    ps.setString(5, x.getVehicleModel());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            commit(c);
        } catch (SQLException e) {
            rollback(c);
            throw new IllegalStateException("保存教练列表失败: " + e.getMessage(), e);
        }
    }
}
