
<template >
  <div id="center">
    <!--    头部-->
    <Header  style="background:rgba(255,255,255,0.8)" :user="user" @setSearch="getSearch"/>
    <!--      主体-->
    <div style="display: flex; ">

      <!--内容区域-->
      <div style="flex: 6;" ></div>
      <router-view :search="search" style="flex: 12;background-color:rgba(255,255,255,0.9)" @userInfo="refreshUser"/>
      <div style="flex: 6"></div>

    </div>

  </div >

</template>
<style>

</style>
<script>



import Header from "../components/Header";
import Aside from "../components/Aside";
import request from "../utils/request";

export default {
  name: "Layout",
  components: {
    Header,
    Aside,
  },
  data(){
    return{
      search:"",
      user:{}
    }
  },
  created() {
    this.refreshUser()
  },


  methods:{
    getSearch(sea){
      this.search=sea;
    },
    refreshUser(){
      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).id
      request.get("/user/" + userId).then(res => {
        this.user = res.data
      })
    }
  },

}
</script>

<style scoped>
#center{
  min-height: 100vh;
  background: url(../assets/photo/mainBg.png) center center /
    cover no-repeat fixed;

}
</style>