<template >
  <div style="">
<!--    70px是header的长度-->
    <el-menu
        style="width: 200px;min-height: calc(100vh - 70px)"
        :default-active="path"
        router
        class="el-menu-vertical-demo"
    >
      <el-sub-menu index="1">
        <template #title>
          <span>系统管理</span>
        </template>
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/article">文章管理</el-menu-item>
          <el-menu-item index="/comment">评论管理</el-menu-item>

      </el-sub-menu>
<!--      <el-menu-item index="/book">图书管理</el-menu-item>-->
<!--      <el-menu-item index="/storeBack">商品</el-menu-item>-->
    </el-menu>
  </div>

</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      path:this.$route.path
    }
  }
}
</script>

<style scoped>

</style>