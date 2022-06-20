<template>
  <div>
    <el-card style=" margin: 20px">
      <div style="text-align: center"><el-avatar shape="square" :size="100" :src="form.avatar"></el-avatar></div>
      <div style="text-align: center">个人中心</div>
      <el-divider>
        <i class="el-icon-star-off"></i>
      </el-divider>
      <el-form ref="form" :model="form" label-width="80px">

        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="修改昵称">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="修改邮箱">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="修改密码">
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item label="修改头像">
          <el-upload ref="upload" action="/api/avatar/upload" :on-success="filesUploadSuccess">
            <el-button size="small" type="primary">点击上传</el-button>

          </el-upload>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="update">保存</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {

      }
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    console.log(this.form)
  },
  methods: {
    filesUploadSuccess(res){
      this.form.avatar=res.data
      this.$refs.upload.clearFiles()
    },
   
    update() {
      request.put("/user", this.form).then(res => {
        console.log(res)
        if (res.flag === true) {
          this.$message({
            type: "success",
            message: res.message
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          // 触发Layout更新用户信息
          this.$emit("userInfo")
        } else {
          this.$message({
            type: "error",
            message: res.message
          })
        }
      })
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
