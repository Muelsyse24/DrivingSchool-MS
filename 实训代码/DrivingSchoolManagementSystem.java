package abcd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



import 实训asdasd.Login;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class DrivingSchoolManagementSystem<StudentTrainingManagementFrame> {

    private static final TableModel studentTableModel = null;
	private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;

    private JFrame mainFrame; // 主界面框架
    private JPanel contentPanel; // 内容面板

    private List<Coach> coaches = new ArrayList<>(); // 存储教练信息的列表
    private List<Student> students = new ArrayList<>(); // 存储学员信息的列表
    private List<Fee> fees = new ArrayList<>(); // 存储费用信息的列表
    private List<Training> trainings = new ArrayList<>(); // 存储训练信息的列表
    private List<Subject> subjects = new ArrayList<>(); // 存储科目信息的列表
    private List<Graduation> graduations = new ArrayList<>(); // 存储毕业信息的列表

    public DrivingSchoolManagementSystem() {
        JFrame frame = new JFrame("驾校管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("登录");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        frame.add(panel, BorderLayout.CENTER);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if ("admin".equals(username) && "123456".equals(password)) {
                    // 登录成功，显示主界面
                    showMainFrame();
                } else {
                    JOptionPane.showMessageDialog(frame, "登录失败");
                }
            }
        });

        frame.setVisible(true);
    }



    private void showMainFrame() {
        JFrame mainFrame = new JFrame("驾校管理系统主界面");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // 教练管理按钮
        JButton coachManagementButton = new JButton("教练管理");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(coachManagementButton, gbc);
        coachManagementButton.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                showCoachManagementFrame();
            }
        });


        // 学员基本信息管理按钮
        JButton studentInfoManagementButton = new JButton("学员基本信息管理");
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(studentInfoManagementButton, gbc);
        studentInfoManagementButton.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
            	showStudentManagementFrame();
            }
        });

        // 学员费用管理按钮
        JButton studentFeeManagementButton = new JButton("学员费用管理");
        gbc.gridx = 2;
        gbc.gridy = 0;
        contentPanel.add(studentFeeManagementButton, gbc);
        studentFeeManagementButton.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
            	showStudentFeeManagementFrame();
            }

			
        });
        // 学员训练管理按钮studentFeeManagementButton
        JButton studentTrainingManagementButton = new JButton("学员训练管理");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(studentTrainingManagementButton, gbc);
        studentTrainingManagementButton.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                showStudentTrainingManagementFrame();
            }
        });
        // 学员科目设置按钮
        JButton studentSubjectSettingButton = new JButton("学员科目设置");
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(studentSubjectSettingButton, gbc);
        studentSubjectSettingButton.addActionListener(new ActionListener() {

         
            public void actionPerformed(ActionEvent e) {
                showStudentSubjectSettingFrame();
            }
        });
        // 毕业信息按钮
        JButton graduationInfoButton = new JButton("毕业信息");
        gbc.gridx = 2;
        gbc.gridy = 1;
        contentPanel.add(graduationInfoButton, gbc);
        graduationInfoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showGraduationInfoFrame();
            }
        });
        // 退出系统按钮
        JButton logoutButton = new JButton("退出系统");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(logoutButton, gbc);

        mainFrame.add(contentPanel);
        mainFrame.setVisible(true);
        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void showCoachManagementFrame() {
        JFrame coachManagementFrame = new JFrame("教练管理系统");
        coachManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);

        DefaultTableModel coachTableModel = new DefaultTableModel(new String[] { "教练姓名", "手机号码", "身份证号", "教练车牌", "车辆型号" }, 0);
        JTable coachTable = new JTable(coachTableModel);

        // 模拟添加一些教练数据
        Coach coach1 = new Coach("张三", "138xxxx1234", "123456789012345678", "粤 A12345", "大众");
        Coach coach2 = new Coach("李四", "136xxxx5678", "987654321098765432", "粤 B67890", "丰田");
        coaches.add(coach1);
        coaches.add(coach2);
        for (Coach coach : coaches) {
            coachTableModel.addRow(new Object[] { coach.getName(), coach.getPhoneNumber(), coach.getIdCard(), coach.getCoachLicensePlate(), coach.getVehicleModel() });
        }
        coachManagementFrame.add(topPanel, BorderLayout.NORTH);
        coachManagementFrame.add(new JScrollPane(coachTable), BorderLayout.CENTER);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 添加教练逻辑
                String name = JOptionPane.showInputDialog(coachManagementFrame, "输入教练姓名");
                String phone = JOptionPane.showInputDialog(coachManagementFrame, "输入手机号码");
                String idCard = JOptionPane.showInputDialog(coachManagementFrame, "输入身份证号");
                String licensePlate = JOptionPane.showInputDialog(coachManagementFrame, "输入教练车牌");
                String vehicleModel = JOptionPane.showInputDialog(coachManagementFrame, "输入车辆型号");
                Coach newCoach = new Coach(name, phone, idCard, licensePlate, vehicleModel);
                coaches.add(newCoach);
                coachTableModel.addRow(new Object[] { name, phone, idCard, licensePlate, vehicleModel });
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 删除教练逻辑
                int selectedRow = coachTable.getSelectedRow();
                if (selectedRow!= -1) {
                    coachTableModel.removeRow(selectedRow);
                    coaches.remove(selectedRow);
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {
                // 修改教练逻辑
                int selectedRow = coachTable.getSelectedRow();
                if (selectedRow!= -1) {
                    Coach coach = coaches.get(selectedRow);
                    String newName = JOptionPane.showInputDialog(coachManagementFrame, "输入新教练姓名", coach.getName());
                    String newPhone = JOptionPane.showInputDialog(coachManagementFrame, "输入新手机号码", coach.getPhoneNumber());
                    String newIdCard = JOptionPane.showInputDialog(coachManagementFrame, "输入新身份证号", coach.getIdCard());
                    String newLicensePlate = JOptionPane.showInputDialog(coachManagementFrame, "输入新教练车牌", coach.getCoachLicensePlate());
                    String newVehicleModel = JOptionPane.showInputDialog(coachManagementFrame, "输入新车辆型号", coach.getVehicleModel());
                    coach.setName(newName);
                    coach.setPhoneNumber(newPhone);
                    coach.setIdCard(newIdCard);
                    coach.setCoachLicensePlate(newLicensePlate);
                    coach.setVehicleModel(newVehicleModel);
                    coachTableModel.setValueAt(newName, selectedRow, 0);
                    coachTableModel.setValueAt(newPhone, selectedRow, 1);
                    coachTableModel.setValueAt(newIdCard, selectedRow, 2);
                    coachTableModel.setValueAt(newLicensePlate, selectedRow, 3);
                    coachTableModel.setValueAt(newVehicleModel, selectedRow, 4);
                }
            }
        });
        coachManagementFrame.pack();
        coachManagementFrame.setVisible(true);
    }
    private void showStudentManagementFrame() {
        JFrame studentManagementFrame = new JFrame("学员管理系统");
        studentManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(modifyButton);
        DefaultTableModel studentTableModel = new DefaultTableModel(new String[] { "学员编号", "姓名", "身份证号", "身份证图片", "手机号码", "地址", "申请类型", "报名时间", "所属教练" }, 0);
        JTable studentTable = new JTable(studentTableModel);
        // 模拟添加一些学员数据
        Student student1 = new Student("XY000001", "张三", "123456789012345678", "张三.jpg", "138xxxx1234", "北京市", "C1", "2023-01-01", "张三");
        Student student2 = new Student("XY000002", "李四", "987654321098765432", "李四.jpg", "136xxxx5678", "上海市", "C2", "2023-02-01", "李四");
        students.add(student1);
        students.add(student2);
        for (Student student : students) {
            studentTableModel.addRow(new Object[] { student.getId(), student.getName(), student.getIdCard(), student.getIdCardImage(), student.getPhoneNumber(), student.getAddress(), student.getApplicationType(), student.getEnrollmentTime(), student.getCoach() });
        }
        studentManagementFrame.add(topPanel, BorderLayout.NORTH);
        studentManagementFrame.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 添加学员逻辑
                String id = generateStudentId();
                String name = JOptionPane.showInputDialog(studentManagementFrame, "输入学员姓名");
                String idCard = JOptionPane.showInputDialog(studentManagementFrame, "输入学员身份证号");
                String idCardImage = JOptionPane.showInputDialog(studentManagementFrame, "输入学员身份证图片路径");
                String phoneNumber = JOptionPane.showInputDialog(studentManagementFrame, "输入学员手机号码");
                String address = JOptionPane.showInputDialog(studentManagementFrame, "输入学员地址");
                String applicationType = JOptionPane.showInputDialog(studentManagementFrame, "输入学员申请类型");
                String enrollmentTime = JOptionPane.showInputDialog(studentManagementFrame, "输入学员报名时间");
                String coach = JOptionPane.showInputDialog(studentManagementFrame, "输入学员所属教练");
                Student newStudent = new Student(id, name, idCard, idCardImage, phoneNumber, address, applicationType, enrollmentTime, coach);
                students.add(newStudent);
                studentTableModel.addRow(new Object[] { id, name, idCard, idCardImage, phoneNumber, address, applicationType, enrollmentTime, coach });
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 删除学员逻辑
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow!= -1) {
                    studentTableModel.removeRow(selectedRow);
                    students.remove(selectedRow);
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {       
            public void actionPerformed(ActionEvent e) {
                // 修改学员逻辑
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow!= -1) {
                    Student student = students.get(selectedRow);
                    String newName = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员姓名", student.getName());
                    String newIdCard = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员身份证号", student.getIdCard());
                    String newIdCardImage = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员身份证图片路径", student.getIdCardImage());
                    String newPhoneNumber = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员手机号码", student.getPhoneNumber());
                    String newAddress = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员地址", student.getAddress());
                    String newApplicationType = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员申请类型", student.getApplicationType());
                    String newEnrollmentTime = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员报名时间", student.getEnrollmentTime());
                    String newCoach = JOptionPane.showInputDialog(studentManagementFrame, "输入新学员所属教练", student.getCoach());
                    student.setName(newName);
                    student.setIdCard(newIdCard);
                    student.setIdCardImage(newIdCardImage);
                    student.setPhoneNumber(newPhoneNumber);
                    student.setAddress(newAddress);
                    student.setApplicationType(newApplicationType);
                    student.setEnrollmentTime(newEnrollmentTime);
                    student.setCoach(newCoach);
                    studentTableModel.setValueAt(newName, selectedRow, 1);
                    studentTableModel.setValueAt(newIdCard, selectedRow, 2);
                    studentTableModel.setValueAt(newIdCardImage, selectedRow, 3);
                    studentTableModel.setValueAt(newPhoneNumber, selectedRow, 4);
                    studentTableModel.setValueAt(newAddress, selectedRow, 5);
                    studentTableModel.setValueAt(newApplicationType, selectedRow, 6);
                    studentTableModel.setValueAt(newEnrollmentTime, selectedRow, 7);
                    studentTableModel.setValueAt(newCoach, selectedRow, 8);
                }
            }
        });
        studentManagementFrame.pack();
        studentManagementFrame.setVisible(true);
    }
    // 生成学员编号
    private String generateStudentId() {
        // 从数据库中查找最大编号
        // 如果为空，则从 XY000000 开始
        // 否则在最大编号基础上加 1
        return "XY000001";
    }
