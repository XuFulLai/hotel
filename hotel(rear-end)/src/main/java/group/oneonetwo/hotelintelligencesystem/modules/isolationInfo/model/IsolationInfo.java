package group.oneonetwo.hotelintelligencesystem.modules.isolationInfo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName isolation_info
 */
@TableName(value ="isolation_info")
public class IsolationInfo implements Serializable {
    /**
     * 隔离人员id
     */
    @TableId
    private String id;

    /**
     * 隔离人员姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String id_card;

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    private Integer type;

    /**
     * 隔离人员电话
     */
    private String phone;

    /**
     * 隔离隔离人员邮箱
     */
    private String email;

    /**
     * 隔离酒店id
     */
    private String hotel_id;

    /**
     * 隔离酒店房间类型
     */
    private String room_type;

    /**
     * 隔离支付
     */
    private String pay;

    /**
     * 入住时间
     */
    private Date check_in_time;

    /**
     * 离开时间
     */
    private Date check_out_time;

    /**
     * 房间id
     */
    private String room_id;

    /**
     * 房间名称
     */
    private String room_name;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 是否删除
     */
    private Integer is_deleted;

    /**
     * 创建人
     */
    private String create_by;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新人
     */
    private String update_by;

    /**
     * 更新时间
     */
    private Date update_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 隔离人员id
     */
    public String getId() {
        return id;
    }

    /**
     * 隔离人员id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 隔离人员姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 隔离人员姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 身份证
     */
    public String getId_card() {
        return id_card;
    }

    /**
     * 身份证
     */
    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 隔离人员类型(0:密接,1:应隔离人员,2:入境人员,3:自行进入中高风险人员)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 隔离人员电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 隔离人员电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 隔离隔离人员邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 隔离隔离人员邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 隔离酒店id
     */
    public String getHotel_id() {
        return hotel_id;
    }

    /**
     * 隔离酒店id
     */
    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    /**
     * 隔离酒店房间类型
     */
    public String getRoom_type() {
        return room_type;
    }

    /**
     * 隔离酒店房间类型
     */
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    /**
     * 隔离支付
     */
    public String getPay() {
        return pay;
    }

    /**
     * 隔离支付
     */
    public void setPay(String pay) {
        this.pay = pay;
    }

    /**
     * 入住时间
     */
    public Date getCheck_in_time() {
        return check_in_time;
    }

    /**
     * 入住时间
     */
    public void setCheck_in_time(Date check_in_time) {
        this.check_in_time = check_in_time;
    }

    /**
     * 离开时间
     */
    public Date getCheck_out_time() {
        return check_out_time;
    }

    /**
     * 离开时间
     */
    public void setCheck_out_time(Date check_out_time) {
        this.check_out_time = check_out_time;
    }

    /**
     * 房间id
     */
    public String getRoom_id() {
        return room_id;
    }

    /**
     * 房间id
     */
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    /**
     * 房间名称
     */
    public String getRoom_name() {
        return room_name;
    }

    /**
     * 房间名称
     */
    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    /**
     * 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 是否删除
     */
    public Integer getIs_deleted() {
        return is_deleted;
    }

    /**
     * 是否删除
     */
    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    /**
     * 创建人
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * 创建人
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    /**
     * 创建时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 创建时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 更新人
     */
    public String getUpdate_by() {
        return update_by;
    }

    /**
     * 更新人
     */
    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    /**
     * 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
