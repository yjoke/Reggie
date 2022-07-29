package com.itheima.reggie.controller;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.itheima.reggie.dto.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("common")
public class ImageController {

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.folder}")
    private String folder;

    @Resource
    private OSS oss;

    /**
     * 上传图片到阿里云服务器
     *
     * @param file 前端传来的文件
     * @return 返回文件名
     */
    @SneakyThrows
    @PostMapping("upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) {

        // 获取文件名
        String fileName = file.getOriginalFilename();
        if (fileName == null) return R.error("上传失败");

        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        // 生成随机文件名
        fileName = UUID.randomUUID() + suffix;

        // 上传文件
        oss.putObject(bucketName, folder + fileName, file.getInputStream());

        // 返回文件路径
        return R.success(fileName);
    }

    /**
     * 从阿里云 oss 获取文件传给前端
     *
     * @param fileName 文件名
     * @return 返回文件
     */
    @SneakyThrows
    @GetMapping("download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("name") String fileName) {

        // 获取输入流
        InputStream inputStream = oss.getObject(bucketName, folder + fileName)
                                     .getObjectContent();

        // 返回对象
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream));
    }

}