//    private void showStudentFeeManagementFrame() {
//        JFrame frame = new JFrame("学员费用管理");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setSize(600, 400);
//
//        JPanel panel = new JPanel();
//        JButton addButton = new JButton("添加");
//        JButton deleteButton = new JButton("删除");
//        JButton modifyButton = new JButton("修改");
//        JButton importButton = new JButton("导入");
//        JButton exportButton = new JButton("导出");
//
//        panel.add(addButton);
//        panel.add(deleteButton);
//        panel.add(modifyButton);
//        panel.add(importButton);
//        panel.add(exportButton);
//
//        frame.add(panel, BorderLayout.CENTER);
//
//        frame.setVisible(true);
//    }framefeeManagementFrame
 
    private void showStudentFeeManagementFrame() {
        JFrame frame= new JFrame("费用管理系统");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,400);
        JPanel Panel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        Panel.add(addButton);
        Panel.add(deleteButton);
        Panel.add(modifyButton);
        frame.add(Panel,BorderLayout.CENTER);
        frame.setVisible(true);
        
        DefaultTableModel feeTableModel = new DefaultTableModel(new String[] { "单号", "单据日期", "学员编号", "姓名", "性别", "身份证号", "手机号", "准驾车型", "费用类型", "套餐金额", "优惠金额", "实际缴费金额", "业务员", "操作人" }, 0);
        JTable feeTable = new JTable(feeTableModel);

        // 模拟添加一些费用数据
        Fee fee1 = new Fee("SJ000001", "2023-01-01", "XY000001", "张三", "男", "123456789012345678", "138xxxx1234", "C1", "学费", 5000.0, 0.0, 5000.0, "业务员 1", "操作人 1");
        Fee fee2 = new Fee("SJ000002", "2023-02-01", "XY000002", "李四", "女", "987654321098765432", "136xxxx5678", "C2", "补考费", 200.0, 0.0, 200.0, "业务员 2", "操作人 2");
        fees.add(fee1);
        fees.add(fee2);
        for (Fee fee : fees) {
            feeTableModel.addRow(new Object[] { fee.getId(), fee.getDate(), fee.getStudentId(), fee.getName(), fee.getGender(), fee.getIdCard(), fee.getPhoneNumber(), fee.getLicenseType(), fee.getType(), fee.getPackageAmount(), fee.getDiscountAmount(), fee.getPaidAmount(), fee.getSalesperson(), fee.getOperator() });
        }

        frame.add(Panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(feeTable), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 添加费用逻辑
                String id = generateFeeId();
                String date = JOptionPane.showInputDialog(frame, "输入单据日期");
                String studentId = JOptionPane.showInputDialog(frame, "输入学员编号");
                String name = JOptionPane.showInputDialog(frame, "输入学员姓名");
                String gender = JOptionPane.showInputDialog(frame, "输入学员性别");
                String idCard = JOptionPane.showInputDialog(frame, "输入学员身份证号");
                String phoneNumber = JOptionPane.showInputDialog(frame, "输入学员手机号码");
                String licenseType = JOptionPane.showInputDialog(frame, "输入准驾车型");
                String type = JOptionPane.showInputDialog(frame, "输入费用类型");
                Double packageAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "输入套餐金额"));
                Double discountAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "输入优惠金额"));
                Double paidAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "输入实际缴费金额"));
                String salesperson = JOptionPane.showInputDialog(frame, "输入业务员");
                String operator = JOptionPane.showInputDialog(frame, "输入操作人");

                Fee newFee = new Fee(id, date, studentId, name, gender, idCard, phoneNumber, licenseType, type, packageAmount, discountAmount, paidAmount, salesperson, operator);
                fees.add(newFee);
                feeTableModel.addRow(new Object[] { id, date, studentId, name, gender, idCard, phoneNumber, licenseType, type, packageAmount, discountAmount, paidAmount, salesperson, operator });
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 删除费用逻辑
                int selectedRow = feeTable.getSelectedRow();
                if (selectedRow!= -1) {
                    feeTableModel.removeRow(selectedRow);
                    fees.remove(selectedRow);
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 修改费用逻辑
                int selectedRow = feeTable.getSelectedRow();
                if (selectedRow!= -1) {
                    Fee fee = fees.get(selectedRow);
                    String newId = JOptionPane.showInputDialog(frame, "输入新的单号", fee.getId());
                    String newDate = JOptionPane.showInputDialog(frame, "输入新的单据日期", fee.getDate());
                    String newStudentId = JOptionPane.showInputDialog(frame, "输入新的学员编号", fee.getStudentId());
                    String newName = JOptionPane.showInputDialog(frame, "输入新的学员姓名", fee.getName());
                    String newGender = JOptionPane.showInputDialog(frame, "输入新的学员性别", fee.getGender());
                    String newIdCard = JOptionPane.showInputDialog(frame, "输入新的学员身份证号", fee.getIdCard());
                    String newPhoneNumber = JOptionPane.showInputDialog(frame, "输入新的学员手机号码", fee.getPhoneNumber());
                    String newLicenseType = JOptionPane.showInputDialog(frame, "输入新的准驾车型", fee.getLicenseType());
                    String newType = JOptionPane.showInputDialog(frame, "输入新的费用类型", fee.getType());
                    Double newPackageAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "输入新的套餐金额", fee.getPackageAmount()));
                    Double newDiscountAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "输入新的优惠金额", fee.getDiscountAmount()));
                    Double newPaidAmount = Double.parseDouble(JOptionPane.showInputDialog(frame, "输入新的实际缴费金额", fee.getPaidAmount()));
                    String newSalesperson = JOptionPane.showInputDialog(frame, "输入新的业务员", fee.getSalesperson());
                    String newOperator = JOptionPane.showInputDialog(frame, "输入新的操作人", fee.getOperator());
                    fee.setId(newId);
                    fee.setDate(newDate);
                    fee.setStudentId(newStudentId);
                    fee.setName(newName);
                    fee.setGender(newGender);
                    fee.setIdCard(newIdCard);
                    fee.setPhoneNumber(newPhoneNumber);
                    fee.setLicenseType(newLicenseType);
                    fee.setType(newType);
                    fee.setPackageAmount(newPackageAmount);
                    fee.setDiscountAmount(newDiscountAmount);
                    fee.setPaidAmount(newPaidAmount);
                    fee.setSalesperson(newSalesperson);
                    fee.setOperator(newOperator);
                    feeTableModel.setValueAt(newId, selectedRow, 0);
                    feeTableModel.setValueAt(newDate, selectedRow, 1);
                    feeTableModel.setValueAt(newStudentId, selectedRow, 2);
                    feeTableModel.setValueAt(newName, selectedRow, 3);
                    feeTableModel.setValueAt(newGender, selectedRow, 4);
                    feeTableModel.setValueAt(newIdCard, selectedRow, 5);
                    feeTableModel.setValueAt(newPhoneNumber, selectedRow, 6);
                    feeTableModel.setValueAt(newLicenseType, selectedRow, 7);
                    feeTableModel.setValueAt(newType, selectedRow, 8);
                    feeTableModel.setValueAt(newPackageAmount, selectedRow, 9);
                    feeTableModel.setValueAt(newDiscountAmount, selectedRow, 10);
                    feeTableModel.setValueAt(newPaidAmount, selectedRow, 11);
                    feeTableModel.setValueAt(newSalesperson, selectedRow, 12);
                    feeTableModel.setValueAt(newOperator, selectedRow, 13);
                }
            }
        });
    }
    public String generateFeeId() {
        // 生成唯一的费用单号
        return "SJ" + (fees.size() + 1) + "000001";
    }
    
    
