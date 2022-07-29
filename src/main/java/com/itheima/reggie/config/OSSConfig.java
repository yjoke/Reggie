package com.itheima.reggie.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HeYunjia
 */

@Configuration
public class OSSConfig {

    @Value("${oss.endpoint}")
    String endpoint;

    @Value("${oss.accessKeyId}")
    String accessKeyId;

    @Value("${oss.accessKeySecret}")
    String accessKeySecret;

    @Bean
    public OSS ossClient() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setSupportCname(true);
        return new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret, conf);
    }
}
