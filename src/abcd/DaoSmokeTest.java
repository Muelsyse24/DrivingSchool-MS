package abcd;

import java.io.File;
import java.util.List;

/**
 * 数据层的无界面自测：不打开任何窗口，直接验证 6 张表的增、删、改、查，
 * 以及“关掉再打开”之后数据还在不在。
 *
 * 用法：java -cp "out;lib/*" abcd.DaoSmokeTest
 */
public class DaoSmokeTest {

    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        // 从干净库开始，避免旧数据干扰断言
        deleteDatabase();

        CoachDao coachDao = new CoachDao();
        StudentDao studentDao = new StudentDao();
        FeeDao feeDao = new FeeDao();
        TrainingDao trainingDao = new TrainingDao();
        SubjectDao subjectDao = new SubjectDao();
        GraduationDao graduationDao = new GraduationDao();

        // ---- 增
        List<Coach> coaches = coachDao.loadAll();
        coaches.add(new Coach("张三", "13800000001", "110101199001010011", "粤A12345", "大众"));
        coaches.add(new Coach("李四", "13800000002", "110101199001010022", "粤B67890", "丰田"));
        coachDao.replaceAll(coaches);
        check("教练 新增 2 条后读回 2 条", coachDao.loadAll().size() == 2);
        check("教练 姓名落库正确", "李四".equals(coachDao.loadAll().get(1).getName()));

        List<Student> students = studentDao.loadAll();
        Student s1 = new Student("XY000001", "王五", "110101199001010033", "13500000001",
                "北京市海淀区", "C1", "2026-01-10", "张三");
        s1.setIdCardImage("wangwu.jpg");
        students.add(s1);
        studentDao.replaceAll(students);
        List<Student> reloadedStudents = studentDao.loadAll();
        check("学员 新增后读回 1 条", reloadedStudents.size() == 1);
        check("学员 身份证图片字段保留", "wangwu.jpg".equals(reloadedStudents.get(0).getIdCardImage()));
        check("学员 所属教练字段保留", "张三".equals(reloadedStudents.get(0).getCoachName()));

        List<Fee> fees = feeDao.loadAll();
        fees.add(new Fee("SJ1000001", "2026-01-10", "XY000001", "王五", "男",
                "110101199001010033", "13500000001", "C1", "学费", 5000.0, 200.0, 4800.0, "业务员1", "操作人1"));
        feeDao.replaceAll(fees);
        check("费用 金额字段精确", Math.abs(feeDao.loadAll().get(0).getPaidAmount() - 4800.0) < 1e-6);

        List<Training> trainings = trainingDao.loadAll();
        trainings.add(new Training("王五", "场地训练", 12, "2026-01-12"));
        trainingDao.replaceAll(trainings);
        check("训练 学时字段为整数", trainingDao.loadAll().get(0).getStudyHours() == 12);

        List<Subject> subjects = subjectDao.loadAll();
        subjects.add(new Subject("K1", "科目一"));
        subjects.add(new Subject("K2", "科目二"));
        subjectDao.replaceAll(subjects);
        check("科目 新增 2 条", subjectDao.loadAll().size() == 2);

        List<Graduation> graduations = graduationDao.loadAll();
        graduations.add(new Graduation("王五", "C1", "2026-03-01"));
        graduationDao.replaceAll(graduations);
        check("毕业信息 新增 1 条", graduationDao.loadAll().size() == 1);

        // ---- 改
        List<Coach> editCoaches = coachDao.loadAll();
        editCoaches.get(0).setVehicleModel("本田");
        coachDao.replaceAll(editCoaches);
        check("教练 修改落库", "本田".equals(coachDao.loadAll().get(0).getVehicleModel()));

        // ---- 删
        List<Coach> deleteCoaches = coachDao.loadAll();
        deleteCoaches.remove(0);
        coachDao.replaceAll(deleteCoaches);
        check("教练 删除后剩 1 条", coachDao.loadAll().size() == 1);

        // ---- 重开连接：模拟程序关闭再启动
        Db.close();
        check("重开连接后教练数据仍在", coachDao.loadAll().size() == 1);
        check("重开连接后学员数据仍在", studentDao.loadAll().size() == 1);
        check("重开连接后科目数据仍在", subjectDao.loadAll().size() == 2);

        // ---- 数据库文件确实存在
        check("data/school.db 已生成", new File("data/school.db").isFile());

        Db.close();
        System.out.println();
        System.out.println("通过 " + passed + " 项，失败 " + failed + " 项");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void check(String name, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("  OK   " + name);
        } else {
            failed++;
            System.out.println("  FAIL " + name);
        }
    }

    private static void deleteDatabase() {
        Db.close();
        File db = new File("data/school.db");
        if (db.exists() && !db.delete()) {
            throw new IllegalStateException("无法删除旧数据库: " + db.getAbsolutePath());
        }
    }
}
