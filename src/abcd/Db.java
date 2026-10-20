package abcd;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLite 连接与建表。
 * 数据库文件固定在程序工作目录下的 data/school.db，程序关闭前调用 close() 释放连接。
 */
public final class Db {

    private static final String URL = "jdbc:sqlite:data/school.db";
    private static Connection connection;

    private Db() {
    }

    public static synchronized Connection get() {
        if (connection == null) {
            try {
                File dir = new File("data");
                if (!dir.exists() && !dir.mkdirs()) {
                    throw new IllegalStateException("无法创建数据目录: " + dir.getAbsolutePath());
                }
                connection = DriverManager.getConnection(URL);
                createTables(connection);
            } catch (SQLException e) {
                throw new IllegalStateException("无法打开数据库 " + URL + ": " + e.getMessage(), e);
            }
        }
        return connection;
    }

    private static void createTables(Connection c) throws SQLException {
        try (Statement st = c.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS coach ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "mobile TEXT,"
                    + "id_card TEXT,"
                    + "license_plate TEXT,"
                    + "vehicle_model TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS student ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "student_number TEXT,"
                    + "name TEXT NOT NULL,"
                    + "id_card TEXT,"
                    + "id_card_image TEXT,"
                    + "mobile TEXT,"
                    + "address TEXT,"
                    + "application_type TEXT,"
                    + "enrollment_time TEXT,"
                    + "coach_name TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS fee ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "fee_number TEXT,"
                    + "fee_date TEXT,"
                    + "student_number TEXT,"
                    + "name TEXT,"
                    + "gender TEXT,"
                    + "id_card TEXT,"
                    + "mobile TEXT,"
                    + "license_type TEXT,"
                    + "fee_type TEXT,"
                    + "package_amount REAL,"
                    + "discount_amount REAL,"
                    + "paid_amount REAL,"
                    + "salesperson TEXT,"
                    + "operator TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS training ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "student_name TEXT,"
                    + "subject_type TEXT,"
                    + "study_hours INTEGER,"
                    + "practice_date TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS subject ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "subject_number TEXT,"
                    + "subject_name TEXT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS graduation ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "student_name TEXT,"
                    + "license_type TEXT,"
                    + "graduation_date TEXT)");
        }
    }

    public static synchronized void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                // 关闭失败不影响退出
            } finally {
                connection = null;
            }
        }
    }
}
