# 驾校学员管理系统（Java Swing + SQLite）

实训项目重构版。原始版本是一个 1178 行的单文件程序，数据只存在内存的 `List` 里，关掉就没了；
且 `import 实训asdasd.Login;` 引用了不存在的包，**实际编译不过**。这个版本修掉了这些问题。

## 运行

```bat
build.bat     :: 编译（生成 out\ 目录）
run.bat       :: 启动（登录：admin / 123456）
```

依赖都在 `lib\`：`sqlite-jdbc-3.47.1.0.jar`、`slf4j-api-2.0.16.jar`、`slf4j-simple-2.0.16.jar`。
需要 JDK 17 或更高（开发环境是 JDK 25）。

## 目录

```
src\abcd\
  DrivingSchoolManagementSystem.java   界面与交互（登录 + 6 个管理窗口）
  Coach.java  Student.java  Fee.java
  Training.java  Subject.java  Graduation.java      六个实体
  Db.java                              SQLite 连接与建表
  DaoSupport.java                      批量写入的事务骨架
  CoachDao.java  StudentDao.java  FeeDao.java
  TrainingDao.java  SubjectDao.java  GraduationDao.java   六个 DAO
  DaoSmokeTest.java                    无界面自测（15 项断言）
```

数据库文件落在 `data\school.db`，首次运行自动创建。

## 七个模块

| 模块 | 功能 |
|---|---|
| 登录 | 账号密码校验（实训版写死 admin/123456） |
| 教练管理 | 增删改查，字段：姓名/手机/身份证/车牌/车型 |
| 学员管理 | 增删改查，编号按 `XY` + 6 位流水自动递增 |
| 费用管理 | 增删改查，14 个字段，含套餐/优惠/实缴三个金额 |
| 训练管理 | 增删改查 + 按学员姓名查询，学时按整数校验 |
| 科目设置 | 增删改查（原版只有空按钮） |
| 毕业信息 | 增删改查（原版只有空按钮） |

## 相对原版修了什么

1. **编译不过** → 删除不存在的 `import 实训asdasd.Login;`，去掉类名上误写的泛型 `<StudentTrainingManagementFrame>`。
2. **运行时崩溃** → 训练模块原来写 `((JTable) students).getSelectedRow()`，把 `List` 强转成 `JTable`，一点就 `ClassCastException`；改为正常的表格模型。
3. **数据不落地** → 全部 `List` 只存内存；现在 6 张表都走 SQLite，每次增删改后整体写回，重启程序数据还在。
4. **空壳窗口** → 科目设置、毕业信息两个窗口原来只有按钮没有功能，补成完整的 CRUD。
5. **编号生成是假的** → `generateStudentId()` 原来恒返回 `"XY000001"`，现在扫描现有最大编号加一。
6. **一堆空方法** → 原版有大量 `// TODO Auto-generated method stub` 的空 setter 和返回 null 的 `Object getXxx()`，已全部替换成真实字段与类型化的 getter/setter。
7. **单文件 1178 行** → 拆成 13 个类，界面、实体、数据访问分层。

## 打包成 exe（可分发）

`release\DrivingSchoolMS\DrivingSchoolMS.exe` 是 jpackage 生成的便携版，**自带 JRE，双击即用**，目标机器不需要装 Java。

完整打包流程：

```bat
:: 1) 编译
javac -encoding UTF-8 -cp "lib/*" -d out src\abcd\*.java

:: 2) 解压三个依赖 jar（注意：只删签名文件，不要删整个 META-INF）
jar --extract --file lib\sqlite-jdbc-3.47.1.0.jar --dir libtmp
jar --extract --file lib\slf4j-api-2.0.16.jar      --dir libtmp
jar --extract --file lib\slf4j-simple-2.0.16.jar   --dir libtmp

:: 3) 合并成 fat jar
jar --create --file dist\app.jar --main-class abcd.DrivingSchoolManagementSystem -C out . -C libtmp .

:: 4) 生成 app-image
jpackage --type app-image --name DrivingSchoolMS --input dist --main-jar app.jar --dest release ^
  --java-options "-Dfile.encoding=UTF-8" ^
  --java-options "--enable-native-access=ALL-UNNAMED" ^
  --java-options "-Djava.io.tmpdir=$APPDIR"
```

### 打包时踩的两个坑

1. **合并 fat jar 时别删整个 `META-INF`**。删掉 `META-INF/services/java.sql.Driver` 后，`DriverManager` 找不到驱动，报 `No suitable driver found`，再被包装成 `Error opening connection`。
2. **exe 里必须把 `java.io.tmpdir` 指到可写目录**。sqlite-jdbc 会把 native 库 `sqlitejdbc.dll` 解压到 `java.io.tmpdir` 再 `System.load`；目标环境里 TEMP 不可写时抛 `java.nio.file.AccessDeniedException`，对外表现就是启动时报 `Error opening connection`。`-Djava.io.tmpdir=$APPDIR` 让它解压到 exe 旁边的 `app` 目录，问题消失。

## 已知限制

- 密码是明文硬编码，只适合教学演示。
- 每次保存是“删表 + 整表重插”，数据量到万级时应该改成增量 `UPDATE`/`INSERT`。
- 学员编号和费用单号没有做成数据库唯一约束，并发场景下可能重号。
- 时间字段是字符串，没有做格式校验。
