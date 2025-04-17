<template>
  <div class="add-update-preview">
    <el-form
      class="form-content"
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      :style="{backgroundColor:addEditForm.addEditBoxColor}"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item class="info" label="预约编号">
            <el-input v-model="ruleForm.id" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item class="info" label="预约日期">
            <el-input v-model="ruleForm.appointDate" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item class="info" label="预约时间段">
            <el-input v-model="ruleForm.timeSlot" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item class="info" label="患者ID">
            <el-input v-model="ruleForm.userId" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item class="info" label="医生ID">
            <el-input v-model="ruleForm.doctorId" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item class="select" label="预约状态" prop="status">
            <el-select v-model="ruleForm.status" placeholder="请选择" :disabled="type=='info'">
              <el-option label="待确认" value="PENDING"></el-option>
              <el-option label="已确认" value="CONFIRMED"></el-option>
              <el-option label="已取消" value="CANCELED"></el-option>
              <el-option label="已完成" value="COMPLETED"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item class="textarea" label="备注" prop="note">
            <el-input
              v-model="ruleForm.note"
              type="textarea"
              :rows="6"
              placeholder="请输入备注信息"
              :disabled="type=='info'"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item class="btn">
        <el-button v-if="type!='info'" type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      type: "",
      ruleForm: {
        id: "",
        doctorId: "",
        userId: "",
        appointDate: "",
        timeSlot: "",
        status: "",
        note: ""
      },
      rules: {
        status: [{ required: true, message: "预约状态不能为空", trigger: "blur" }]
      },
      addEditForm: {
        addEditBoxColor: "#fff"
      }
    };
  },
  props: ["parent"],
  methods: {
    // 初始化
    init(id, type) {
      this.id = id;
      this.type = type;
      
      if (id) {
        this.$http({
          url: `appointment/info/${id}`,
          method: "get",
          params: {}
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.ruleForm = data.data;
          }
        });
      }
    },
    // 提交
    onSubmit() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: `appointment/update`,
            method: "post",
            data: this.ruleForm
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.parent.showFlag = true;
                  this.parent.addOrUpdateFlag = false;
                  this.parent.appointmentAddOrUpdateFlag = false;
                  this.parent.getDataList();
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
      this.parent.showFlag = true;
      this.parent.addOrUpdateFlag = false;
      this.parent.appointmentAddOrUpdateFlag = false;
    }
  }
};
</script>

<style lang="scss" scoped>
.add-update-preview {
  .form-content {
    background-color: #fff;
    padding: 12px;
    border-radius: 6px;
    margin: 15px;
    .info {
      width: 100%;
      height: 30px;
    }
    .select {
      width: 100%;
      height: 30px;
    }
    .btn {
      margin: 10px 0;
      text-align: center;
    }
  }
}
</style> 