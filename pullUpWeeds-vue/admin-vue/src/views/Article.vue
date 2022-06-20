<template >
  <div >
    <div style="padding: 10px">
      <div style="margin: 10px 0">
        <el-button type="primary" @click="add">新增</el-button>

      </div>
      <div  style="margin: 10px 0">
        <el-input v-model="search" placeholder="输入关键字" style="width: 20%" clearable></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      </div>
      <el-table :data="tableData" border  stripe style="width: 100%">
        <el-table-column prop="id" label="ID" sortable=""> </el-table-column>
        <el-table-column prop="title" label="标题" sortable=""> </el-table-column>
<!--        <el-table-column prop="content" label="内容" sortable=""></el-table-column>-->
        <el-table-column prop="user.nickname" label="作者" sortable=""></el-table-column>
        <el-table-column prop="time" label="发表时间" sortable=""></el-table-column>

        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button size="mini" type="success" @click="handleContent(scope.row)" >内容</el-button>
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
        <el-dialog title="添加文章" v-model="dialogVisible" width="60%">
          <el-form  :model="form" label-width="120px">

            <el-form-item label="标题" >
              <el-input  v-model="form.title" style="width: 80%;"></el-input>
            </el-form-item>

<!--            <el-form-item label="作者">-->
<!--              <el-input v-model="form.authorId" style="width: 80%;"></el-input>-->
<!--            </el-form-item>-->

            <div id="div1">

            </div>
          </el-form>
          <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>


        <el-dialog title="文章内容" v-model="vcontent" width="50%">
          <el-card>
            <div v-html="contents.content"></div>
          </el-card>
        </el-dialog>
      </div>


    </div>

  </div>
</template>

<script>
// @ is an alias to /src

import E from 'wangeditor'
import request from "../utils/request";
let editor
export default {
  data() {
    return{
      currentPage:1,
      pageSize: 5,
      dialogVisible: false,
      form: {

      },
      total:0,
      contents:{},
      vcontent:false,
      search: '',
      tableData: [

      ]
    }
  },
  name: 'Article',
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
    // filesUploadSuccess(res){
    //   this.form.avatar=res.data
    //   this.$refs.upload.clearFiles()
    // },
    load(){
      // console.log("session"+sessionStorage.getItem("user"));
      //
      request.get("/article",{
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
      this.$nextTick(()=>{  //对未来元素的操作
       if (!editor){
         editor = new E('#div1')//创建wangeditor对象
         editor.config.uploadImgServer = "http://localhost:8090/photo/upload"//配置图片上传地址
         editor.config.uploadFileName = "file"
         editor.config.excludeMenus = [
           'emoticon',
           'video',
         ]
         editor.create()
        }else {//如果已有editor对象则清空其中的内容
        editor.txt.html("")
        }
      }
    )
    },
    save: function () {
      if (this.form.id){//  更新
        this.form.content=editor.txt.html()
        request.put("/article",this.form).then(res=> {
          this.loadMsg(res)
          this.load()
          this.dialogVisible=false
        })
        this.dialogVisible = false
      }else {//  新增
        this.form.content=editor.txt.html()
        let userStr= sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr)
        this.form.authorId = user.id
        request.post("/article",this.form).then(res=> {
          this.loadMsg(res)
          this.load()
          this.dialogVisible=false
        })

      }


    },
    handleContent(row){
      this.contents=row
      this.vcontent=true
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
     //  this.$nextTick(()=>{
     //    if (!editor){
     //          editor.config.uploadImgServer = "http://localhost:8090/photo/upload"//配置图片上传地址
     //          editor.config.uploadFileName = "file"
     //          editor.create()
     //  }else {
     //    editor.txt.html("")
     //  }
     //  editor.txt.html(row.content)
     //  }
     // )
      this.$nextTick(()=>{  //对未来元素的操作
            if (!editor){
              editor = new E('#div1')//创建wangeditor对象
              editor.config.uploadImgServer = "http://localhost:8090/photo/upload"//配置图片上传地址
              editor.config.uploadFileName = "file"
              editor.config.excludeMenus = [
                'emoticon',
                'video',
              ]
              editor.create()
            }else {//如果已有editor对象则清空其中的内容
              editor.txt.html("")
            }
            editor.txt.html(row.content)
          }
      )
    },
    handleDelete(id){
      request.delete("/article/"+id).then(res=>{
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
