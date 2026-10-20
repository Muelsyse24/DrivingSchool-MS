package abcd;

/** 费用实体 */
public class Fee {
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

    public Fee() {
    }

    public Fee(String feeNumber, String feeDate, String studentNumber, String name, String gender,
            String idCard, String mobile, String licenseType, String feeType, double packageAmount,
            double discountAmount, double paidAmount, String salesperson, String operator) {
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

    public String getFeeNumber() {
        return feeNumber;
    }

    public void setFeeNumber(String feeNumber) {
        this.feeNumber = feeNumber;
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return feeNumber == null ? "" : feeNumber;
    }
}
