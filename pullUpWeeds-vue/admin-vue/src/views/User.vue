<template >
  <div >
    <div style="padding: 10px">
      <div style="margin:  0">
        <el-button type="primary" @click="add">新增</el-button>

      </div>
      <div  style="margin: 10px 0">
        <el-input v-model="search" placeholder="输入用户昵称关键字" style="width: 20%" clearable></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      </div>
      <el-table :data="tableData" border  stripe style="width: 99%">
        <el-table-column prop="id" label="ID" sortable=""> </el-table-column>
        <el-table-column prop="username" label="用户名" sortable=""> </el-table-column>
        <el-table-column prop="nickname" label="昵称" sortable=""></el-table-column>
        <el-table-column  label="权限" sortable="">
          <template  #default="scope">
                      <div v-if="scope.row.manager == 1">
                        管理员
                      </div>
                      <div v-else-if="scope.row.manager==0">
                        用户
                      </div>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" sortable=""></el-table-column>
        <el-table-column  label="头像" >
          <template  #default="scope">
            <el-image
                style="width: 70px; height: 70px"
                :src="scope.row.avatar"
                :preview-src-list="scope.row.avatar"
            ></el-image>
          </template>

        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.row)" >编辑</el-button>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button size="mini"   type="danger" >删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>

      </el-table>
      <div style="margin: 10px">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5,10,20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        >
        </el-pagination>
        <el-dialog title="提示" v-model="dialogVisible" width="30%">
          <el-form  :model="form" label-width="120px">
            <el-form-item label="用户名">
              <el-input v-model="form.username" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item label="密码" >
              <el-input type="password" v-model="form.password" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="form.nickname" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="form.email" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item label="权限">
              <el-radio v-model="form.manager"  :label= "1">管理员</el-radio>
              <el-radio v-model="form.manager" :label= "0">用户</el-radio>
            </el-form-item>
            <el-form-item label="头像">
              <el-upload ref="upload" action="/api/avatar/upload" :on-success="filesUploadSuccess">
                <el-button size="small" type="primary">点击上传</el-button>

              </el-upload>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>


      </div>


    </div>

  </div>
</template>

<script>
// @ is an alias to /src


import request from "../utils/request";

export default {
  data() {
    return{
      currentPage:1,
      pageSize: 5,
      dialogVisible: false,
      form: {
        username: 233
      },
      total:0,

      search: '',
      tableData: [

      ]
    }
  },
  name: 'Home',
  components: {

  },

  created() {
    this.load()
  },

  methods:{
    loadMsg(res){
      if (res.flag === true){
        this.$message({
          type:"success",
          message:res.message
        })
      }else {
        this.$message({
          type:"error",
          message:res.message
        })
      }
    },
    filesUploadSuccess(res){
      this.form.avatar=res.data
      this.$refs.upload.clearFiles()
    },
    load(){
      console.log("session"+sessionStorage.getItem("user"));

      request.get("/user",{
        // get请求需要params
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res =>{
        this.loadMsg(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })


    },
    add(){
      this.dialogVisible = true
      this.form={}
    },
    save: function () {
      if (this.form.id){//  更新
        request.put("/user",this.form).then(res=> {
          this.loadMsg(res)
          this.load()
          this.dialogVisible=false
        })
        this.dialogVisible = false
      }else {//  新增
        request.post("/user",this.form).then(res=> {
          this.loadMsg(res)
          this.load()
          this.dialogVisible=false
        })
      }


    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id){
      request.delete("/user/"+id).then(res=>{
        this.loadMsg(res)

        this.load()
      })
    },
    handleSizeChange(pageSize){//改变当前页数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){//改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },


  }
}
</script>
