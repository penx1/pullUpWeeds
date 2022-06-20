
<template>

  <div id="login">
    <h1>注册</h1>
    <el-form  :model="form" :rules="rules" ref="form">
      <el-form-item  prop="username">
        <el-input prefix-icon="el-icon-user" v-model="form.username" style="width: 100%;"></el-input>
      </el-form-item>
      <el-form-item prop="password" >
        <el-input prefix-icon="el-icon-lock" type="password" v-model="form.password" show-password style="width: 100%;"></el-input>
      </el-form-item>
      <el-form-item prop="confirm" >
        <el-input prefix-icon="el-icon-lock" type="password" v-model="form.confirm" show-password style="width: 100%;"></el-input>
      </el-form-item>
      <el-button type="primary" @click="register" style="width: 100%">注册</el-button>
    </el-form>
  </div>


</template>
<style>

body{
  width: 100%;
  height: 100%;
  font-family: 'Open Sans',sans-serif;
  margin: 0;
  background-color: #79BBFF;
}
#login{
  position: absolute;
  top: 40%;
  left:50%;
  margin: -150px 0 0 -150px;
  width: 300px;
  height: 300px;
}
#login h1{
  color: #fff;
  text-shadow:0 0 10px;
  letter-spacing: 1px;
  text-align: center;
}
h1{
  font-size: 2em;
  margin: 0.67em 0;
}

</style>
<script>
import request from "../utils/request";

export default {
  data(){
    let checkUsername = (rule, value, callback) => {
      let reg = /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/;
      if (!value) {
        callback(new Error('用户名为必填项'));
      } else if (value.length < 5 || value.length > 12) {
        callback(new Error('长度在 5 到 12 个字符'));
      }
      else if (!reg.test(value)) {
        callback(new Error('姓名不支持特殊字符'));
      } else if (this.usernameRules(value)) {
        callback(new Error('用户名已存在'));
      }
      else {
        return true;
        callback();
      }
    };
    return{
      rules: {
        username: [
          // { validator: checkUsername, trigger: 'blur' },
          { required: true, message: '请输入用户名', trigger: 'blur' },

        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],confirm: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
        ],
      },
      form:{

      }
    }
  },
  methods:{
     async usernameRules(value) {

       return true;
       console.log("ceshi测试")

       // const {data: res} = await  request.post('/user/checkUser', param).then(res => {
       //   if (res.flag === "success") {
       //     return true;
       //   } else {
       //     return false;
       //   }
       //   return false;
       //   ;
       // })

       let param = new URLSearchParams();
       param.append("username",value);

       request.post('/user/checkUser', param).then(res => {
         if (res.flag == "success") {
           return true;
         } else {
           return false;
         }
         ;
       })


     },

    register(){
      this.$refs['form'].validate((valid) => {
        if (valid) {


          if (this.form.password !== this.form.confirm){
            this.$message({
              type:"error",
              message:"两次密码输入不一致"
            })
            return
          }
          request.post('/user/register',this.form).then(res => {
            if (res.code === '0'){
              this.$message({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login")//登陆成功重定向到主页
            }else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })

        }
      })

    }
  },
  name: "Register"
}
</script>

<style scoped>

</style>