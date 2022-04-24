<template>

  <div style="min-height: 100vh">
    
    <!-- 导航栏 -->
    <top-nav></top-nav>

    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="" />
    </div>

    <div class="record-list-main">

      <div class="record-list-content">

        <div class="d-flex align-items-center justify-content-between">

          <div
            :class="isIsolation ? 'active' : ''"
            class="order-status cursor"
            @click="isolationRecordsHandle(!isIsolation)">

            <div>
              <h3 class="font-22">隔离记录</h3>
              <p class="font-16">{{ isolationNum}}</p>
            </div>

          </div>

          <div
            :class="isApply ? 'active' : ''"
            class="order-status cursor"
            @click="applyRecordsHandle(!isApply)">

            <div>
              <h3 class="font-22">申请记录</h3>
              <p class="font-16">12</p>
            </div>

          </div>

        </div>

        <!-- 隔离记录 -->
        <div v-if="isIsolation" class="d-flex flex-column justify-content-between">

          <ul class="order-list">
            <li v-for="(item, index) in isolationList">
              <div class="d-flex align-items-center justify-content-between mb-15 font-20">
                <p>{{ item.status | isolationStatusFilter }}</p>
                <div>
                  <el-button
                    v-if="item.status == 0"
                    @click="getCheckRecords(item.id)">检测记录</el-button>
                  <el-button
                    v-if="item.status == 0"
                    type="primary"
                    @click="applyHandle">物质申请</el-button>
                </div>
              </div>
              <div class="d-flex align-items-center justify-content-between font-16 color-6">
                <div>
                  <p class="mb-10">
                    {{ $t("orderList.hotelName") }}{{ item.hotelName }}
                  </p>
                  <p class="mb-10">隔离ID：{{ item.id }}</p>
                  <div class="d-flex align-items-center">
                    <p>{{ $t("orderList.roomType") }}{{ item.roomTypeName | roomNameFormat }}</p>
                    <p class="ml-10 mr-10" style="color: #e0e0e0">|</p>
                    <p>{{ $t("isolationList.roomName") }}{{ item.roomName }}</p>
                  </div>
                </div>
                <p v-if="item.pay">{{ $t("orderList.spend") }}￥{{ item.pay }}</p>
              </div>
            </li>
          </ul>

          <div v-if="pageNum > 5" class="d-flex align-items-center justify-content-center">
            <el-pagination
                background
                @current-change="isolationCurrent"
                @prev-click="isolationPrev"
                @next-click="isolationNext"
                layout="prev, pager, next"
                :total="isolationNum">
            </el-pagination>
          </div>

        </div>

        <div v-else-if="isApply" class="d-flex flex-column justify-content-between">
          <!-- 申请记录 -->
          <ul class="order-list">
            <li v-for="(item, index) in applyList">
              <div class="d-flex align-items-center justify-content-between mb-15 font-20">
                <p>{{ item.reviewStatus | applyStatusFilter }}</p>
              </div>
              <div class="d-flex align-items-center justify-content-between font-16 color-6">
                <div>
                  <p class="mb-10">申请物品：{{ item.applyThing }}</p>
                  <p class="mb-10">申请数量：{{ "" + item.applyNum + item.thingUnit }}</p>
                  <p class="mb-10">申请ID：{{ item.id }}</p>
                  <p class="mb-10">申请说明：{{ item.applyRemark }}</p>
                  <p style="color: red" v-if="item.reviewRemarks" class="mb-10">审核说明：{{ item.reviewRemarks }}</p>
                </div>
                <p v-if="item.pay">{{ $t("orderList.spend") }}￥{{ item.pay }}</p>
              </div>
            </li>
          </ul>
        </div>


      </div>

      <!-- 暂无订单，改为暂无记录 -->
      <!-- <div v-show="orderList.length == 0" class="order-null text-center font-30">{{ $t('orderList.noOrder') }}</div> -->

      <Footer></Footer>

      <!-- 检测记录dialog -->
      <!-- <el-dialog
          title="检测记录"
          :visible.sync="checkVisible"
          width="570px">
      <div class="content">
          <div class="d-flex align-items-center mb-15">
              <p class="w-100 text-left">检测人姓名:</p>
              <el-input
                  style="width: 350px;"
                  placeholder="检测人姓名"
                  v-model="check.name"
                  disabled
                  clearable>
              </el-input>
          </div>            
          <div v-if="testValue == 'modify'" class="d-flex align-items-center mb-15">
              <p class="w-100 text-left">检测类型:</p>
              <el-tag v-if="form.checkType==0" type="danger">紧急</el-tag>
              <el-tag v-if="form.checkType==1" type="warning">应隔离人员</el-tag>
              <el-tag v-if="form.checkType==2" type="info">入境人员</el-tag>
              <el-tag v-if="form.checkType==3" >中高风险地区人员</el-tag>
          </div>
          <div class="d-flex align-items-center mb-15">
              <p class="w-100 text-left">检测类型：</p>
              <el-select v-model="form.checkType" placeholder="请选择">
                  <el-option
                  v-for="item in testOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                  </el-option>
              </el-select>
          </div>
          <div class="d-flex align-items-center mb-15">
              <p class="w-100 text-left">检测结果：</p>
              <el-input
                  v-if="form.checkType == 0"
                  style="width: 350px;"
                  placeholder="请输入检测温度"
                  v-model="form.checkRes"
                  clearable>
              </el-input>
              <el-select v-else v-model="form.checkRes" placeholder="请选择">
                  <el-option
                      v-for="item in resultOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
              </el-select>
          </div>
          <div class="d-flex align-items-center">
              <p class="w-100 text-left">检测时间:</p>
              <el-date-picker
                  v-model="form.checkTime"
                  type="datetime"
                  placeholder="选择日期时间">
              </el-date-picker>
          </div>

      </div>
      <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="confirm">确 定</el-button>
          </span>
      </el-dialog> -->

      <!--      物资申请dialog-->
      <!-- <el-dialog
          title="物质申请"
          :visible.sync="applyVisible"
          width="40%"
          custom-class="min-w-450"
      >
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请物资:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入物质名称"
              v-model="applyForm.applyThing"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请数量:</p>
          <el-input-number
              v-model="applyForm.applyNum"
              :precision="2"
              :step="0.1"
              :max="10"
              style="width: 350px;"
              placeholder="请输入申请数量"
              clearable
          ></el-input-number>

        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请单位:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入物质单位"
              v-model="applyForm.thingUnit"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">紧急程度:</p>
          <el-select style="width: 350px;" v-model="applyForm.emergencyLevel" placeholder="请选择紧急程度">
            <el-option v-for="item in emergencyLevelOptions" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请备注:</p>
          <el-input
              style="width: 350px;"
              type="textarea"
              placeholder="请输入备注"
              v-model="applyForm.applyRemark"
              maxlength="70"
              show-word-limit
          >
          </el-input>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="applyVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmApply">确 定</el-button>
        </span>

      </el-dialog>       -->

    </div>
  </div>
