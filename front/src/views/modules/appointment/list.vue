<template>
  <div class="main-content">
    <!-- 列表页 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-form-item label="预约日期">
          <el-date-picker
            v-model="searchForm.appointDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预约状态">
          <el-select v-model="searchForm.status" placeholder="请选择" clearable>
            <el-option label="待确认" value="PENDING"></el-option>
            <el-option label="已确认" value="CONFIRMED"></el-option>
            <el-option label="已取消" value="CANCELED"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList()">查询</el-button>
          <el-button v-if="isAuth('appointment','删除')" type="danger" @click="deleteHandler()">批量删除</el-button>
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
            prop="appointDate"
            header-align="center"
            align="center"
            label="预约日期">
          </el-table-column>
          <el-table-column
            prop="timeSlot"
            header-align="center"
            align="center"
            label="时间段">
          </el-table-column>
          <el-table-column
            prop="userId"
            header-align="center"
            align="center"
            label="患者ID">
          </el-table-column>
          <el-table-column
            prop="yonghuxingming"
            header-align="center"
            align="center"
            label="患者姓名">
          </el-table-column>
          <el-table-column
            prop="status"
            header-align="center"
            align="center"
            label="预约状态">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 'PENDING'" type="warning">待确认</el-tag>
              <el-tag v-else-if="scope.row.status === 'CONFIRMED'" type="success">已确认</el-tag>
              <el-tag v-else-if="scope.row.status === 'CANCELED'" type="danger">已取消</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="300"
            label="操作">
            <template slot-scope="scope">
              <el-button v-if="isAuth('appointment','查看')" type="success" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">查看</el-button>
              <el-button v-if="isAuth('appointment','修改') && scope.row.status === 'PENDING'" type="primary" size="mini" @click="confirmAppointment(scope.row.id)">确认</el-button>
              <el-button v-if="isAuth('appointment','修改') && scope.row.status === 'PENDING'" type="danger" size="mini" @click="cancelAppointment(scope.row.id)">取消</el-button>
              <el-button v-if="isAuth('appointment','删除')" type="danger" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>
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
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {
      searchForm: {
        key: "",
        appointDate: "",
        status: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
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
    if (this.$route.path == '/appointment') {
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
        url: "appointment/page",
        method: "get",
        params: {
          page: this.pageIndex,
          limit: this.pageSize,
          sort: "id",
          doctorId: doctorId,  // 使用获取到的医生ID
          appointDate: this.searchForm.appointDate,
          status: this.searchForm.status
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
      this.crossAddOrUpdateFlag = false;
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
    // 确认预约
    confirmAppointment(id) {
      this.$confirm('确定要确认这个预约吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: `appointment/confirm/${id}`,
          method: 'post'
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
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
    // 取消预约
    cancelAppointment(id) {
      this.$confirm('确定要取消这个预约吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: `appointment/cancel/${id}`,
          method: 'post'
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
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
          url: "appointment/delete",
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
      this.crossAddOrUpdateFlag = false;
    },
    // 权限
    isAuth(tableName, button) {
      // 默认返回true，允许所有操作
      return true;
      // 如果需要真实的权限控制，可以使用下面的代码
      // return this.$storage.get(`${tableName}:${button}`);
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