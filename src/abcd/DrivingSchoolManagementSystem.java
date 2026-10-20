package abcd;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * 驾校管理系统的入口与全部界面。
 *
 * 分工：本类只管界面与交互；数据读写交给 6 个 DAO，DAO 再落到 SQLite。
 * 内存里的 List 是界面显示的唯一数据源，每次增删改之后调 replaceAll 整体写回，
 * 这样界面和数据永远不会各说各话。
 */
public class DrivingSchoolManagementSystem {

    private JFrame mainFrame;

    private final List<Coach> coaches = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Fee> fees = new ArrayList<>();
    private final List<Training> trainings = new ArrayList<>();
    private final List<Subject> subjects = new ArrayList<>();
    private final List<Graduation> graduations = new ArrayList<>();

    private final CoachDao coachDao = new CoachDao();
    private final StudentDao studentDao = new StudentDao();
    private final FeeDao feeDao = new FeeDao();
    private final TrainingDao trainingDao = new TrainingDao();
    private final SubjectDao subjectDao = new SubjectDao();
    private final GraduationDao graduationDao = new GraduationDao();

    public static void main(String[] args) {
        SwingHelper.run(() -> new DrivingSchoolManagementSystem());
    }

    public DrivingSchoolManagementSystem() {
        loadFromDatabase();
        showLoginFrame();
    }