</template>

<script>
import { get, post } from "../utils/request";
import TopNav from "../components/TopNav";
import Footer from "../components/Footer.vue";

export default {
  name: "RecordList",
  components: {
    TopNav,
    Footer,
  },
  data() {
    return {
      checkVisible: false,
      applyVisible: false,
      // orderList: [],
      isolationList: [],
      applyList: [],
      pageNum: 0,
      isolationNum: 0,
      // pageNum: 0,
      statusList: [],
      isIsolation: true,
      isApply: false,
      applyForm: {
        applyThing: "",
        applyNum: 0,
        thingUnit: "",
        emergencyLevel: "",
        applyRemark: "",
      },
      emergencyLevelOptions: [
        {
          value: 0,
          label: "不紧急",
        },
        {
          value: 1,
          label: "普通紧急",
        },
        {
          value: 2,
          label: "非常紧急",
        },
      ],
      statusTextList: ["未支付", "已支付", "已关闭"],
      check: {
        name: '',
        checkType: '',
        checkRes: '',
        checkTime: '',
      }
    };
  },
  filters: {
    applyStatusFilter(value) {
      const lang = localStorage.getItem("lang");
      if (lang == "zh" || lang == null) {
        switch (value) {
          case 0:
            value = "未审核";
            break;
          case 1:
            value = "已同意";
            break;
          case 2:
            value = "已拒绝";
            break;
          default:
            value = "已拒绝";
            break;
        }
        return value;
      } else if (lang == "en") {
        switch (value) {
          case 0:
            value = "Not reviewed";
            break;
          case 1:
            value = "Approved";
            break;
          case 2:
            value = "Rejected";
            break;
          default:
            value = "Rejected";
            break;
        }
        return value;
      }
    },
    isolationStatusFilter(value) {
      const lang = localStorage.getItem("lang");
      if (lang == "zh" || lang == null) {
        switch (value) {
          case 0:
            value = "正在隔离";
            break;
          case 1:
            value = "解除隔离";
            break;
          case 2:
            value = "阳性入院";
            break;
          default:
            value = "阳性入院";
            break;
        }
        return value;
      } else if (lang == "en") {
        switch (value) {
          case 0:
            value = "Isolating";
            break;
          case 1:
            value = "Free";
            break;
          case 2:
            value = "Positive";
            break;
          default:
            value = "Positive";
            break;
        }
        return value;
      }
    },
    statusFilter(value) {
      const lang = localStorage.getItem("lang");
      if (lang == "zh" || lang == null) {
        switch (value) {
          case "0":
            value = "未支付";
            break;
          case "1":
            value = "已支付";
            break;
          case "2":
            value = "已关闭";
            break;
          case "3":
            value = "已入住";
            break;
          default:
            value = "已完成";
            break;
        }
        return value;
      } else if (lang == "en") {
        switch (value) {
          case "0":
            value = "Unpaid";
            break;
          case "1":
            value = "Paid";
            break;
          case "2":
            value = "Closed";
            break;
          case "3":
            value = "Checked in";
            break;
          default:
            value = "Completed";
            break;
        }
        return value;
      }
    },
    roomNameFormat(val) {
      const lang = localStorage.getItem("lang");
      if (lang == "zh") {
        return val;
      } else if (lang == "en") {
        if (val == "单人房") {
          return (val = "Single-bed room");
        } else if (val == "豪华大床房") {
          return (val = "Deluxe single Room");
        } else if (val == "双人房") {
          return (val = "Double room");
        } else if (val == "总统套房") {
          return (val = "Presidential suite");
        } else if (val == "经济房") {
          return (val = "Economy Room");
        } else {
          return val;
        }
      }
    },
  },
  mounted() {
    this.getIsolationRecords()
  },
  methods: {
    // 隔离记录 Start
    // 点击隔离记录大按钮函数
    isolationRecordsHandle(val) {
      this.isIsolation = val;
      if (val) {
        this.isApply = false;
        this.getIsolationRecords();
      }
    },
    // 页面渲染
    getIsolationRecords() {
      let data = {
        page: {
          page: 1,
          size: 10,
        },
      };
      this.isolationRecordsRequest(data);
    },
    //获取隔离列表请求
    isolationRecordsRequest(data) {
      post("/api/isolationInfo/my", data)
        .then((res) => {
          console.log(res);
          this.isolationList = res.data.data.records;
          this.isolationNum = res.data.data.total;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //选择页码
    isolationCurrent(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.isolationRecordsRequest(data)
    },

    //上一页
    isolationPrev(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.isolationRecordsRequest(data)
    },

    //下一页
    isolationNext(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.isolationRecordsRequest(data)
    },     

    // 获取检测记录函数
    getCheckRecords(id) {
      let data = {
        uId: id,
        page: {
          page: 1,
          size: 10,
        },
      };
      post("api/checkRecords/ownPage", data)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.error(err);
        });
    },

    // 物资申请函数，打开弹出框
    applyHandle() {
      this.applyVisible = true;
      this.applyForm = {
        applyThing: "",
        applyNum: 0,
        thingUnit: "",
        emergencyLevel: "",
        applyRemark: "",
        uType: 1,
      };
    },    

    // 弹出框申请物资确认按钮函数
    confirmApply() {
      let data = this.applyForm;
      post("api/materialsApply/apply", data).then((res) => {
        if (res.data.code === "200") {
          this.$notify.success({
            title: "成功",
            message: "申请成功",
          });
          this.applyVisible = false;
        } else {
          this.$message({
            message: res.data.msg,
            type: "error",
          });
        }
      });
    },
    // 隔离记录 End

    // 点击申请记录大按钮函数
    applyRecordsHandle(val) {
      this.isApply = val;
      if (val) {
        this.isIsolation = false;
        this.getApplyRecords();
      }
    },

    // 获取申请记录函数
    getApplyRecords() {
      let data = {
        page: {
          page: 1,
          size: 10,
        },
      };
      post("api/materialsApply/page", data)
        .then( res => {
          this.applyList = res.data.data.records;
          this.pageNum = res.data.data.total;
        })
    },   

  },
};
</script>

<style scoped>
.record-list-main {
  height: calc(100vh - 220px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #ecf5ff;
}

.t-nav {
  width: 100%;
  height: 68px;
  background: #fff;
  opacity: 0.8;
  z-index: 999;
  position: fixed;
  top: 0;
  left: 0;
}

.nav-content {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  height: 100%;
}

.nav-content ul {
  display: flex;
  align-items: center;
  height: 100%;
}

.nav-content li {
  font-size: 18px;
  margin: 0 30px;
}

.nav-bt {
  width: 123px;
  height: 34px;
  line-height: 34px;
  background: #28b535;
  border-radius: 16px;
  border: 0;
  color: #fff;
}

.nav-content a {
  color: #a1a09e;
}

.nav-content a:hover {
  color: #323230;
}

.hotel-list-bg {
  width: 100%;
  height: 220px;
}

.hotel-list-bg img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 220px;
}

.hotel-list-bg h3 {
  margin-bottom: 25px;
  font-size: 36px;
  font-weight: 400;
  color: #ffffff;
}

.hotel-list-content {
  display: flex;
  justify-content: center;
  padding-top: 30px;
}

.hotel-list {
}

.hotel-list li {
  display: flex;
  margin-bottom: 30px;
}

.hotel-list li .li-l {
  width: 370px;
  height: 423px;
  margin: 0px 30px;
}

.hotel-list li .li-l img {
  width: 100%;
  height: 100%;
}

.hotel-list li .li-r {
  width: 360px;
  margin: 0px 30px;
  position: relative;
}

.hotel-list li .li-r h4 {
  font-size: 36px;
  margin-bottom: 15px;
  font-weight: 400;
}

.hotel-list li .li-r h6 {
  font-size: 14px;
  margin-bottom: 28px;
  font-weight: 400;
}

.hotel-list li .li-r div {
  font-size: 15px;
  color: #898989;
  margin-bottom: 20px;
  overflow: auto;
  max-height: 206px;
}

.hotel-list li .li-r button {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 118px;
  height: 38px;
  line-height: 38px;
  background: #e1bd85;
  font-size: 12px;
  color: #ffffff;
  border-radius: 5px;
  border: none;
}

.hotel-list li:nth-child(even) .li-l {
  order: 2;
}

.record-list-content {
  /* margin: 20px auto 0px auto;
  width: 80vw; */

  padding: 30px 15%;
  background-color: #ecf5ff;
}

.order-status {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  border-radius: 15px;
  flex: 1;
  height: 120px;
  margin: 0px 10px;
  padding: 0px 20px;
}

.order-status > img {
  width: 60px;
  height: 60px;
  margin-right: 22px;
}

.order-status > div {
  display: flex;
  flex-direction: column;
  height: 60px;
  justify-content: space-between;
}

.order-list {
  margin: 20px 10px;
}

.order-list li {
  padding: 25px;
  margin-bottom: 10px;
  background-color: white;
  border-radius: 15px;
  flex: 1;
}

.el-pagination.is-background .btn-next,
.el-pagination.is-background .btn-prev,
.el-pagination.is-background .el-pager li {
  background-color: #ffffff !important;
}

.active {
  border: 1px solid #d2d2d3;
  background: #f5f7fa;
}
</style>