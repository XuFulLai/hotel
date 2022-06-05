<template>

  <div class="material-main">

    <!-- <div class="d-flex justify-content-between" style="padding: 10px;">
        <div class="block search">
            <span class="demonstration">日期：</span>
            <el-date-picker
                v-model="searchParams.dateRange"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
            </el-date-picker>
        </div>
        <el-button-group class="d-flex justify-content">
            <el-button type="primary" icon="el-icon-search" @click="getMaterialList">查询</el-button>
            <el-button type="info" icon="el-icon-refresh" @click="reset">重置</el-button>
        </el-button-group>
    </div> -->

    <!-- 表格 -->
    <el-table
        stripe
        border
        :data="materialData"
        class="global-table"
        style="width: 100%;overflow: auto;">
      <el-table-column
          align="center"
          prop="applyThing"
          label="申请物品">
      </el-table-column>
      <el-table-column
          align="center"
          prop="applyNum"
          label="申请数量">
      </el-table-column>
      <el-table-column
          align="center"
          prop="thingUnit"
          label="申请单位">
      </el-table-column>
      <el-table-column
          align="center"
          prop="emergencyLevel"
          label="紧急程度">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.emergencyLevel==0" type="success">不紧急</el-tag>
          <el-tag v-if="scope.row.emergencyLevel==1" type="warning">普通紧急</el-tag>
          <el-tag v-if="scope.row.emergencyLevel==2" type="danger">非常紧急</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          prop="applyRemarks"
          label="申请备注">
      </el-table-column>
      <el-table-column
          align="center"
          prop="reviewStatus"
          label="审核状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.reviewStatus==0" type="warning">待审核</el-tag>
          <el-tag v-if="scope.row.reviewStatus==1" type="success">审核通过</el-tag>
          <el-tag v-if="scope.row.reviewStatus==2" type="danger">审核拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="操作"
          width="240"
          align="center">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleRead(scope.$index, scope.row)">查看
          </el-button>
          <el-button
              size="mini"
              type="danger"
              :disabled="scope.row.reviewStatus != 0"
              @click="handleCheck(scope.$index, scope.row)">审核
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination
        class="mt-10 mb-10"
        background
        @current-change="handleCurrentChange"
        @prev-click="prevPage"
        @next-click="nextPage"
        layout="prev, pager, next"
        :total="pageNum">
    </el-pagination>

    <!-- 弹出框 -->
    <el-dialog
        title="审核详情"
        :visible.sync="dialogVisible"
        width="570px">
      <div class="content">
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">紧急程度:</p>
          <el-tag v-if="form.emergencyLevel==0" type="danger">紧急</el-tag>
          <el-tag v-if="form.emergencyLevel==1" type="warning">应隔离人员</el-tag>
          <el-tag v-if="form.emergencyLevel==2" type="info">入境人员</el-tag>
          <el-tag v-if="form.emergencyLevel==3">中高风险地区人员</el-tag>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请物品:</p>
          <el-input
              style="width: 350px;"
              placeholder="申请物品"
              v-model="form.applyThing"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请数量:</p>
          <el-input
              style="width: 350px;"
              placeholder="申请数量"
              v-model="form.applyNum"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请单位:</p>
          <el-input
              style="width: 350px;"
              placeholder="申请单位"
              v-model="form.thingUnit"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请备注:</p>
          <el-input
              style="width: 350px;"
              placeholder="申请备注"
              v-model="form.applyRemarks"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">审核说明:</p>
          <el-input
              type="textarea"
              :rows="2"
              style="width: 350px;"
              placeholder="请输入审核说明"
              :disabled="currentCheckStatus == 'readOnly' || form.reviewStatus != 0"
              v-model="form.textarea">
          </el-input>
