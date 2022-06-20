<template >
  <div style="padding: 10px">
    <div >
      <el-card>
        <el-row>
          <el-col :span="4">
            <el-card style="margin-right: 10px">
              <el-row>
                <el-avatar shape="square" :size="100" :src="article.user.avatar"></el-avatar>
              </el-row>
              <div style="text-align: center">
                <h2 >{{article.user.nickname}}</h2>

              </div>
            </el-card>
          </el-col>
          <el-col :span="20"><el-card> <div v-html="this.article.content"></div></el-card> </el-col>
        </el-row>
      </el-card>
    </div>
    <div >

      <el-table :data="tableData" border stripe   style="width: 100%">

        <el-table-column  label="评论">
          <template #default="scope">
            <el-row >
              <el-col :span="2" > <el-avatar shape="square" :size="small" :src="scope.row.user.avatar"></el-avatar></el-col>
              <el-col :span="10"> <el-divider direction="vertical"></el-divider>{{scope.row.user.nickname}}</el-col>
              <el-col :span="10"><div ></div></el-col>
              <el-col :span="2"><div >{{scope.row.floor}}楼</div></el-col>
            </el-row>
            <el-divider></el-divider>
            <el-row style="height: 80px">
              <div  v-html="con(scope.row.content)"></div>
            </el-row>
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

      </div>

      <div>
        <el-row>
          <el-col>发表评论</el-col>
        </el-row>
        <el-row>
          <el-col><div id="weditor"></div></el-col>
        </el-row>
        <el-row>
          <el-col><el-button @click="addComment">发布</el-button></el-col>
        </el-row>
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
      article:{
        user:{
          avatar:"",
          nickname:""
        }
      },
      currentPage:1,
      pageSize: 5,
      form: {

      },
      total:0,
      search: '',
      tableData: [

      ]
    }
  },
  name: 'DetailedArticle',
  components: {

  },

  created() {
    this.load()


  },

  methods:{

    addComment(){
      if (editor.txt){//如果编辑器中不为空则发送请求，添加评论

        let userStr= sessionStorage.getItem("user")
        let user = JSON.parse(userStr)
        this.form.content=editor.txt.html()
        this.form.authorId = user.id
        this.form.articleId =this.$route.params.id
        request.post("/comment",this.form).then(res=> {
          this.loadMsg(res)
          this.load()

        })
      }else {
        this.$message({type:"error",message:"评论不能为空"})
      }

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

    load(){

      request.get("/article/"+this.$route.params.id,{
        // get请求需要params，获得文章

      }).then(res =>{
        this.loadMsg(res)
        this.article = res.data

      })

      request.get("/comment/getArticleComments",{
        // get请求需要params，获得评论
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          ArticleId: this.$route.params.id
        }
      }).then(res =>{
        this.loadMsg(res)
        this.tableData = res.data.records
        this.total = res.data.total

      })

      //加载waneditor组件
      this.$nextTick(()=>{


        editor = new E('#weditor')
        editor.config.height = 200
        editor.config.menus = [
          'bold',
          'head',
          'link',
          'italic',
          'emoticon',
          'underline'
        ]
        editor.create()

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