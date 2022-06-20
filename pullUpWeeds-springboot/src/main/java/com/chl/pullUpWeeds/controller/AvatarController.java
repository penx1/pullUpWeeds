package com.chl.pullUpWeeds.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
@Api("头像控制器")
@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    @ApiOperation("上传头像")
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uuid = IdUtil.fastUUID();
        String rootFilePath = System.getProperty("user.dir")+"/pullUpWeeds-springboot/src/main/resources/avatar/"+uuid+"_"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        return new  Result<String>(ResultInfo.SUCCESS_UPLOAD_PHOTO,ip+":"+port+ "/avatar/" +uuid);
    }


    @ApiOperation("下载头像")
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) throws UnsupportedEncodingException {

        OutputStream os;
        String basePath =  System.getProperty("user.dir")+"\\pullUpWeeds-springboot\\src\\main\\resources\\avatar\\";
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