//    private void showStudentTrainingManagementFrame() {
//        JFrame frame = new JFrame("学员训练管理");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setSize(600, 400);
//        JPanel panel = new JPanel();
//        JButton addButton = new JButton("添加");
//        JButton deleteButton = new JButton("删除");
//        JButton modifyButton = new JButton("修改");
//        panel.add(addButton);
//        panel.add(deleteButton);
//        panel.add(modifyButton);
//        frame.add(panel, BorderLayout.CENTER);
//        frame.setVisible(true);
//    }



        private void showStudentTrainingManagementFrame() {
////            mainFrame = new JFrame("学员训练管理系统");
////            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////            mainFrame.setBounds(100, 100, 800, 600);
//        	JFrame frame = new JFrame("学员训练管理");
//          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//          frame.setSize(600, 400);
////            contentPanel = new JPanel();
////            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
////            mainFrame.setContentPane(contentPanel);
////            contentPanel.setLayout(new BorderLayout(0, 0));
//          JPanel panel = new JPanel();
////            JPanel topPanel = new JPanel();
//            JButton addButton = new JButton("添加");
//            JButton deleteButton = new JButton("删除");
//            JButton modifyButton = new JButton("修改");
//            JButton queryButton = new JButton("查询");
//            panel.add(addButton);
//            panel.add(deleteButton);
//            panel.add(modifyButton);
//            panel.add(queryButton);
//
////            contentPanel.add(panel, BorderLayout.NORTH);
//
//		            frame.add(panel, BorderLayout.CENTER);
//          frame.setVisible(true);
//            students = (List<Student>) new JTable(studentTableModel);
//
//            contentPanel.add(new JScrollPane(), BorderLayout.CENTER);
//
//            students = new ArrayList<>();
//JFrame frame = new JFrame("学员培训管理系统");
        	JFrame frame = new JFrame("学员培训管理系统");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel = new JPanel();

            JButton addButton = new JButton("添加");
            JButton deleteButton = new JButton("删除");
            JButton modifyButton = new JButton("修改");
            JButton queryButton = new JButton("查询");

            panel.add(addButton);
            panel.add(deleteButton);
            panel.add(modifyButton);
            panel.add(queryButton);

            frame.add(panel, BorderLayout.CENTER);

            frame.setVisible(true);
            addButton.addActionListener(new ActionListener() {

                @Override
        	
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(mainFrame, "输入学员姓名");
                    if (name == null || name.isEmpty()) {
                        return;
                    }
                    String subjectType = JOptionPane.showInputDialog(mainFrame, "输入科目类型（场地训练/路考训练/文明驾驶训练）");
                    if (subjectType == null || subjectType.isEmpty()) {
                        return;
                    }
                    int hours;
                    try {
                        hours = Integer.parseInt(JOptionPane.showInputDialog(mainFrame, "输入学时"));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "学时输入无效，请重新输入！");
                        return;
                    }
                    String practiceDate = JOptionPane.showInputDialog(mainFrame, "输入练习日期");
                    if (practiceDate == null || practiceDate.isEmpty()) {
                        return;
                    }

                    Student newStudent = new Student(name, subjectType, hours, practiceDate);
                    students.add(newStudent);
                    ((DefaultTableModel) studentTableModel).addRow(new Object[]{name, subjectType, hours, practiceDate});
                }
            });

            deleteButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = ((JTable) students).getSelectedRow();
                    if (selectedRow!= -1) {
                        students.remove(selectedRow);
                        ((DefaultTableModel) studentTableModel).removeRow(selectedRow);
                    }
                }
            });

            modifyButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int selectedRow = ((JTable) students).getSelectedRow();
                    if (selectedRow!= -1) {
                        Student student = students.get(selectedRow);
                        String newName = JOptionPane.showInputDialog(mainFrame, "输入新学员姓名", student.getName());
                        if (newName == null || newName.isEmpty()) {
                            return;
                        }
                        String newSubjectType = JOptionPane.showInputDialog(mainFrame, "输入新科目类型", student.getSubjectType());
                        if (newSubjectType == null || newSubjectType.isEmpty()) {
                            return;
                        }
                        int newHours;
                        try {
                            newHours = Integer.parseInt(JOptionPane.showInputDialog(mainFrame, "输入新学时", String.valueOf(student.getHours())));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "学时输入无效，请重新输入！");
                            return;
                        }
                        String newPracticeDate = JOptionPane.showInputDialog(mainFrame, "输入新练习日期", student.getPracticeDate());
                        if (newPracticeDate == null || newPracticeDate.isEmpty()) {
                            return;
                        }

                        student.setName(newName);
                        student.setSubjectType(newSubjectType);
                        student.setHours(newHours);
                        student.setPracticeDate(newPracticeDate);

                        studentTableModel.setValueAt(newName, selectedRow, 0);
                        studentTableModel.setValueAt(newSubjectType, selectedRow, 1);
                        studentTableModel.setValueAt(newHours, selectedRow, 2);
                        studentTableModel.setValueAt(newPracticeDate, selectedRow, 3);
                    }
                }
            });

            queryButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // 查询逻辑可以在这里添加
                }
            });
        }

       
    

    
    private void showStudentSubjectSettingFrame() {
        JFrame frame = new JFrame("学员科目设置");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        JPanel panel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(modifyButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    
    
    
    private void showGraduationInfoFrame() {
        JFrame frame = new JFrame("毕业信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        JPanel panel = new JPanel();
        JButton addButton = new JButton("添加");
        JButton deleteButton = new JButton("删除");
        JButton modifyButton = new JButton("修改");
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(modifyButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new DrivingSchoolManagementSystem();
    }
}
class Coach {
    private String name;
    private String mobile;
    private String idCard;
    private String licensePlate;
    private String vehicleModel;
    // 构造函数、getter 和 setter 方法
    public Coach(String name, String mobile, String idCard, String licensePlate, String vehicleModel) {
        this.name = name;
        this.mobile = mobile;
        this.idCard = idCard;
        this.licensePlate = licensePlate;
        this.vehicleModel = vehicleModel;
    }
    public void setVehicleModel(String newVehicleModel) {}
	public void setCoachLicensePlate(String newLicensePlate) {}
	public void setIdCard(String newIdCard) {
		// TODO Auto-generated method stub
		
	}

	public void setPhoneNumber(String newPhone) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String newName) {
		// TODO Auto-generated method stub
		
	}

	public Object getCoachLicensePlate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }
}

class Student {
    private String studentNumber;
    private String name;
    private String idCard;
    private String mobile;
    private String address;
    private String applicationType;
    private String registrationDate;
    private Coach coach;

    // 构造函数、getter 和 setter 方法

    public Student(String studentNumber, String name, String idCard, String mobile, String address, String applicationType,
            String registrationDate, Coach coach) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.idCard = idCard;
        this.mobile = mobile;
        this.address = address;
        this.applicationType = applicationType;
        this.registrationDate = registrationDate;
        this.coach = coach;
    }

    public void setPracticeDate(String newPracticeDate) {
		// TODO Auto-generated method stub
		
	}

	public void setHours(int newHours) {
		// TODO Auto-generated method stub
		
	}

	public void setSubjectType(String newSubjectType) {
		// TODO Auto-generated method stub
		
	}

	public Object getPracticeDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] getHours() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSubjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCoach(String newCoach) {
		// TODO Auto-generated method stub
		
	}

	public void setEnrollmentTime(String newEnrollmentTime) {
		// TODO Auto-generated method stub
		
	}

	public void setApplicationType(String newApplicationType) {
		// TODO Auto-generated method stub
		
	}

	public void setAddress(String newAddress) {
		// TODO Auto-generated method stub
		
	}

	public void setPhoneNumber(String newPhoneNumber) {
		// TODO Auto-generated method stub
		
	}

	public void setIdCardImage(String newIdCardImage) {
		// TODO Auto-generated method stub
		
	}

	public void setIdCard(String newIdCard) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String newName) {
		// TODO Auto-generated method stub
		
	}

	public Object getIdCardImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getEnrollmentTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Student(String string, String string2, String string3, String string4, String string5, String string6,
			String string7, String string8, String string9) {
		// TODO Auto-generated constructor stub
	}

	public Student(String name2, String subjectType, int hours, String practiceDate) {
		// TODO Auto-generated constructor stub
	}

	public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public Coach getCoach() {
        return coach;
    }
}

