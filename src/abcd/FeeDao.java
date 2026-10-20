package abcd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** 费用表的数据访问对象 */
public class FeeDao extends DaoSupport {

    public List<Fee> loadAll() {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT fee_number, fee_date, student_number, name, gender, id_card, mobile,"
                + " license_type, fee_type, package_amount, discount_amount, paid_amount,"
                + " salesperson, operator FROM fee ORDER BY id";
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Fee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getDouble(10), rs.getDouble(11), rs.getDouble(12),
                        rs.getString(13), rs.getString(14)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("读取费用列表失败: " + e.getMessage(), e);
        }
        return list;
    }

    public void replaceAll(List<Fee> list) {
        Connection c = Db.get();
        try {
            begin(c);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE FROM fee");
            }
            String sql = "INSERT INTO fee(fee_number, fee_date, student_number, name, gender, id_card,"
                    + " mobile, license_type, fee_type, package_amount, discount_amount, paid_amount,"
                    + " salesperson, operator) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (Fee x : list) {
                    ps.setString(1, x.getFeeNumber());
                    ps.setString(2, x.getFeeDate());
                    ps.setString(3, x.getStudentNumber());
                    ps.setString(4, x.getName());
                    ps.setString(5, x.getGender());
                    ps.setString(6, x.getIdCard());
                    ps.setString(7, x.getMobile());
                    ps.setString(8, x.getLicenseType());
                    ps.setString(9, x.getFeeType());
                    ps.setDouble(10, x.getPackageAmount());
                    ps.setDouble(11, x.getDiscountAmount());
                    ps.setDouble(12, x.getPaidAmount());
                    ps.setString(13, x.getSalesperson());
                    ps.setString(14, x.getOperator());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            commit(c);
        } catch (SQLException e) {
            rollback(c);
            throw new IllegalStateException("保存费用列表失败: " + e.getMessage(), e);
        }
    }
}
