package abcd;

/** 教练实体 */
public class Coach {
    private String name;
    private String mobile;
    private String idCard;
    private String licensePlate;
    private String vehicleModel;

    public Coach() {
    }

    public Coach(String name, String mobile, String idCard, String licensePlate, String vehicleModel) {
        this.name = name;
        this.mobile = mobile;
        this.idCard = idCard;
        this.licensePlate = licensePlate;
        this.vehicleModel = vehicleModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    /** 表格里显示教练名字而不是对象地址 */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }
}