class Fee {
    private String feeNumber;
    private String feeDate;
    private String studentNumber;
    private String name;
    private String gender;
    private String idCard;
    private String mobile;
    private String licenseType;
    private String feeType;
    private double packageAmount;
    private double discountAmount;
    private double paidAmount;
    private String salesperson;
    private String operator;

    // 构造函数、getter 和 setter 方法

    public Fee(String feeNumber, String feeDate, String studentNumber, String name, String gender, String idCard,
            String mobile, String licenseType, String feeType, double packageAmount, double discountAmount,
            double paidAmount, String salesperson, String operator) {
        this.feeNumber = feeNumber;
        this.feeDate = feeDate;
        this.studentNumber = studentNumber;
        this.name = name;
        this.gender = gender;
        this.idCard = idCard;
        this.mobile = mobile;
        this.licenseType = licenseType;
        this.feeType = feeType;
        this.packageAmount = packageAmount;
        this.discountAmount = discountAmount;
        this.paidAmount = paidAmount;
        this.salesperson = salesperson;
        this.operator = operator;
    }

    public void setOperator(String newOperator) {
		// TODO Auto-generated method stub
		
	}

	public void setSalesperson(String newSalesperson) {
		// TODO Auto-generated method stub
		
	}

	public void setPaidAmount(Double newPaidAmount) {
		// TODO Auto-generated method stub
		
	}

