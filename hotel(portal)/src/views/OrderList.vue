<template>
  <div style="min-height: 100vh;">

    <!-- 导航栏 -->
    <top-nav></top-nav>

    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="">
    </div>

    <div class="order-list-main">

      <div class="order-list-content">

        <div class="d-flex align-items-center justify-content-between">

          <div v-for="(item,index) in statusList" class="order-status">
            <img :src="require(`../assets/images/status_${item.status}.png`)" alt="">
            <div>
              <!-- <h3 class="font-22">{{ statusTextList[item.status] }}</h3> -->
              <h3 class="font-22">{{ item.status | statusFilter }}</h3>
              <p class="font-16">{{ item.counts }}</p>
            </div>
          </div>

        </div>

        <ul class="order-list">
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

      <div v-show="orderList.length == 0" class="order-null text-center font-30">{{ $t('orderList.noOrder') }}</div>


      <Footer></Footer>

    </div>


  </div>
</template>

<script>
import {get, post} from "../utils/request";
import TopNav from '../components/TopNav'
import Footer from '../components/Footer.vue';

export default {
  name: "OrderList",
  components: {
    TopNav,
    Footer
  },
  data() {
    return {
      applyVisible: false,
      orderList: [],
      isolationList: [],
      applyList: [],
      pageNum: 0,
      statusList: [],
      isIsolation: false,
      isApply: false,
      applyForm: {
        applyThing: '',
        applyNum: 0,
        thingUnit: '',
        emergencyLevel: '',
        applyRemark: '',
      },
      emergencyLevelOptions: [{
        value: 0,
        label: '不紧急'
      }, {
        value: 1,
        label: '普通紧急'
      }, {
        value: 2,
        label: '非常紧急'
      },],
      // statusTextList: [this.$t('orderList.status0'),this.$t('orderList.status1'),this.$t('orderList.status2')],
      statusTextList: ['未支付', '已支付', '已关闭']
    }
  },
  filters: {
    applyStatusFilter(value) {
      const lang = localStorage.getItem('lang')
      if (lang == 'zh' || lang == null) {
        switch (value) {
          case 0:
            value = '未审核'
            break;
          case 1:
            value = '已同意'
            break;
          case 2:
            value = '已拒绝'
            break;
          default:
            value = '已拒绝'
            break;
        }
        return value
      } else if (lang == 'en') {
        switch (value) {
          case 0:
            value = 'Not reviewed'
            break;
          case 1:
            value = 'Approved'
            break;
          case 2:
            value = 'Rejected'
            break;
          default:
            value = 'Rejected'
            break;
        }
        return value
      }
    },
    isolationStatusFilter(value) {
      const lang = localStorage.getItem('lang')
      if (lang == 'zh' || lang == null) {
        switch (value) {
          case 0:
            value = '正在隔离'
            break;
          case 1:
            value = '解除隔离'
            break;
          case 2:
            value = '阳性入院'
            break;
          default:
            value = '阳性入院'
            break;
        }
        return value
      } else if (lang == 'en') {
        switch (value) {
          case 0:
            value = 'Isolating'
            break;
          case 1:
            value = 'Free'
            break;
          case 2:
            value = 'Positive'
            break;
          default:
            value = 'Positive'
            break;
        }
        return value
      }
    },
    statusFilter(value) {
      const lang = localStorage.getItem('lang')
      if (lang == 'zh' || lang == null) {
        switch (value) {
          case '0':
            value = '未支付'
            break;
          case '1':
            value = '已支付'
            break;
          case '2':
            value = '已关闭'
            break;
          case '3':
            value = '已入住'
            break;
          default:
            value = '已完成'
            break;
        }
        return value
      } else if (lang == 'en') {
        switch (value) {
          case '0':
            value = 'Unpaid'
            break;
          case '1':
            value = 'Paid'
            break;
          case '2':
            value = 'Closed'
            break;
          case '3':
            value = 'Checked in'
            break;
          default:
            value = 'Completed'
            break;
        }
        return value
      }
    },
    roomNameFormat(val) {
      const lang = localStorage.getItem('lang')
      if (lang == 'zh') {
        return val
      } else if (lang == 'en') {
        if (val == '单人房') {
          return val = 'Single-bed room'
        } else if (val == '豪华大床房') {
          return val = 'Deluxe single Room'
        } else if (val == '双人房') {
          return val = 'Double room'
        } else if (val == '总统套房') {
          return val = 'Presidential suite'
        } else if (val == '经济房') {
          return val = 'Economy Room'
        } else {
          return val
        }
      }
    }
  },
  mounted() {
    this.getOrderList()
    this.getStatus()
  },
  methods: {
    getStatus() {
      const data = {
        page: {
          page: 1,
          size: 10
        }
      }
      post('/api/order/count', data)
          .then(res => {
            console.log(res);
            let resStatusList = res.data.data
            resStatusList.map(element => {
              let statusObj = {}
              statusObj.counts = element.counts
              statusObj.status = element.status
              this.statusList.push(statusObj)
              return resStatusList
            });
            console.log(resStatusList);
            console.log(this.statusList);
          })
          .catch(err => {
            console.log(err);
          })
    },
    getOrderList() {
      let data = {
        page: {
          page: 1,
          size: 10
        }
      }
      this.orderListRequest(data)
    },
    //获取订单列表请求
    orderListRequest(data) {
      post('/api/order/my', data)
          .then(res => {
            console.log(res);
            this.orderList = res.data.data.records
            this.pageNum = res.data.data.total
          })
          .catch(err => {
            console.log(err);
          })
    },

    //选择页码
    handleCurrentChange(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.orderListRequest(data)
      // window.scrollTo(0, 0)
    },

    //上一页
    prevPage(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.orderListRequest(data)
      // window.scrollTo(0, 0)
    },

    //下一页
    nextPage(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.orderListRequest(data)
      // window.scrollTo(0, 0)
    },

    cancelOrder(id) {
      get('/api/order/cancel/' + id)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: this.$t('orderList.success'),
                type: 'success'
              });
              this.$router.go(0)
            }
          })
          .catch(err => {
            console.log(err);
          })
    }
  }
}
</script>

<style scoped>
.order-list-main {
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