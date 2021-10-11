package group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("酒店实体")
public class HotelVO implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("酒店名")
    private String name;

    @ApiModelProperty("酒店地址")
    private String address;

    @ApiModelProperty("酒店停车场")
    private String parkingLot;

    @ApiModelProperty("酒店发票")
    private String invoice;

    @ApiModelProperty("酒店接待服务")
    private String reception;

    @ApiModelProperty("酒店客房服务")
    private String room;

    @ApiModelProperty("餐饮服务")
    private String recreation;

    @ApiModelProperty("酒店其他服务")
    private String other;

    @ApiModelProperty("取消政策")
    private  String policyCancel;

    @ApiModelProperty("政策床位增加")
    private String policyaAddbed;

    @ApiModelProperty("政策其他")
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

    public String getPolicyaAddbed() {
        return policyaAddbed;
    }

    public void setPolicyaAddbed(String policyaAddbed) {
        this.policyaAddbed = policyaAddbed;
    }

    public String getPolicyOther() {
        return policyOther;
    }

    public void setPolicyOther(String policyOther) {
        this.policyOther = policyOther;
    }

    @Override
    public String toString(){
        return "HotelVO{"+
                "id='"+id+'\''+
                ",name='"+name+'\''+
                ",address='"+address+'\''+
                ",parking_lot='"+parkingLot+'\''+
                ",invoice='"+invoice+'\''+
                ",reception='"+reception+'\''+
                ",room='"+room+'\''+
                ",recreation='"+recreation+'\''+
                ",other='"+other+'\''+
                ",policy_cancel='"+policyCancel+'\''+
                ",policy_addbed='"+policyaAddbed+'\''+
                ",policy_other='"+policyOther+
                '}';


    }
}