	public void setDiscountAmount(Double newDiscountAmount) {
		// TODO Auto-generated method stub
		
	}

	public void setPackageAmount(Double newPackageAmount) {
		// TODO Auto-generated method stub
		
	}

	public void setType(String newType) {
		// TODO Auto-generated method stub
		
	}

	public void setLicenseType(String newLicenseType) {
		// TODO Auto-generated method stub
		
	}

	public void setPhoneNumber(String newPhoneNumber) {
		// TODO Auto-generated method stub
		
	}

	public void setIdCard(String newIdCard) {
		// TODO Auto-generated method stub
		
	}

	public void setGender(String newGender) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String newName) {
		// TODO Auto-generated method stub
		
	}

	public void setStudentId(String newStudentId) {
		// TODO Auto-generated method stub
		
	}

	public void setDate(String newDate) {
		// TODO Auto-generated method stub
		
	}

	public void setId(String newId) {
		// TODO Auto-generated method stub
		
	}

	public Object getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStudentId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFeeNumber() {
        return feeNumber;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getFeeType() {
        return feeType;
    }

    public double getPackageAmount() {
        return packageAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public String getOperator() {
        return operator;
    }
}

class Training {
    private String studentName;
    private String subjectType;
    private int studyHours;
    private String practiceDate;

    // 构造函数、getter 和 setter 方法

    public Training(String studentName, String subjectType, int studyHours, String practiceDate) {
        this.studentName = studentName;
        this.subjectType = subjectType;
        this.studyHours = studyHours;
        this.practiceDate = practiceDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public int getStudyHours() {
        return studyHours;
    }

    public String getPracticeDate() {
        return practiceDate;
    }
}

class Subject {
    private String subjectNumber;
    private String subjectName;

    // 构造函数、getter 和 setter 方法

    public Subject(String subjectNumber, String subjectName) {
        this.subjectNumber = subjectNumber;
        this.subjectName = subjectName;
    }

    public String getSubjectNumber() {
        return subjectNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }
}

class Graduation {
    private String studentName;
    private String licenseType;
    private String graduationDate;

    // 构造函数、getter 和 setter 方法

    public Graduation(String studentName, String licenseType, String graduationDate) {
        this.studentName = studentName;
        this.licenseType = licenseType;
        this.graduationDate = graduationDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getGraduationDate() {
        return graduationDate;
    }
}