    /** 启动时一次性把 6 张表读进内存 */
    private void loadFromDatabase() {
        try {
            coaches.addAll(coachDao.loadAll());
            students.addAll(studentDao.loadAll());
            fees.addAll(feeDao.loadAll());
            trainings.addAll(trainingDao.loadAll());
            subjects.addAll(subjectDao.loadAll());
            graduations.addAll(graduationDao.loadAll());
        } catch (RuntimeException e) {
            // 打包成 exe 后没有控制台，把完整堆栈落到程序目录，方便排查
            java.io.StringWriter buffer = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(buffer));
            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of("startup-error.log"), buffer.toString());
            } catch (java.io.IOException ignored) {
                // 日志写不出来也不能挡住错误提示
            }
            JOptionPane.showMessageDialog(null, "读取数据库失败：" + e.getMessage(),
                    "启动失败", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    // ---------------------------------------------------------------- 登录

    private void showLoginFrame() {
        JFrame loginFrame = new JFrame("驾校管理系统 - 登录");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(360, 220);
        loginFrame.setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("用户名："));
        JTextField userField = new JTextField("admin");
        form.add(userField);
        form.add(new JLabel("密码："));
        JPasswordField passField = new JPasswordField();
        form.add(passField);

        JButton loginButton = new JButton("登录");
        JButton exitButton = new JButton("退出");
        JPanel buttons = new JPanel();
        buttons.add(loginButton);
        buttons.add(exitButton);

        loginFrame.add(form, BorderLayout.CENTER);
        loginFrame.add(buttons, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            // 实训版本：账号密码写死，只区分“能进/不能进”
            if ("admin".equals(username) && "123456".equals(password)) {
                loginFrame.dispose();
                showMainFrame();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "用户名或密码错误", "登录失败",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        exitButton.addActionListener(e -> System.exit(0));

        loginFrame.setVisible(true);
    }

    // ---------------------------------------------------------------- 主界面

    private void showMainFrame() {
        mainFrame = new JFrame("驾校管理系统");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(560, 420);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 12, 12));

        JButton coachButton = new JButton("教练管理");
        JButton studentButton = new JButton("学员管理");
        JButton feeButton = new JButton("费用管理");
        JButton trainingButton = new JButton("训练管理");
        JButton subjectButton = new JButton("科目设置");
        JButton graduationButton = new JButton("毕业信息");

        panel.add(coachButton);
        panel.add(studentButton);
        panel.add(feeButton);
        panel.add(trainingButton);
        panel.add(subjectButton);
        panel.add(graduationButton);

        coachButton.addActionListener(e -> showCoachManagementFrame());
        studentButton.addActionListener(e -> showStudentManagementFrame());
        feeButton.addActionListener(e -> showStudentFeeManagementFrame());
        trainingButton.addActionListener(e -> showStudentTrainingManagementFrame());
        subjectButton.addActionListener(e -> showStudentSubjectSettingFrame());
        graduationButton.addActionListener(e -> showGraduationInfoFrame());

        mainFrame.add(panel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton exitButton = new JButton("退出系统");
        exitButton.addActionListener(e -> System.exit(0));
        bottom.add(exitButton);
        mainFrame.add(bottom, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    // ---------------------------------------------------------------- 教练管理

    private void showCoachManagementFrame() {
        JFrame frame = new JFrame("教练管理系统");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = { "教练姓名", "手机号码", "身份证号", "教练车牌", "车辆型号" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshCoachTable(model);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String name = ask(frame, "输入教练姓名");
            if (isBlank(name)) {
                return;
            }
            String phone = ask(frame, "输入手机号码");
            String idCard = ask(frame, "输入身份证号");
            String licensePlate = ask(frame, "输入教练车牌");
            String vehicleModel = ask(frame, "输入车辆型号");
            coaches.add(new Coach(name, phone, idCard, licensePlate, vehicleModel));
            persistCoaches(model);
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            coaches.remove(row);
            persistCoaches(model);
        });

        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            Coach coach = coaches.get(row);
            coach.setName(askOrKeep(frame, "输入新教练姓名", coach.getName()));
            coach.setMobile(askOrKeep(frame, "输入新手机号码", coach.getMobile()));
            coach.setIdCard(askOrKeep(frame, "输入新身份证号", coach.getIdCard()));
            coach.setLicensePlate(askOrKeep(frame, "输入新教练车牌", coach.getLicensePlate()));
            coach.setVehicleModel(askOrKeep(frame, "输入新车辆型号", coach.getVehicleModel()));
            persistCoaches(model);
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void refreshCoachTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Coach c : coaches) {
            model.addRow(new Object[] { c.getName(), c.getMobile(), c.getIdCard(),
                    c.getLicensePlate(), c.getVehicleModel() });
        }
    }

    private void persistCoaches(DefaultTableModel model) {
        coachDao.replaceAll(coaches);
        refreshCoachTable(model);
    }

    // ---------------------------------------------------------------- 学员管理

    private void showStudentManagementFrame() {
        JFrame frame = new JFrame("学员管理系统");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = { "学员编号", "姓名", "身份证号", "身份证图片", "手机号码", "地址",
                "申请类型", "报名时间", "所属教练" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshStudentTable(model);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String id = generateStudentId();
            String name = ask(frame, "输入学员姓名（编号自动生成：" + id + "）");
            if (isBlank(name)) {
                return;
            }
            Student s = new Student(id, name,
                    ask(frame, "输入学员身份证号"),
                    ask(frame, "输入学员手机号码"),
                    ask(frame, "输入学员地址"),
                    ask(frame, "输入学员申请类型"),
                    ask(frame, "输入学员报名时间"),
                    ask(frame, "输入学员所属教练"));
            s.setIdCardImage(ask(frame, "输入身份证图片路径"));
            students.add(s);
            persistStudents(model);
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            students.remove(row);
            persistStudents(model);
        });

        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            Student s = students.get(row);
            s.setName(askOrKeep(frame, "输入新学员姓名", s.getName()));
            s.setIdCard(askOrKeep(frame, "输入新身份证号", s.getIdCard()));
            s.setIdCardImage(askOrKeep(frame, "输入新身份证图片路径", s.getIdCardImage()));
            s.setMobile(askOrKeep(frame, "输入新手机号码", s.getMobile()));
            s.setAddress(askOrKeep(frame, "输入新地址", s.getAddress()));
            s.setApplicationType(askOrKeep(frame, "输入新申请类型", s.getApplicationType()));
            s.setEnrollmentTime(askOrKeep(frame, "输入新报名时间", s.getEnrollmentTime()));
            s.setCoachName(askOrKeep(frame, "输入新所属教练", s.getCoachName()));
            persistStudents(model);
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void refreshStudentTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Student s : students) {
            model.addRow(new Object[] { s.getStudentNumber(), s.getName(), s.getIdCard(),
                    s.getIdCardImage(), s.getMobile(), s.getAddress(), s.getApplicationType(),
                    s.getEnrollmentTime(), s.getCoachName() });
        }
    }

    private void persistStudents(DefaultTableModel model) {
        studentDao.replaceAll(students);
        refreshStudentTable(model);
    }

    /** 学员编号：XY + 6 位流水，取现有最大值加一 */
    private String generateStudentId() {
        int max = 0;
        for (Student s : students) {
            String number = s.getStudentNumber();
            if (number != null && number.startsWith("XY")) {
                try {
                    max = Math.max(max, Integer.parseInt(number.substring(2)));
                } catch (NumberFormatException ignored) {
                    // 手工填过的非标准编号直接跳过
                }
            }
        }
        return String.format("XY%06d", max + 1);
    }

    // ---------------------------------------------------------------- 费用管理

    private void showStudentFeeManagementFrame() {
        JFrame frame = new JFrame("费用管理系统");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = { "单号", "单据日期", "学员编号", "姓名", "性别", "身份证号", "手机号",
                "准驾车型", "费用类型", "套餐金额", "优惠金额", "实际缴费金额", "业务员", "操作人" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshFeeTable(model);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String id = generateFeeId();
            String date = ask(frame, "输入单据日期（单号自动生成：" + id + "）");
            if (isBlank(date)) {
                return;
            }
            String studentNumber = ask(frame, "输入学员编号");
            String name = ask(frame, "输入学员姓名");
            String gender = ask(frame, "输入学员性别");
            String idCard = ask(frame, "输入学员身份证号");
            String phone = ask(frame, "输入学员手机号码");
            String licenseType = ask(frame, "输入准驾车型");
            String type = ask(frame, "输入费用类型");
            double packageAmount = askDouble(frame, "输入套餐金额");
            double discountAmount = askDouble(frame, "输入优惠金额");
            double paidAmount = askDouble(frame, "输入实际缴费金额");
            String salesperson = ask(frame, "输入业务员");
            String operator = ask(frame, "输入操作人");
            fees.add(new Fee(id, date, studentNumber, name, gender, idCard, phone, licenseType,
                    type, packageAmount, discountAmount, paidAmount, salesperson, operator));
            persistFees(model);
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            fees.remove(row);
            persistFees(model);
        });

        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            Fee f = fees.get(row);
            f.setFeeNumber(askOrKeep(frame, "输入新的单号", f.getFeeNumber()));
            f.setFeeDate(askOrKeep(frame, "输入新的单据日期", f.getFeeDate()));
            f.setStudentNumber(askOrKeep(frame, "输入新的学员编号", f.getStudentNumber()));
            f.setName(askOrKeep(frame, "输入新的学员姓名", f.getName()));
            f.setGender(askOrKeep(frame, "输入新的性别", f.getGender()));
            f.setIdCard(askOrKeep(frame, "输入新的身份证号", f.getIdCard()));
            f.setMobile(askOrKeep(frame, "输入新的手机号码", f.getMobile()));
            f.setLicenseType(askOrKeep(frame, "输入新的准驾车型", f.getLicenseType()));
            f.setFeeType(askOrKeep(frame, "输入新的费用类型", f.getFeeType()));
            f.setPackageAmount(askDoubleOrKeep(frame, "输入新的套餐金额", f.getPackageAmount()));
            f.setDiscountAmount(askDoubleOrKeep(frame, "输入新的优惠金额", f.getDiscountAmount()));
            f.setPaidAmount(askDoubleOrKeep(frame, "输入新的实际缴费金额", f.getPaidAmount()));
            f.setSalesperson(askOrKeep(frame, "输入新的业务员", f.getSalesperson()));
            f.setOperator(askOrKeep(frame, "输入新的操作人", f.getOperator()));
            persistFees(model);
        });

        frame.setSize(1100, 500);
        frame.setVisible(true);
    }

    private void refreshFeeTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Fee f : fees) {
            model.addRow(new Object[] { f.getFeeNumber(), f.getFeeDate(), f.getStudentNumber(),
                    f.getName(), f.getGender(), f.getIdCard(), f.getMobile(), f.getLicenseType(),
                    f.getFeeType(), f.getPackageAmount(), f.getDiscountAmount(), f.getPaidAmount(),
                    f.getSalesperson(), f.getOperator() });
        }
    }

    private void persistFees(DefaultTableModel model) {
        feeDao.replaceAll(fees);
        refreshFeeTable(model);
    }

    /** 费用单号：SJ + 流水 + 000001 */
    private String generateFeeId() {
        return "SJ" + (fees.size() + 1) + "000001";
    }

    // ---------------------------------------------------------------- 训练管理

    private void showStudentTrainingManagementFrame() {
        JFrame frame = new JFrame("学员训练管理系统");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = { "学员姓名", "科目类型", "学时", "练习日期" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshTrainingTable(model);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        JButton queryButton = new JButton("查询");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);
        topPanel.add(queryButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String name = ask(frame, "输入学员姓名");
            if (isBlank(name)) {
                return;
            }
            String subjectType = ask(frame, "输入科目类型（场地训练/路考训练/文明驾驶训练）");
            if (isBlank(subjectType)) {
                return;
            }
            int hours = askInt(frame, "输入学时");
            if (hours < 0) {
                return;
            }
            String practiceDate = ask(frame, "输入练习日期");
            if (isBlank(practiceDate)) {
                return;
            }
            trainings.add(new Training(name, subjectType, hours, practiceDate));
            persistTrainings(model);
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            trainings.remove(row);
            persistTrainings(model);
        });

        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            Training t = trainings.get(row);
            t.setStudentName(askOrKeep(frame, "输入新学员姓名", t.getStudentName()));
            t.setSubjectType(askOrKeep(frame, "输入新科目类型", t.getSubjectType()));
            t.setStudyHours(askIntOrKeep(frame, "输入新学时", t.getStudyHours()));
            t.setPracticeDate(askOrKeep(frame, "输入新练习日期", t.getPracticeDate()));
            persistTrainings(model);
        });

        queryButton.addActionListener(e -> {
            String keyword = ask(frame, "按学员姓名查询（留空显示全部）");
            model.setRowCount(0);
            for (Training t : trainings) {
                if (isBlank(keyword) || (t.getStudentName() != null && t.getStudentName().contains(keyword))) {
                    model.addRow(new Object[] { t.getStudentName(), t.getSubjectType(),
                            t.getStudyHours(), t.getPracticeDate() });
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void refreshTrainingTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Training t : trainings) {
            model.addRow(new Object[] { t.getStudentName(), t.getSubjectType(),
                    t.getStudyHours(), t.getPracticeDate() });
        }
    }

    private void persistTrainings(DefaultTableModel model) {
        trainingDao.replaceAll(trainings);
        refreshTrainingTable(model);
    }

    // ---------------------------------------------------------------- 科目设置

    private void showStudentSubjectSettingFrame() {
        JFrame frame = new JFrame("学员科目设置");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = { "科目编号", "科目名称" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshSubjectTable(model);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String number = ask(frame, "输入科目编号");
            if (isBlank(number)) {
                return;
            }
            String name = ask(frame, "输入科目名称");
            subjects.add(new Subject(number, name));
            persistSubjects(model);
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            subjects.remove(row);
            persistSubjects(model);
        });

        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            Subject s = subjects.get(row);
            s.setSubjectNumber(askOrKeep(frame, "输入新科目编号", s.getSubjectNumber()));
            s.setSubjectName(askOrKeep(frame, "输入新科目名称", s.getSubjectName()));
            persistSubjects(model);
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void refreshSubjectTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Subject s : subjects) {
            model.addRow(new Object[] { s.getSubjectNumber(), s.getSubjectName() });
        }
    }

    private void persistSubjects(DefaultTableModel model) {
        subjectDao.replaceAll(subjects);
        refreshSubjectTable(model);
    }

    // ---------------------------------------------------------------- 毕业信息

    private void showGraduationInfoFrame() {
        JFrame frame = new JFrame("毕业信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = { "学员姓名", "准驾车型", "毕业日期" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        refreshGraduationTable(model);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String name = ask(frame, "输入学员姓名");
            if (isBlank(name)) {
                return;
            }
            String licenseType = ask(frame, "输入准驾车型");
            String date = ask(frame, "输入毕业日期");
            graduations.add(new Graduation(name, licenseType, date));
            persistGraduations(model);
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            graduations.remove(row);
            persistGraduations(model);
        });

        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(frame, "请先选中一行");
                return;
            }
            Graduation g = graduations.get(row);
            g.setStudentName(askOrKeep(frame, "输入新学员姓名", g.getStudentName()));
            g.setLicenseType(askOrKeep(frame, "输入新准驾车型", g.getLicenseType()));
            g.setGraduationDate(askOrKeep(frame, "输入新毕业日期", g.getGraduationDate()));
            persistGraduations(model);
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void refreshGraduationTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Graduation g : graduations) {
            model.addRow(new Object[] { g.getStudentName(), g.getLicenseType(), g.getGraduationDate() });
        }
    }

    private void persistGraduations(DefaultTableModel model) {
        graduationDao.replaceAll(graduations);
        refreshGraduationTable(model);
    }

    // ---------------------------------------------------------------- 输入辅助

    private static String ask(JFrame parent, String message) {
        return JOptionPane.showInputDialog(parent, message);
    }

    private static String askOrKeep(JFrame parent, String message, String current) {
        String value = JOptionPane.showInputDialog(parent, message, current);
        return value == null ? current : value;
    }

    private static double askDouble(JFrame parent, String message) {
        String raw = JOptionPane.showInputDialog(parent, message);
        return parseDouble(raw);
    }

    private static double askDoubleOrKeep(JFrame parent, String message, double current) {
        String raw = JOptionPane.showInputDialog(parent, message, String.valueOf(current));
        return raw == null ? current : parseDouble(raw);
    }

    private static double parseDouble(String raw) {
        if (raw == null || raw.isBlank()) {
            return 0;
        }
        try {
            return Double.parseDouble(raw.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "数值格式不对，按 0 处理：" + raw);
            return 0;
        }
    }

    private static int askInt(JFrame parent, String message) {
        String raw = JOptionPane.showInputDialog(parent, message);
        return parseInt(raw, -1);
    }

    private static int askIntOrKeep(JFrame parent, String message, int current) {
        String raw = JOptionPane.showInputDialog(parent, message, String.valueOf(current));
        return raw == null ? current : parseInt(raw, current);
    }

    private static int parseInt(String raw, int fallback) {
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "整数格式不对：" + raw);
            return fallback;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    /** 把启动界面切到 EDT，避免 Swing 组件在非事件线程里创建 */
    private static final class SwingHelper {
        private SwingHelper() {
        }

        static void run(Runnable body) {
            javax.swing.SwingUtilities.invokeLater(body);
        }
    }
}
