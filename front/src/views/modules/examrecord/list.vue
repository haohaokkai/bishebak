<template>
  <div class="main-content">
    <!-- 列表页 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row :gutter="20" class="slt">
          <el-form-item label="患者姓名">
            <el-input v-model="searchForm.username" placeholder="患者姓名" clearable></el-input>
          </el-form-item>
          <el-form-item label="测试模板">
            <el-input v-model="searchForm.papername" placeholder="测试模板名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="search()">查询</el-button>
          </el-form-item>
        </el-row>
      </el-form>

      <div class="table-content">
        <el-table :data="dataList" border v-loading="dataListLoading" style="width: 100%">
          <el-table-column prop="username" label="患者姓名" align="center"></el-table-column>
          <el-table-column prop="papername" label="测试模板" align="center"></el-table-column>
          <el-table-column prop="questionname" label="试题名称" align="center"></el-table-column>
          <el-table-column prop="score" label="题目分值" align="center"></el-table-column>
          <el-table-column prop="myscore" label="实际得分" align="center"></el-table-column>
          <el-table-column prop="addtime" label="答题时间" align="center"></el-table-column>
          <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="viewDetails(scope.row)">查看详情</el-button>
              <el-button type="text" size="small" @click="downloadDetails(scope.row)">下载答题详情</el-button>
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
        ></el-pagination>
      </div>

      <!-- 查看详情对话框 -->
      <el-dialog title="答题详情" :visible.sync="detailsDialogVisible" width="60%">
        <div v-if="currentRecord">
          <h3>基本信息</h3>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="患者姓名">{{ currentRecord.username }}</el-descriptions-item>
            <el-descriptions-item label="测试模板">{{ currentRecord.papername }}</el-descriptions-item>
            <el-descriptions-item label="试题名称">{{ currentRecord.questionname }}</el-descriptions-item>
            <el-descriptions-item label="答题时间">{{ currentRecord.addtime }}</el-descriptions-item>
          </el-descriptions>
          
          <h3 style="margin-top: 20px">答题情况</h3>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="题目分值">{{ currentRecord.score }}</el-descriptions-item>
            <el-descriptions-item label="实际得分">{{ currentRecord.myscore }}</el-descriptions-item>
            <el-descriptions-item label="正确答案">{{ currentRecord.answer }}</el-descriptions-item>
            <el-descriptions-item label="考生答案">{{ currentRecord.myanswer }}</el-descriptions-item>
          </el-descriptions>

          <div v-if="currentRecord.analysis" style="margin-top: 20px">
            <h3>答案解析</h3>
            <div v-html="currentRecord.analysis"></div>
          </div>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showFlag: true,
      searchForm: {
        username: '',
        papername: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      detailsDialogVisible: false,
      currentRecord: null
    }
  },
  created() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'id',
        doctorId: localStorage.getItem('userId') // 获取当前登录医生的ID
      }
      if (this.searchForm.username) {
        params['username'] = '%' + this.searchForm.username + '%'
      }
      if (this.searchForm.papername) {
        params['papername'] = '%' + this.searchForm.papername + '%'
      }
      this.$http({
        url: '/examrecord/doctor/list', // 需要后端提供此接口
        method: 'get',
        params: params
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list
          this.totalPage = data.data.total
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 搜索
    search() {
      this.pageIndex = 1
      this.getDataList()
    },
    // 查看详情
    viewDetails(row) {
      this.currentRecord = row
      this.detailsDialogVisible = true
    },
    // 下载答题详情
    downloadDetails(row) {
      // 创建下载内容
      let content = `患者答题详情报告\n\n`
      content += `基本信息：\n`
      content += `患者姓名：${row.username}\n`
      content += `测试模板：${row.papername}\n`
      content += `试题名称：${row.questionname}\n`
      content += `答题时间：${row.addtime}\n\n`
      content += `答题情况：\n`
      content += `题目分值：${row.score}\n`
      content += `实际得分：${row.myscore}\n`
      content += `正确答案：${row.answer}\n`
      content += `考生答案：${row.myanswer}\n\n`
      if (row.analysis) {
        content += `答案解析：\n${row.analysis.replace(/<[^>]+>/g, '')}\n`
      }

      // 创建并下载文件
      const blob = new Blob([content], { type: 'text/plain;charset=utf-8' })
      const link = document.createElement('a')
      link.href = URL.createObjectURL(blob)
      link.download = `${row.username}-答题详情-${row.addtime}.txt`
      link.click()
      URL.revokeObjectURL(link.href)
    }
  }
}
</script>

<style scoped>
.form-content {
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}
.slt {
  margin: 0;
  display: flex;
  align-items: center;
}
.table-content {
  margin-top: 20px;
}
.el-pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
