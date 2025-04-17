<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 左侧品牌展示区 -->
      <div class="brand-section">
        <div class="brand-content">
          <h1>人格障碍线上诊断平台</h1>
          <p>专业的心理健康服务平台</p>
        </div>
      </div>
      
      <!-- 右侧登录表单 -->
      <div class="login-section">
        <div class="login-form">
          <h2>账号登录</h2>
          <el-form label-position="top">
            <el-form-item>
              <el-input 
                v-model="rulesForm.username" 
                placeholder="请输入用户名"
                prefix-icon="el-icon-user">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-input 
                v-model="rulesForm.password" 
                type="password" 
                placeholder="请输入密码"
                prefix-icon="el-icon-lock">
              </el-input>
            </el-form-item>
            <el-form-item class="role-item">
              <el-radio-group v-model="rulesForm.role">
                <el-radio 
                  v-for="item in menus"
                  v-if="item.hasBackLogin=='是'"
                  :key="item.roleName"
                  :label="item.roleName">
                  {{item.roleName}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-button 
              type="primary" 
              class="login-btn" 
              @click="login()">
              登录
            </el-button>
            <div class="register-link">
              <span @click="register('yisheng')">注册医生账号</span>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: transparent;
  background-image: url(../assets/login-bg.jpg);
  background-size: cover;
  background-position: center;
  padding: 20px;

  .login-box {
    width: 1000px;
    height: 500px;
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

    .login-section {
      flex: 1;
      display: flex;
      justify-content: center;
      align-items: center;
      background: rgba(255, 255, 255, 0.3);
      padding: 0;
      position: relative;
      min-width: 500px;

      .login-form {
        width: 80%;
        max-width: 360px;
        height: 100%;
        padding: 40px 30px;
        background: transparent;
        border-radius: 0;
        margin: 0 auto;
        position: absolute;
        top: 0;
        left: 50%;
        transform: translateX(-50%);
        box-shadow: none;
        display: flex;
        flex-direction: column;
        justify-content: center;

        h2 {
          font-size: 24px;
          color: #333;
          margin-bottom: 30px;
          text-align: center;
        }

        .el-form-item {
          margin-bottom: 20px;
        }

        .role-item {
          margin-bottom: 15px;
          
          .el-radio-group {
            display: flex;
            justify-content: space-between;
          }
        }

        .login-btn {
          width: 100%;
          height: 40px;
          margin-top: 10px;
          background: #1E90FF;
          border: none;
          font-size: 16px;
          transition: all 0.3s;

          &:hover {
            background: rgba(138, 174, 220, 0.8);
          }
        }

        .register-link {
          text-align: center;
          margin-top: 15px;
          
          span {
            color: #1E90FF;
            cursor: pointer;
            font-size: 14px;
            
            &:hover {
              color: rgba(138, 174, 220, 0.8);
              text-decoration: underline;
            }
          }
        }
      }
    }
  }
}

::v-deep .el-input__inner {
  height: 40px;
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

::v-deep .el-radio__label {
  font-size: 14px;
  color: #333;
}

::v-deep .el-radio__inner {
  border-color: rgba(220, 223, 230, 0.5);
  background: rgba(255, 255, 255, 0.3);
}

::v-deep .el-radio.is-checked .el-radio__inner {
  background: rgba(30, 144, 255, 0.6);
  border-color: rgba(30, 144, 255, 0.6);
}

::v-deep .el-radio.is-checked .el-radio__label {
  color: #1E90FF;
}
</style>

<script>
import menu from "@/utils/menu";

export default {
  data() {
    return {
      rulesForm: {
        username: "",
        password: "",
        role: "",
        code: '',
      },
      menus: [],
      tableName: "",
    };
  },
  mounted() {
    let menus = menu.list();
    this.menus = menus;
  },
  methods: {
    register(tableName){
      this.$storage.set("loginTable", tableName);
      this.$router.push({path:'/register'})
    },
    login() {
      if (!this.rulesForm.username) {
         this.$message.error("请输入用户名");
        return;
      }
      if (!this.rulesForm.password) {
         this.$message.error("请输入密码");
        return;
      }
      if (!this.rulesForm.role) {
         this.$message.error("请选择角色");
        return;
      }
      let menus = this.menus;
      for (let i = 0; i < menus.length; i++) {
        if (menus[i].roleName == this.rulesForm.role) {
          this.tableName = menus[i].tableName;
        }
      }
      this.$http({
        url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
        method: "post"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$storage.set("Token", data.token);
          this.$storage.set("role", this.rulesForm.role);
          this.$storage.set("sessionTable", this.tableName);
          this.$storage.set("adminName", this.rulesForm.username);
          this.$router.replace({ path: "/index/" });
        } else {
          this.$message.error(data.msg);
        }
      });
    }
  }
};
</script>
