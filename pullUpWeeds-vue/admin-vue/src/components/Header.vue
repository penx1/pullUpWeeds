<template>
<div style="height: 70px;line-height: 70px;border-bottom: 1px solid #ccc;display: flex; background-image: linear-gradient(45deg,#7AFFAF,#7A88FF);
}" >
  <div style="flex: 2;padding-left: 30px;font-weight: bold;color: dodgerblue ">TureHouse后台管理</div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;text-align:right;height: 70px;line-height: 70px;padding-top: 15px">
      <el-avatar  :size="40" :src="user.avatar" ></el-avatar>
  </div>
  <div style="flex: 2;text-align:center;">
    <el-dropdown>
      <span class="el-dropdown-link" >
           {{user.nickname}}
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
          <template #dropdown>
            <el-dropdown-menu>

              <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
              <el-dropdown-item @click="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
    </el-dropdown>
  </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Header",
  props:['user'],
  data(){
    return{

    }
  },
  created() {

  },
  methods:{
    logout(){
      request.get("/logout").then(res=>{

      })
      this.$router.push('/login')
    }
  }

}
</script>

<style scoped>

</style>