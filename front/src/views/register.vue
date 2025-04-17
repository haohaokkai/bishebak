<template>
  <div class="register-container">
    <div class="register-box">
      <!-- 左侧品牌展示区 -->
      <div class="brand-section">
        <div class="brand-content">
          <h1>人格障碍线上诊断平台</h1>
          <p>专业的心理健康服务平台</p>
        </div>
      </div>
      
      <!-- 右侧注册表单 -->
      <div class="register-section">
        <div class="register-form">
          <h2>账号注册</h2>
          <el-form ref="rgsForm" class="rgs-form" :model="ruleForm">
            <el-form-item label="用户账号" class="input" v-if="tableName=='yonghu'">
              <el-input v-model="ruleForm.yonghuzhanghao" autocomplete="off" placeholder="请输入用户账号" />
            </el-form-item>
            <el-form-item label="用户姓名" class="input" v-if="tableName=='yonghu'">
              <el-input v-model="ruleForm.yonghuxingming" autocomplete="off" placeholder="请输入用户姓名" />
            </el-form-item>
            <el-form-item label="密码" class="input" v-if="tableName=='yonghu'">
              <el-input v-model="ruleForm.mima" autocomplete="off" placeholder="请输入密码" type="password" />
            </el-form-item>
            <el-form-item label="确认密码" class="input" v-if="tableName=='yonghu'">
              <el-input v-model="ruleForm.mima2" autocomplete="off" placeholder="请再次输入密码" type="password"/>
            </el-form-item>
            <el-form-item label="手机号" class="input" v-if="tableName=='yonghu'">
              <el-input v-model="ruleForm.shoujihao" autocomplete="off" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="医生账号" class="input" v-if="tableName=='yisheng'">
              <el-input v-model="ruleForm.yishengzhanghao" autocomplete="off" placeholder="请输入医生账号" />
            </el-form-item>
            <el-form-item label="医生姓名" class="input" v-if="tableName=='yisheng'">
              <el-input v-model="ruleForm.yishengxingming" autocomplete="off" placeholder="请输入医生姓名" />
            </el-form-item>
            <el-form-item label="密码" class="input" v-if="tableName=='yisheng'">
              <el-input v-model="ruleForm.mima" autocomplete="off" placeholder="请输入密码" type="password" />
            </el-form-item>
            <el-form-item label="确认密码" class="input" v-if="tableName=='yisheng'">
              <el-input v-model="ruleForm.mima2" autocomplete="off" placeholder="请再次输入密码" type="password"/>
            </el-form-item>
            <el-form-item label="手机号" class="input" v-if="tableName=='yisheng'">
              <el-input v-model="ruleForm.shoujihao" autocomplete="off" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="医生职位" class="input" v-if="tableName=='yisheng'">
              <el-input v-model="ruleForm.yishengzhiwei" autocomplete="off" placeholder="请输入医生职位" />
            </el-form-item>
            <div class="button-group">
              <el-button class="register-btn" type="primary" @click="login()">注册</el-button>
              <el-button class="cancel-btn" type="primary" @click="close()">取消</el-button>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
      },
      tableName:"",
      rules: {},
    };
  },
  mounted(){
    let table = this.$storage.get("loginTable");
    this.tableName = table;
      },
  created() {
    
  },
  methods: {
    // 获取uuid
    getUUID () {
      return new Date().getTime();
    },
    close(){
	this.$router.push({ path: "/login" });
    },
    // 注册
    login() {
	var url=this.tableName+"/register";
      if((!this.ruleForm.yonghuzhanghao) && `yonghu` == this.tableName){
        this.$message.error(`用户账号不能为空`);
        return
      }
      if((this.ruleForm.mima!=this.ruleForm.mima2) && `yonghu` == this.tableName){
	    this.$message.error(`两次密码输入不一致`);
	    return
      }
      if(`yonghu` == this.tableName && this.ruleForm.shoujihao&&(!this.$validate.isMobile(this.ruleForm.shoujihao))){
        this.$message.error(`手机号应输入手机格式`);
        return
      }
      if((!this.ruleForm.yishengzhanghao) && `yisheng` == this.tableName){
        this.$message.error(`医生账号不能为空`);
        return
      }
      if((this.ruleForm.mima!=this.ruleForm.mima2) && `yisheng` == this.tableName){
	    this.$message.error(`两次密码输入不一致`);
	    return
      }
      if(`yisheng` == this.tableName && this.ruleForm.shoujihao&&(!this.$validate.isMobile(this.ruleForm.shoujihao))){
        this.$message.error(`手机号应输入手机格式`);
        return
      }
      this.$http({
        url: url,
        method: "post",
        data:this.ruleForm
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            message: "注册成功",
            type: "success",
            duration: 1500,
            onClose: () => {
              this.$router.replace({ path: "/login" });
            }
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: transparent;
  background-image: url(../assets/login-bg.jpg);
  background-size: cover;
  background-position: center;
  padding: 20px;

  .register-box {
    width: 1200px;
    height: 900px;
    display: flex;
    background: rgba(255, 255, 255, 0.3);
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
    overflow: hidden;
    margin: 0 auto;

    .brand-section {
      flex: 1;
      background: linear-gradient(135deg, rgba(30, 144, 255, 0.4), rgba(65, 105, 225, 0.4));
      padding: 40px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      color: #fff;

      .brand-content {
        h1 {
          font-size: 32px;
          margin-bottom: 20px;
          font-weight: 500;
        }
        p {
          font-size: 16px;
          opacity: 0.9;
        }
      }
    }

    .register-section {
      flex: 1;
      display: flex;
      justify-content: center;
      align-items: center;
      background: rgba(255, 255, 255, 0.3);
      padding: 40px;
      position: relative;
      min-width: 600px;
      overflow-y: auto;

      .register-form {
        width: 100%;
        max-width: 500px;
        min-height: 100%;
        padding: 40px;
        background: transparent;
        border-radius: 0;
        margin: 0 auto;
        position: relative;
        box-shadow: none;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;

        h2 {
          font-size: 28px;
          color: #333;
          margin-bottom: 40px;
          text-align: center;
        }

        .el-form-item {
          margin-bottom: 25px;
          width: 100%;
        }

        .button-group {
          display: flex;
          justify-content: space-between;
          margin-top: 40px;
          margin-bottom: 20px;
          width: 100%;
        }

        .register-btn {
          width: 45%;
          height: 44px;
          background: #1E90FF;
          border: none;
          font-size: 16px;
          transition: all 0.3s;

          &:hover {
            background: rgba(138, 174, 220, 0.8);
          }
        }

        .cancel-btn {
          width: 45%;
          height: 44px;
          background: #909399;
          border: none;
          font-size: 16px;
          transition: all 0.3s;

          &:hover {
            background: #606266;
          }
        }
      }
    }
  }
}

::v-deep .el-input__inner {
  height: 44px;
  border-radius: 4px;
  border: 1px solid rgba(220, 223, 230, 0.5);
  transition: all 0.3s;
  background: rgba(255, 255, 255, 0.3);
  color: #333;
  font-size: 16px;
  font-weight: 500;
}

::v-deep .el-input__inner::placeholder {
  color: rgba(0, 0, 0, 0.7);
  font-size: 16px;
  font-weight: 500;
}

::v-deep .el-input__inner:focus {
  border-color: #1E90FF;
  box-shadow: 0 0 0 2px rgba(30, 144, 255, 0.1);
}

::v-deep .el-form-item__label {
  font-size: 14px;
  color: #333;
  font-weight: 500;
  line-height: 44px;
}
</style>
