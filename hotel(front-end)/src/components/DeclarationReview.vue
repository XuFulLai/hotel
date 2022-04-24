<template>

  <div class="review-main">

        <div class="d-flex justify-content-between" style="padding: 10px;">
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
                <el-button type="primary" icon="el-icon-search" @click="getReviewList">查询</el-button>
                <el-button type="info" icon="el-icon-refresh" @click="reset">重置</el-button>
            </el-button-group>
        </div>

        <!-- 表格 -->
        <el-table
            stripe
            border
            :data="reviewData"
            style="width: 100%">
            <el-table-column
                align="center"
                prop="name"
                label="申请人姓名">
            </el-table-column>
            <el-table-column
                align="center"
                prop="idCard"
                label="身份证号码">
            </el-table-column>
            <el-table-column
                align="center"
                prop="phone"
                label="手机号码">
            </el-table-column>
            <el-table-column
                align="center"
                prop="type"
                label="隔离人员类型">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.type==0" type="danger">密接</el-tag>
                    <el-tag v-if="scope.row.type==1" type="warning">应隔离人员</el-tag>
                    <el-tag v-if="scope.row.type==2" type="info">入境人员</el-tag>
                    <el-tag v-if="scope.row.type==3" >中高风险地区人员</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                align="center"
                prop="checkInTime"
                :formatter="eTableDateTime"
                label="入住时间">
            </el-table-column>
            <el-table-column
                align="center"
                prop="checkOutTime"
                :formatter="eTableDateTime"
                label="离开时间">
            </el-table-column>                        
            <el-table-column
                label="操作"
                width="240"
                align="center">
            <template slot-scope="scope">
                <el-button
                    size="mini"
                    @click="handleCheck(scope.$index, scope.row)">查看
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
            <p class="w-100 text-left">隔离人员类型:</p>
            <el-tag v-if="form.type==0" type="danger">密接</el-tag>
            <el-tag v-if="form.type==1" type="warning">应隔离人员</el-tag>
            <el-tag v-if="form.type==2" type="info">入境人员</el-tag>
            <el-tag v-if="form.type==3" >中高风险地区人员</el-tag>          
        </div>           
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">申请人姓名:</p>
          <el-input
              style="width: 350px;"
              placeholder="申请人姓名"
              v-model="form.name"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">身份证号码:</p>
          <el-input
              style="width: 350px;"
              placeholder="身份证号码"
              v-model="form.idCard"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">手机号码:</p>
          <el-input
              style="width: 350px;"
              placeholder="手机号码"
              v-model="form.phone"
              disabled
              clearable>
          </el-input>
        </div>       
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">入住时间:</p>
          <el-input
              style="width: 350px;"
              placeholder="入住时间"
              v-model="form.checkInTime"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">离开时间:</p>
          <el-input
              style="width: 350px;"
              placeholder="离开时间"
              v-model="form.checkOutTime"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">拒绝理由:</p>
          <el-input
              type="textarea"
              :rows="2"
              style="width: 350px;"
              placeholder="请输入拒绝理由"
              :disabled="!form.switch"
              v-model="form.textarea">
          </el-input>
          <el-switch
            class="ml-10"
            v-model="form.switch">
          </el-switch>
        </div>     
      </div>
      <span slot="footer" class="dialog-footer d-flex justify-content-between">
          <div>
            <el-button @click="prev">上一条</el-button>
            <el-button @click="next">下一条</el-button>
          </div>

          <div>              
            <el-button type="danger" @click="refuse">拒 绝</el-button>
            <el-button type="primary" @click="confirm">同 意</el-button>
          </div>
    </span>
    </el-dialog>        

  </div>

  

</template>

<script>
import { post } from "../utils/request";
import { dateFormat } from "../utils/dateTime";

