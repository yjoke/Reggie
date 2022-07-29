package com.itheima.reggie.controller;

import cn.hutool.core.lang.UUID;
import com.itheima.reggie.dto.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author HeYunjia
 */

@Slf4j
@RestController
@RequestMapping("common")
public class ImageController {

    @Value("${reggie.path}")
    private String basePath;

    @SneakyThrows
    @PostMapping("upload")
    public R<String> upload(@RequestParam("file") MultipartFile file) {

        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return R.error("error");

        // 获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 路径不存在, 并且创建文件夹失败
        File dir = new File(basePath);
        if (!dir.exists() && !dir.mkdirs()) return R.error("error");

        // 获取一个随机文件名
        String fileName = UUID.randomUUID().toString() + suffix;

        // 上传文件
        file.transferTo(new File(basePath + fileName));

        return R.success(fileName);
    }

    @SneakyThrows
    @GetMapping("old-download")
    public void download(@RequestParam("name") String fileName,
                         HttpServletResponse response) {
        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(new File(basePath + fileName));

        // 设置响应头图片
        response.setContentType("image/jpeg");

        // 获取输出流
        ServletOutputStream outputStream = response.getOutputStream();

        // 输出
        int len;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        outputStream.close();
        fileInputStream.close();
    }

    @SneakyThrows
    @GetMapping("download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("name") String fileName) {

        // 读文件
        String filePath = basePath + fileName;
        FileSystemResource file = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(file.contentLength())
                .body(new InputStreamResource(file.getInputStream()));
    }
}
