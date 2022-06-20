<template >
  <div >
    <div style="padding: 10px">

      <el-table :data="tableData" border  stripe style="width: 100%">
<!--        <el-table-column prop="id" label="ID" sortable=""> </el-table-column>-->
<!--        <el-table-column prop="title" label="标题" sortable=""> </el-table-column>-->
        <!--        <el-table-column prop="content" label="内容" sortable=""></el-table-column>-->
<!--        <el-table-column prop="user.nickname" label="作者" sortable=""></el-table-column>-->
<!--        <el-table-column prop="time" label="发表时间" sortable=""></el-table-column>-->

        <el-table-column  label="文章列表">
          <template #default="scope">
            <el-row style="height: 40px">
              <el-col :span="24">  <div style="line-height: 40px" ><h2 v-text="scope.row.title" ></h2> </div></el-col>
            </el-row>
            <el-divider></el-divider>
            <el-row style="height: 100px">
              <el-col v-if="getImgSrc(scope.row.content)" :span="4" > <img style="width: 100px;"  :src="getImgSrc(scope.row.content)"></el-col>
              <el-col :span="20"><div class="limit" v-html="con(scope.row.content)"></div></el-col>
            </el-row>
            <el-row>
              <el-col :span="22"><div></div></el-col>
              <el-col :span="2">  <div style="text-align: left"><el-button type="text" @click="comment(scope.row.id)">阅读全文<i class="el-icon-arrow-down"></i></el-button></div></el-col>
            </el-row>

          </template>
        </el-table-column>

      </el-table>
      <div style="margin: 10px">
        <el-row>
          <el-col :span="20">
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
          </el-col>
          <el-col :span="4"><el-button @click="add">发布文章</el-button></el-col>
        </el-row>



        <el-dialog title="添加文章" v-model="dialogVisible" width="60%">
          <el-form  :model="form" label-width="120px">

            <el-form-item label="标题" >
              <el-input  v-model="form.title" style="width: 80%;"></el-input>
            </el-form-item>

            <div id="div1">

            </div>
          </el-form>
          <span slot="footer" class="dialog-footer">
              <el-button @click="closeDia">取 消</el-button>
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
  props:['search'],
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
      tableData: [

      ]
    }
  },
  watch:{
    search: 'load'
  },
  name: 'MainPage',
  components: {

  },

  created() {
    this.load()
  },

  methods:{

    closeDia(){
      this.dialogVisible=false
    },
    comment(articleId){
      this.$router.push("/DetailedArticle/"+articleId);
    },
    getImgSrc: function (e) {//提取content中的图片
      const RegEx = /(?<=(img src="))[^"]*?(?=")/gims;
      e.match(RegEx)
      if (e.match(RegEx)!=null)
        return e.match(RegEx)[0]
      return ""
    },
    con(val) {//提取content中的文字部分
      let str = val

      return str.replace(/<[^>]*>|/g, "");
    },
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

        this.form.content=editor.txt.html()
        let userStr= sessionStorage.getItem("user") || "{}"
        let user = JSON.parse(userStr)

        this.form.authorId = user.id
        request.post("/article",this.form).then(res=> {
          this.loadMsg(res)
          this.load()
          this.dialogVisible=false
        })




    },
    handleContent(row){
      this.contents=row
      this.vcontent=true
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

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
<style>
.limit{
  width:400px;
  overflow:hidden; /*超出部分隐藏*/
  white-space:nowrap; /*让文本强制在一行不换行*/
  text-overflow:ellipsis;/*显示省略号来代表被修剪的文本*/
}
/*上面四个属性缺一不可*/
</style>