<!--          <el-switch-->
<!--              class="ml-10"-->
<!--              v-model="form.switch">-->
<!--          </el-switch>-->
        </div>
      </div>
      <span slot="footer" class="dialog-footer d-flex justify-content-between">
          <div>
            <!-- <el-button @click="prev">上一条</el-button>
            <el-button @click="next">下一条</el-button> -->
          </div>

          <div v-if="currentCheckStatus == 'check'">
            <el-button :disabled="form.reviewStatus != 0" type="danger" @click="refuse">拒 绝</el-button>
            <el-button :disabled="form.reviewStatus != 0" type="primary" @click="confirm">同 意</el-button>
          </div>
    </span>
    </el-dialog>

  </div>

</template>

<script>
import {post} from "../utils/request";
import {dateFormat} from "../utils/dateTime";

export default {
  name: "MaterialReview",
  data() {
    return {
      currentCheckStatus:"readOnly",
      materialData: [], // 列表数据
      searchParams: { // 筛选框数据
        dateRange: '',
        beginTime: '',
        endTime: '',
      },
      pageNum: 0,
      dialogVisible: false,
      form: {
        textarea: '',
        switch: false,
      },
      currentIndex: '',
      page: ''
    }
  },
  // watch: {
  //     "searchParams.dateRange"(val, oldVal){
  //         if (val) {
  //             this.searchParams.beginTime = val[0]
  //             this.searchParams.endTime = val[1]
  //         }

  //     },
  // },
  mounted() {
    this.getMaterialList()
  },
  methods: {
    // 页面初次渲染函数
    getMaterialList() {
      const data = {
        page: {
          page: 1,
          size: 10
        },
        // beginTime: this.searchParams.beginTime,
        // endTime: this.searchParams.endTime,
      }
      this.materialListRequest(data)
    },

    // 列表获取函数
    materialListRequest(data) {
      post('/api/materialsApply/page', data)
          .then(res => {
            console.log(res);
            this.materialData = res.data.data.records
            this.pageNum = res.data.data.total
          })
          .catch(err => {
            console.error(err);
          })
    },

    //选择页码
    handleCurrentChange(num) {
      const data = {
        page: {
          page: num,
          size: 10
        },
        // beginTime: this.searchParams.beginTime,
        // endTime: this.searchParams.endTime,
      }
      this.materialListRequest(data)
    },

    //上一页
    prevPage(num) {
      const data = {
        page: {
          page: num,
          size: 10
        },
        // beginTime: this.searchParams.beginTime,
        // endTime: this.searchParams.endTime,
      }
      this.materialListRequest(data)
    },

    //下一页
    nextPage(num) {

      const data = {
        page: {
          page: num,
          size: 10
        },
        // beginTime: this.searchParams.beginTime,
        // endTime: this.searchParams.endTime,
      }
      this.materialListRequest(data)
    },

    handleRead(index, row) {
      this.currentCheckStatus = 'readOnly'
      console.log(index);
      console.log(row);
      this.currentIndex = index
      this.form = row
      this.dialogVisible = true
    },
    // 查看详情
    handleCheck(index, row) {
      this.currentCheckStatus = 'check'
      console.log(index);
      console.log(row);
      this.currentIndex = index
      this.form = row
      this.dialogVisible = true

    },

    // 同意
    confirm() {
      const data = {
        id: this.form.id,
        reviewStatus: 1,
        remark: this.form.textarea
      }
      post('/api/materialsApply/review', data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: '成功',
                type: 'success',
                duration: 2000
              });          
            }     
            this.dialogVisible = false

          })
          .catch(err => {
            console.error(err);
          })
    },

    // 拒绝
    refuse() {
      if (!this.form.textarea) {
        this.$message({
          message: '请输入拒绝理由！',
          type: 'warning',
          duration: 2000
        });
        return
      }
      const data = {
        id: this.form.id,
        reviewStatus: 2,
        remark: this.form.textarea
      }
      post('/api/materialsApply/review', data)
          .then(res => {
            console.log(res);
          })
          .catch(err => {
            console.error(err);
          })
    },

    // 重置函数
    reset() {
      this.searchParams = {
        dateRange: '',
        beginTime: '',
        endTime: '',
      }
    },

  }
}
</script>

<style scoped>
.material-main {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}
</style>