export default {
    name: "DeclarationReview",
    data() {
        return {
            reviewData: [], // 列表数据
            searchParams: { // 筛选框数据
                dateRange: '',
                beginTime: '',
                endTime: '',
                textarea: '',
                switch: false,
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
    watch: {
        "searchParams.dateRange"(val, oldVal){
            if (val) {
                this.searchParams.beginTime = val[0]
                this.searchParams.endTime = val[1]
            }

        },
    },    
    mounted() {
        this.getReviewList()
    },
    methods: {
        // 页面初次渲染函数
        getReviewList() {
            const data = {
                page: {
                    page: 1,
                    size: 10
                },
                beginTime: this.searchParams.beginTime,
                endTime: this.searchParams.endTime,                
            }
            this.page = 1
            this.reviewListRequest(data)
        },

        // 列表获取函数
        reviewListRequest(data) {
            post('/api/review/page',data)
                .then( res => {
                    console.log(res);
                    this.reviewData = res.data.data.records
                    this.pageNum = res.data.data.total

                    this.currentIndex = 0
                    let row = this.reviewData[this.currentIndex]
                    this.form = row
                })
                .catch( err => {
                    console.error(err);
                })
        },

        //选择页码
        handleCurrentChange(num) {
            console.log(this.page);
            console.log(num);
            const data = {
                page: {
                    page: num,
                    size: 10
                },
                beginTime: this.searchParams.beginTime,
                endTime: this.searchParams.endTime,
            }
            this.reviewListRequest(data)
        },

        //上一页
        prevPage(num) {
            console.log(this.page);
            console.log(num);            
            const data = {
                page: {
                    page: num,
                    size: 10
                },
                beginTime: this.searchParams.beginTime,
                endTime: this.searchParams.endTime,
            }
            this.reviewListRequest(data)
        },

        //下一页
        nextPage(num) {
            console.log(this.page);
            console.log(num);     
            const data = {
                page: {
                    page: num,
                    size: 10
                },
                beginTime: this.searchParams.beginTime,
                endTime: this.searchParams.endTime,
            }
            this.reviewListRequest(data)
        },

        // 
        handleCheck(index, row) {
            console.log(index);
            console.log(row);
            this.currentIndex = index
            this.form = row
            this.form.checkInTime = dateFormat(this.form.checkInTime)
            this.form.checkOutTime = dateFormat(this.form.checkOutTime)
            this.dialogVisible = true
            
        },

        // 上一条
        prev(){
            this.currentIndex--
            const row = this.reviewData[this.currentIndex]
            console.log(row);
            if (row) {
                this.form = row
            } else if (this.currentIndex == -1 && row == undefined) {
                this.page--
                this.prevPage(this.page)
                this.currentIndex = 9
                row = this.reviewData[this.currentIndex]
                this.form = row
            } else {
                this.$message({
                    message: '已经是第一条了！',
                    type: 'warning',
                    duration: 2000
                });
                return                
            }            
        },

        // 下一条
        next() {
            this.currentIndex++
            let row = this.reviewData[this.currentIndex]
            console.log(row);
            if (row) {
                this.form = row
            } else if (this.currentIndex == 10 && row == undefined) {
                this.page++
                this.nextPage(this.page)
                this.currentIndex = 0
                row = this.reviewData[this.currentIndex]
                this.form = row
            } else {
                this.$message({
                    message: '已经是最后一条了！',
                    type: 'warning',
                    duration: 2000
                });
                return                
            }
        },

        // 同意
        confirm() {
            const data = {
                id: this.form.id,
                reviewStatus: 1,
            }
            post('/api/review/reviews', data)
                .then( res => {
                    console.log(res);
                })
                .catch( err => {
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
            post('/api/review/reviews', data)
                .then( res => {
                    console.log(res);
                })
                .catch( err => {
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

        // 日期时间格式化
        eTableDateTime(row, column, cellValue, index){
            const dateTime = new Date(cellValue) // Date实例

            const YYYY = dateTime.getFullYear() // 获取当前年份
            const MM = dateTime.getMonth() + 1 // 获取当前月份
            const DD = dateTime.getDate() // 获取当前天数
            const hh = this.fillPrefix(dateTime.getHours()) // 获取当前小时，并判断是否需要补零
            const mm = this.fillPrefix(dateTime.getMinutes()) // 获取当前分钟，并判断是否需要补零
            const ss = this.fillPrefix(dateTime.getSeconds()) // 获取当前秒数，并判断是否需要补零
            // 返回格式化之后的当前时间
            // return `${YYYY}-${MM}-${DD} ${hh}:${mm}:${ss}`
            return `${YYYY}-${MM}-${DD}`
        },

        // 补零函数
        fillPrefix(val){
            return val > 9 ? val : `0${val}` // 个位数时间进行补零操作
        }

    }

}
</script>

<style scoped>
.review-main {
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; */
  width: 100%;
  min-width: 1000px;
  height: 100%;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}
.search {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 0 10px;
  font-size: 14px;
  white-space: nowrap;
}
</style>