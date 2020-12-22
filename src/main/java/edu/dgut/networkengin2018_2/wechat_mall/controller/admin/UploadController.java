package edu.dgut.networkengin2018_2.wechat_mall.controller.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import edu.dgut.networkengin2018_2.wechat_mall.util.Result;
import edu.dgut.networkengin2018_2.wechat_mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class UploadController {

    /**
     * 阿里云数据
     */
    private final String url = "https://oss-cn-shenzhen.aliyuncs.com";
    private final String accessKeyId = "LTAI4GDY5UcnXz1zHQqZn1v2";
    private final String accessKeySecret = "UycrSwfN8YaAkH6Zi0CBd51qHZno5h";
    private final String bucketName = "my-blog-vog";
    private final String objectName = "wechat-mini/upload/";


    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename(); //文件名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")); //获取后缀名
        String fileType = suffixName.substring(1);  //文件类型，暂时后缀名代替
        String contentType = "image/" + fileType; //http 头

        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        String path = objectName + newFileName;

        OSS ossClient = new OSSClientBuilder().build(url, accessKeyId, accessKeySecret); //阿里云实例
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
//        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        try {
            ossClient.putObject(bucketName, path, new ByteArrayInputStream(file.getBytes()), metadata);
            Result resultSuccess = ResultGenerator.genSuccessResult();
            resultSuccess.setData("https://oss.ghovos.top/wechat-mini/upload/" + newFileName);
            ossClient.shutdown();
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            ossClient.shutdown();
            return ResultGenerator.genFailResult("上传文件失败");
        }

    }

    @PostMapping({"/upload/files"})
    @ResponseBody
    public Result uploadV2(HttpServletRequest httpServletRequest) throws URISyntaxException {
        List<MultipartFile> multipartFiles = new ArrayList<>(8);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        if (multipartResolver.isMultipart(httpServletRequest)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpServletRequest;
            Iterator<String> iter = multiRequest.getFileNames();
            int total = 0;
            while (iter.hasNext()) {
                if (total > 5) {
                    return ResultGenerator.genFailResult("最多上传5张图片");
                }
                total += 1;
                MultipartFile file = multiRequest.getFile(iter.next());
                multipartFiles.add(file);
            }
        }
        if (CollectionUtils.isEmpty(multipartFiles)) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (multipartFiles != null && multipartFiles.size() > 5) {
            return ResultGenerator.genFailResult("最多上传5张图片");
        }
        List<String> fileNames = new ArrayList(multipartFiles.size());
        for (int i = 0; i < multipartFiles.size(); i++) {
            String fileName = multipartFiles.get(i).getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String fileType = suffixName.substring(1);  //文件类型，暂时后缀名代替
            String contentType = "image/" + fileType; //http 头

            //生成文件名称通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random r = new Random();
            StringBuilder tempName = new StringBuilder();
            tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
            String newFileName = tempName.toString();

            String path = objectName + newFileName;

            OSS ossClient = new OSSClientBuilder().build(url, accessKeyId, accessKeySecret); //阿里云实例
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
//        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
            try {
                ossClient.putObject(bucketName, path, new ByteArrayInputStream(multipartFiles.get(i).getBytes()), metadata);
                Result resultSuccess = ResultGenerator.genSuccessResult();
                fileNames.add("https://oss.ghovos.top/wechat-mini/upload/"+newFileName);
            } catch (IOException e) {
                e.printStackTrace();
                ossClient.shutdown();
                return ResultGenerator.genFailResult("上传文件失败");
            }
        }
        Result resultSuccess = ResultGenerator.genSuccessResult();
        resultSuccess.setData(fileNames);
        return resultSuccess;
    }

/*

    public static void main(String[] args) {
        final String url = "https://oss-cn-shenzhen.aliyuncs.com";
        final String accessKeyId = "LTAI4GDY5UcnXz1zHQqZn1v2";
        final String accessKeySecret = "UycrSwfN8YaAkH6Zi0CBd51qHZno5h";
        final String bucketName = "my-blog-vog";
        final String objectName = "wechat-mini/123.txt";
        OSS ossClient = new OSSClientBuilder().build(url, accessKeyId, accessKeySecret);
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        String content = "Hello OSS";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");
//        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()), metadata);


        // 关闭OSSClient。
        ossClient.shutdown();
    }

*/

}