<template>

  <div style="min-height: 100vh;">

    <!-- 导航栏 -->
    <top-nav></top-nav>

    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="">
    </div>

    <div class="record-list-main">

      <div class="order-list-content">

        <div class="d-flex align-items-center justify-content-between">
          <div v-for="(item,index) in statusList" class="order-status">
            <img :src="require(`../assets/images/status_${item.status}.png`)" alt="">
            <div>
              <h3 class="font-22">{{ item.status | statusFilter }}</h3>
              <p class="font-16">{{ item.counts }}</p>
            </div>
          </div>

          <div :class="isIsolation  ? 'isolation' : ''" class="order-status cursor"
               @click="isolationRecordsHandle(!isIsolation)">
            <div>
              <h3 class="font-22">隔离记录</h3>
              <p class="font-16">12</p>
            </div>
          </div>
          <div :class="isApply  ? 'isolation' : ''" class="order-status cursor"
               @click="applyRecordsHandle(!isApply)">
            <div>
              <h3 class="font-22">申请记录</h3>
              <p class="font-16">12</p>
            </div>
          </div>
        </div>


        <!--        隔离记录-->
        <ul class="order-list" v-if="isIsolation">
          <li v-for="(item,index) in isolationList">

            <div class="d-flex align-items-center justify-content-between mb-15 font-20">
              <p>{{ item.status | isolationStatusFilter }}</p>
              <div>
                <el-button v-if="item.status == 0" @click="getCheckRecords(item.id)">检测记录</el-button>
                <el-button v-if="item.status == 0" type="primary" @click="applyHandle">物质申请</el-button>
              </div>
            </div>
            <div class="d-flex align-items-center justify-content-between font-16 color-6">
              <div>
                <p class="mb-10">{{ $t('orderList.hotelName') }}{{ item.hotelName }}</p>
                <p class="mb-10">隔离ID：{{ item.id }}</p>
                <div class="d-flex align-items-center">
                  <p>{{ $t('orderList.roomType') }}{{ item.roomTypeName | roomNameFormat }}</p>
                  <p class="ml-10 mr-10" style="color: #e0e0e0"> | </p>
                  <p>{{ $t('isolationList.roomName') }}{{ item.roomName }} </p>
                </div>
              </div>
              <p v-if="item.pay">{{ $t('orderList.spend') }}￥{{ item.pay }} </p>
            </div>
          </li>
        </ul>

        <!--        申请记录-->
        <ul class="order-list" v-else-if="isApply">
          <li v-for="(item,index) in applyList">

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
              <p v-if="item.pay">{{ $t('orderList.spend') }}￥{{ item.pay }} </p>
            </div>
          </li>
        </ul>

        <ul class="order-list" v-else>
          <li v-for="(item,index) in orderList">
            <div class="d-flex align-items-center justify-content-between mb-15 font-20">
              <p>{{ item.status | statusFilter }}</p>
              <a v-if="item.status == 1" class="color-red cursor"
                 @click="cancelOrder(item.id)">{{ $t('orderList.cancelOrder') }}</a>
            </div>
            <div class="d-flex align-items-center justify-content-between font-16 color-6">
              <div>
                <p class="mb-10">{{ $t('orderList.hotelName') }}{{ item.hotelName }}</p>
                <p class="mb-10">订单ID：{{ item.id }}</p>
                <div class="d-flex align-items-center">
                  <p>{{ $t('orderList.roomType') }}{{ item.roomTypeName | roomNameFormat }}</p>
                  <p class="ml-10 mr-10" style="color: #e0e0e0"> | </p>
                  <p>{{ $t('orderList.day') }} {{ item.days }} {{ $t('orderList.dayNum') }}</p>
                </div>
              </div>
              <p>{{ $t('orderList.spend') }}￥{{ item.lastPay }} </p>
            </div>
          </li>
        </ul>

        <div v-if="pageNum > 5" class="d-flex align-items-center justify-content-center">
          <el-pagination
              background
              @current-change="handleCurrentChange"
              @prev-click="prevPage"
              @next-click="nextPage"
              layout="prev, pager, next"
              :total="pageNum">
          </el-pagination>
        </div>

      </div>

        <!-- 暂无订单，改为暂无记录 -->
        <!-- <div v-show="orderList.length == 0" class="order-null text-center font-30">{{ $t('orderList.noOrder') }}</div> -->


        <Footer></Footer>

    </div>

    


  </div>

</template>

<script>
import {get, post} from "../utils/request";
import TopNav from '../components/TopNav'
import Footer from '../components/Footer.vue';

export default {
    name: "RecordList",
    components: {
        TopNav,
        Footer
    },
    data() {
        return {

        }
    },
    mounted() {},
    methods: {

    }
}
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
  background: #28B535;
  border-radius: 16px;
  border: 0;
  color: #fff;
}

.nav-content a {
  color: #A1A09E;
}

.nav-content a:hover {
  color: #323230
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
  color: #FFFFFF;
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
  background: #E1BD85;
  font-size: 12px;
  color: #FFFFFF;
  border-radius: 5px;
  border: none;
}

.hotel-list li:nth-child(even) .li-l {
  order: 2;
}

.order-list-content {
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
  margin: 20px 10px
}

.order-list li {
  padding: 25px;
  margin-bottom: 10px;
  background-color: white;
  border-radius: 15px;
  flex: 1;
}

.el-pagination.is-background .btn-next, .el-pagination.is-background .btn-prev, .el-pagination.is-background .el-pager li {
  background-color: #ffffff !important;
}

.isolation {
  border: 1px solid #d2d2d3;
  background: #f5f7fa;
}
</style>