<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-title">TureHouse用户注册</div>
      <!-- 登录表单 -->
      <el-form
          status-icon
          :model="registerForm"
          :rules="rules"
          ref="ruleForm"
          class="register-form"
      >
        <!-- 用户名输入框 -->
        <el-form-item prop="username">
          <el-input
              v-model="registerForm.username"
              prefix-icon="el-icon-user-solid"
              placeholder="用户名"
          />
        </el-form-item>
        <!-- 昵称输入框 -->
        <el-form-item prop="nickname">
          <el-input
              v-model="registerForm.nickname"
              prefix-icon="el-icon-user-solid"
              placeholder="昵称"
          />
        </el-form-item>
<!--        &lt;!&ndash; 邮箱输入框 &ndash;&gt;-->
<!--        <el-form-item prop="email">-->
<!--          <el-input-->
<!--              v-model="registerForm.email"-->
<!--              prefix-icon="el-icon-user-solid"-->
<!--              placeholder="邮箱"-->
<!--              @keyup.enter.native="register"-->
<!--          />-->
<!--        </el-form-item>-->
        <!-- 密码输入框 -->
        <el-form-item prop="password">
          <el-input
              v-model="registerForm.password"
              prefix-icon="el-icon-lock"
              show-password
              placeholder="密码"
          />
        </el-form-item>
      </el-form>
      <!-- 登录按钮 -->
     <div> <el-button type="success" @click="register">注册</el-button></div>
      <div><el-button type="primary" @click="goToLogin">去登陆</el-button></div>
    </div>
  </div>
</template>
<script>
import request from "@/utils/request";
import axios from "axios";

export default {

  data: function() {
    var checkUsername = (rule, value, callback) => {
      let reg = /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/;
      if (!value) {
        callback(new Error('用户名为必填项'));
      } else if (value.length < 5 || value.length > 12) {
        callback(new Error('长度在 5 到 12 个字符'));
      } else if (!reg.test(value)) {
        callback(new Error('姓名不支持特殊字符'));
      }
      else if (!this.usernameRules(value)) {//有bug
        callback(new Error('用户名已存在'));
      }
      else {
        callback();
      }
    };
    return {
      registerForm: {
        username: "",
        password: "",
        nickname: ""
      },
      rules: {
        username: [{ validator: checkUsername, trigger: 'blur' },],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }],
        nickname: [{ required: true, message: "昵称不能为空", trigger: "blur" }],

      }
    };
  },
  methods: {
     async usernameRules(value) {

       let par = new URLSearchParams();
       par.append("username", value);

       const {data: res} = await request.post('/user/checkUser',  par).then(res => {

         if (res.code === '207') {
           console.log("false")
           return false;
         } else {
           console.log("ture")

           return true;
         }
         ;
       })
       // request.post('/user/checkUser', param).then(res => {
       //   if (res.flag === "success") {
       //     return true;
       //   } else {
       //     return false;
       //   }
       //   ;
       // })

     },
    goToLogin(){
      this.$router.push("/login")
    },
    register() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          const that = this;
          // eslint-disable-next-line no-undef

          //腾讯验证
          var captcha = new   TencentCaptcha(
              // this.config.TENCENT_CAPTCHA,
              "2068422883",
              function(res) {
                if (res.ret === 0) {
                  //发送登录请求
                  let param = new URLSearchParams();
                  param.append("username", that.registerForm.username);
                  param.append("password", that.registerForm.password);
                  param.append("nickname", that.registerForm.nickname);

                  request.post("/user/register", param).then(data  => {
                    if (data.flag) {

                      that.$message.success("注册成功");
                      that.$router.push({ path: "/login" });

                    } else {
                      that.$message.error(data.message);
                    }
                  });
                }
              }
          );
          // 显示验证码
          captcha.show();
        } else {
          return false;
        }
      });
    }
  }
};
</script>
<style scoped>
.register-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background: url(../assets/photo/bg.png) center center /
    cover no-repeat;
}
.register-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  padding: 170px 60px 180px;
  width: 350px;
}
.register-title {
  color: #303133;
  font-weight: bold;
  font-size: 1rem;
}
.register-form {
  margin-top: 1.2rem;
}
.register-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
