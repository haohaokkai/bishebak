<template>
  <div class="addEdit-block">
    <el-form
      class="detail-form-content"
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      :style="{backgroundColor:addEditForm.addEditBoxColor}"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item class="date" label="日期" prop="workDate">
            <el-date-picker
              v-model="ruleForm.workDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item class="time" label="时间段" prop="timeSlot">
            <el-time-select
              v-model="ruleForm.timeSlot"
              :picker-options="{
                start: '08:00',
                step: '01:00',
                end: '18:00'
              }"
              placeholder="选择时间段"
              @change="formatTimeSlot">
            </el-time-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" label="最大预约人数" prop="maxPatients">
            <el-input-number v-model="ruleForm.maxPatients" :min="1" :max="20"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item class="btn">
        <el-button
          type="primary"
          @click="onSubmit"
        >提交</el-button>
        <el-button @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  props: {
    parent: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      id: "",
      type: "",
      ruleForm: {
        doctorId: "", // 从登录信息中获取
        workDate: "",
        timeSlot: "",
        maxPatients: 5,
        bookedCount: 0,
      },
      rules: {
        workDate: [{ required: true, message: "请选择日期", trigger: "blur" }],
        timeSlot: [{ required: true, message: "请选择时间段", trigger: "blur" }],
        maxPatients: [{ required: true, message: "请输入最大预约人数", trigger: "blur" }]
      },
      addEditForm: {
        addEditBoxColor: "#fff"
      }
    };
  },
  mounted() {},
  methods: {
    // 初始化
    init(id, type) {
      this.id = id;
      this.type = type;
      
      // 设置当前医生ID
      this.ruleForm.doctorId = this.$storage.get("userid");
      
      if (id) {
        this.$http({
          url: `schedule/info/${id}`,
          method: "get",
          params: {}
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.ruleForm = data.data;
          }
        });
      }
    },
    // 格式化时间段为xx:xx-xx:xx格式
    formatTimeSlot() {
      if (this.ruleForm.timeSlot) {
        const startTime = this.ruleForm.timeSlot;
        // 从08:00形式提取小时和分钟，转为数字
        const [startHour, startMinute] = startTime.split(':').map(Number);
        
        // 计算结束时间（假设每个时间段为1小时）
        let endHour = startHour + 1;
        let endMinute = startMinute;
        
        // 格式化结束时间
        const endTime = `${endHour.toString().padStart(2, '0')}:${endMinute.toString().padStart(2, '0')}`;
        
        // 设置格式化后的时间段
        this.ruleForm.timeSlot = `${startTime}-${endTime}`;
      }
    },
    // 提交
    onSubmit() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: `schedule/${!this.ruleForm.id ? "save" : "update"}`,
            method: "post",
            data: this.ruleForm
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.$emit('refreshDataList');
                  this.back();
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    // 返回
    back() {
      if (this.parent) {
        this.parent.showFlag = true;
        this.parent.addOrUpdateFlag = false;
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.editor{
  height: 500px;
}
</style> 