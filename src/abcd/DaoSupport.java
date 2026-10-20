package abcd;

import java.sql.Connection;
import java.sql.SQLException;

/** 各 DAO 共用的“批量覆盖写入”事务骨架 */
abstract class DaoSupport {

    protected static void begin(Connection c) throws SQLException {
        c.setAutoCommit(false);
    }

    protected static void commit(Connection c) throws SQLException {
        c.commit();
        c.setAutoCommit(true);
    }

    protected static void rollback(Connection c) {
        try {
            c.rollback();
        } catch (SQLException ignored) {
            // 回滚失败不掩盖原始异常
        }
        try {
            c.setAutoCommit(true);
        } catch (SQLException ignored) {
            // 恢复失败将在下一次操作暴露
        }
    }
}
