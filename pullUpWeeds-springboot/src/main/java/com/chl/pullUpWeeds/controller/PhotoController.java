package com.chl.pullUpWeeds.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
@Api("图片控制器")
@RestController
@RequestMapping("/photo")
@CrossOrigin(origins = {"http://localhost:9876","http://localhost:9800"})
public class PhotoController {

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";


    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public JSON upload(MultipartFile file, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        String originalFilename = file.getOriginalFilename();
        String uuid = IdUtil.fastUUID();
        String rootFilePath = System.getProperty("user.dir")+"/pullUpWeeds-springboot/src/main/resources/photos/"+uuid+"_"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        JSONObject json = new JSONObject();
        json.put("errno",0);
        JSONArray data = new JSONArray();
        JSONObject pho = new JSONObject();
        pho.put("url",ip+":"+port+"/photo/"+uuid);
        data.add(pho);
        json.put("data",data);

        return json;
    }


    @ApiOperation("下载图片")
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) throws UnsupportedEncodingException {

        OutputStream os;
        String basePath =  System.getProperty("user.dir")+"\\pullUpWeeds-springboot\\src\\main\\resources\\photos\\";
        List<String> strings = FileUtil.listFileNames(basePath);
        String fileName = strings.stream().filter(name -> name.contains(flag)).findAny().orElse("");//找到流中包含相同uuid的文件


        response.addHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
        response.setContentType("application/octet-stream");

        byte[] bytes = FileUtil.readBytes(basePath + fileName);
        try  {
            os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        }catch (Exception e){
            System.out.println("下载失败");
        }


    }
}
