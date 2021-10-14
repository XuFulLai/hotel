package group.oneonetwo.hotelintelligencesystem.modules.hotel.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.security.SecureRandom;

@TableName("hotel")
public class HotelP0 implements Serializable {
    @TableId("id")
    private String id;

    @TableField("dept_id")
    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @TableField("name")
    private String name;

    @TableField("address")
    private  String address;

    @TableField("parking_lot")
    private  String parkingLot;

    @TableField("invoice")
    private  String invoice;

    @TableField("reception")
    private String reception;

    @TableField("room")
    private String room;

    @TableField("recreation")
    private String recreation;

    @TableField("other")
    private String other;

    @TableField("policy_cancel")
    private String policyCancel;

    @TableField("policy_addbed")
    private String policyAddbed;

    @TableField("policy_other")
    private String policyOther;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getReception() {
        return reception;
    }

    public void setReception(String reception) {
        this.reception = reception;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRecreation() {
        return recreation;
    }

    public void setRecreation(String recreation) {
        this.recreation = recreation;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPolicyCancel() {
        return policyCancel;
    }

    public void setPolicyCancel(String policyCancel) {
        this.policyCancel = policyCancel;
    }

    public String getPolicyAddbed() {
        return policyAddbed;
    }

    public void setPolicyAddbed(String policyAddbed) {
        this.policyAddbed = policyAddbed;
    }

    public String getPolicyOther() {
        return policyOther;
    }

    public void setPolicyOther(String policyOther) {
        this.policyOther = policyOther;
    }
}
