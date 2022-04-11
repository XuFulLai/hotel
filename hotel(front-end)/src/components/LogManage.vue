<template>

  <div class="log-main">

    <div class="d-flex justify-content" style="padding: 10px">
      <div class="block search">
        <span class="demonstration">日期：</span>
        <el-date-picker
          v-model="searchParams.dateRange"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </div>
      <div class="block search">
        <p>日志类型：</p>
        <el-select v-model="searchParams.type" placeholder="请选择">
          <el-option
            v-for="item in Optioner"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="block search">
        <p>操作人：</p>
        <el-input v-model="searchParams.Optioner" placeholder=""></el-input>
      </div>
      <div class="block search">
        <p>日志内容：</p>
        <el-input v-model="searchParams.logContent" placeholder=""></el-input>
      </div>
      <el-button-group class="d-flex justify-content">
        <el-button type="primary" icon="el-icon-search" @click="getLogList">查询</el-button>
        <el-button type="info" icon="el-icon-refresh" @click="reset">重置</el-button>
      </el-button-group>
    </div>

    <div class="d-flex justify-content-end" style="padding: 10px">
      <el-button type="success" style="width: 120px" @click="download">导出成Excel</el-button>
    </div>

    <el-table
        stripe 
        border 
        :data="logData" 
        style="width: 100%">
      <el-table-column
        align="center" 
        prop="sketch" 
        label="日志内容">
      </el-table-column>
            <el-table-column
        align="center"
        prop="type" 
        label="日志类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == 0" type="info">普通日志</el-tag>
          <el-tag v-if="scope.row.type == 1">数据修改</el-tag>
          <el-tag v-if="scope.row.type == 2" type="success">系统修改</el-tag>
          <el-tag v-if="scope.row.type == 3" type="warning">异常日志</el-tag>
          <el-tag v-if="scope.row.type == 4" type="danger">警告日志</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center" 
        prop="createBy" 
        label="操作人">
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        label="创建时间"
      >
      </el-table-column>
      <el-table-column 
        label="操作" 
        width="100"
        align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleCheck(scope.$index, scope.row)"
            >查看
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
      :total="pageNum"
    >
    </el-pagination>

    <!-- 弹出框 -->
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="570px">

      <div class="contont">
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">用户名:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入用户名"
              v-model="lee"
              :disabled="true"
              clearable>
          </el-input>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>

    </el-dialog>

  </div>

</template>

<script>
import {get, post,reqFuc} from "../utils/request";


export default {
  name: "LogManage",
  data() {
    return {
        Optioner: [{
            value: 0,
            label: '普通日志'
        }, {
            value: 1,
            label: '数据修改'
        }, {
            value: 2,
            label: '系统修改'
        }, {
            value: 3,
            label: '异常日志'
        }, {
            value: 4,
            label: '警告日志'
        }],        
        searchParams: {
            dateRange: undefined,
            beginTime: undefined,
            endTime: undefined,
            type: undefined,
            Optioner: undefined,
            logContent: undefined,
        },
        pageNum: 0,
        logData: [],
        title: '',
        dialogVisible: false,
        lee: 'lee'
        
    };
  },
  mounted() {
      this.getLogList()
  },
    watch: {
    "searchParams.dateRange"(val, oldVal){//普通的watch监听

      if (val) {
        this.searchParams.beginTime = val[0]
        this.searchParams.endTime = val[1]
      }

    },
  },
  methods: {
    
    // 重置
    reset() {
        this.searchParams = {
            dateRange: undefined,
            beginTime: undefined,
            endTime: undefined,
            type: undefined,
            Optioner: undefined,
            logContent: undefined,            
        }
    },

    // 获取日志列表
    getLogList(){
        let data = {
            page: {
                page: 1,
                size: 10
            },
            type: this.searchParams.type,
            createBy: this.searchParams.Optioner,
            beginTime: this.searchParams.beginTime,
            endTime: this.searchParams.endTime,
            sketch: this.searchParams.logContent
        }
        this.logListRequest(data)
    },

    //获取日志列表的请求
    logListRequest(data){
        post('/api/logs/page', data)
            .then(res => {
                console.log(res);
                console.log(res.data.data.records);
                this.logData = res.data.data.records
                this.pageNum = res.data.data.total
            })
            .catch(err => {
                console.log(err);
            })
    },

    // 下载Excel表格
    download(){},

    // 选择页码
    handleCurrentChange(num) {
        let data = {
            page: {
                page: num,
                size: 10
            },
            type: this.searchParams.type,
            createBy: this.searchParams.Optioner,
            beginTime: this.searchParams.beginTime,
            endTime: this.searchParams.endTime,
            sketch: this.searchParams.logContent
        }
        this.logListRequest(data)
    },

    // 上一页
    prevPage(num) {
        let data = {
            page: {
                page: num,
                size: 10
            },
            type: this.searchParams.type,
            createBy: this.searchParams.Optioner,
            beginTime: this.searchParams.beginTime,
            endTime: this.searchParams.endTime,
            sketch: this.searchParams.logContent
        }
        this.logListRequest(data)
    },

    // 下一页
    nextPage(num) {
        let data = {
            page: {
                page: num,
                size: 10
            },
            type: this.searchParams.type,
            createBy: this.searchParams.Optioner,
            beginTime: this.searchParams.beginTime,
            endTime: this.searchParams.endTime,
            sketch: this.searchParams.logContent
        }
        this.logListRequest(data)
    },

    handleCheck(index, row) {
      console.log('log:',row);
      console.log('id:',row.id);
      this.dialogVisible = true
      this.title = '查看详情'
      get(`/api/logs/get/${row.id}`)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.error(err);
        })
    },    

    // 日期格式化函数
    dateFormatter(val){
        // console.log(val.createTime);
        var d = new Date(val.createTime);

        var year = d.getFullYear();       //年
        var month = d.getMonth() + 1;     //月
        var day = d.getDate();            //日

        var hh = d.getHours();            //时
        var mm = d.getMinutes();          //分
        var ss = d.getSeconds();           //秒

        var clock = year + "/";

        if (month < 10)
            clock += "0";

        clock += month + "/";

        if (day < 10)
            clock += "0";

        clock += day + " ";

        if (hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;
        return (clock);
    }


  },
};
</script>

<style scoped>
.log-main {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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