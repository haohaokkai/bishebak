<template>
  <div class="main-content">
    <!-- 列表页 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-form-item label="日期">
          <el-date-picker
            v-model="searchForm.workDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="时间段">
          <el-time-select
            v-model="searchForm.timeSlot"
            :picker-options="{
              start: '08:00',
              step: '01:00',
              end: '18:00'
            }"
            placeholder="选择时间段">
          </el-time-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList()">查询</el-button>
          <el-button type="primary" @click="addOrUpdateHandler()">新增</el-button>
          <el-button type="danger" @click="deleteHandler()" v-if="dataListSelections.length > 0">批量删除</el-button>
        </el-form-item>
      </el-form>
      
      <div class="table-content">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="selectionChangeHandler"
          style="width: 100%;">
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            width="50">
          </el-table-column>
          <el-table-column
            prop="id"
            header-align="center"
            align="center"
            width="80"
            label="ID">
          </el-table-column>
          <el-table-column
            prop="workDate"
            header-align="center"
            align="center"
            label="日期">
          </el-table-column>
          <el-table-column
            prop="timeSlot"
            header-align="center"
            align="center"
            label="时间段">
          </el-table-column>
          <el-table-column
            prop="maxPatients"
            header-align="center"
            align="center"
            label="最大预约人数">
          </el-table-column>
          <el-table-column
            prop="bookedCount"
            header-align="center"
            align="center"
            label="已预约人数">
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="200"
            label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">查看</el-button>
              <el-button type="primary" size="mini" @click="addOrUpdateHandler(scope.row.id)">修改</el-button>
              <el-button type="danger" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          style="margin-top: 5px; text-align: center;">
        </el-pagination>
      </div>
    </div>
    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update 
      v-if="addOrUpdateFlag" 
      :parent="this" 
      @refreshDataList="getDataList"
      ref="addOrUpdate">
    </add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {
      searchForm: {
        key: "",
        workDate: "",
        timeSlot: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      addOrUpdateFlag: false
    };
  },
  components: {
    AddOrUpdate
  },
  created() {
    this.getDataList();
  },
  activated() {
    if (this.$route.path == '/schedule') {
      this.getDataList();
    }
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      
      // 医生ID需要从登录信息中获取
      let doctorId = this.$storage.get("userid");
      
      console.log('当前医生ID:', doctorId); // 调试用
      
      this.$http({
        url: "schedule/page",
        method: "get",
        params: {
          page: this.pageIndex,
          limit: this.pageSize,
          sort: "id",
          doctorId: doctorId,  // 使用获取到的医生ID
          workDate: this.searchForm.workDate,
          timeSlot: this.searchForm.timeSlot
        }
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    // 添加/修改
    addOrUpdateHandler(id, type) {
      this.showFlag = false;
      this.addOrUpdateFlag = true;
      if (type != "info") {
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id);
        });
      } else {
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, "info");
        });
      }
    },
    // 删除
    deleteHandler(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map(item => {
            return item.id;
          });
      this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "schedule/delete",
          method: "post",
          data: ids
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 返回
    back() {
      this.showFlag = true;
      this.addOrUpdateFlag = false;
    }
  }
};
</script>

<style lang="scss" scoped>
.main-content {
  .form-content {
    background-color: #fff;
    padding: 10px;
    margin: 10px 0;
    border-radius: 6px;
  }
  .table-content {
    background-color: #fff;
    padding: 10px;
    border-radius: 6px;
  }
}
</style> 