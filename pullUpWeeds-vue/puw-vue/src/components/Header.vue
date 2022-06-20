<template>
<div style="height: 70px;line-height: 70px;border-bottom: 1px solid #ccc;display: flex;
/*background-image: linear-gradient(45deg,#7AFFAF,#7A88FF);*/
}" >
  <div style="flex: 2;text-align: center; font-weight: bold;color: dodgerblue ">
    <el-button type="text" size="big" @click="home"><h3>TureHouse</h3></el-button>
  </div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;" >    <el-button type="text"  @click="home"><h2>首页</h2></el-button></div>
  <div style="flex: 2;"></div>
  <div style="flex: 2;"></div>
  <div style="flex: 4;">

      <el-input v-model="search" placeholder="输入用户文章标题关键字" style="width: 100%"  clearable></el-input>

  </div>
  <div style="flex: 2;text-align: center">  <el-button type="primary"  @click="setSearch" round>查询</el-button></div>
<!--  <div style="flex: 2;"></div>-->
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
      search:"",
    }
  },
  created() {

  },
  methods:{
    setSearch(){//向父组件传search
      this.$emit('setSearch',this.search)
    },
    home(){
      this.$router.push("/");
    },
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