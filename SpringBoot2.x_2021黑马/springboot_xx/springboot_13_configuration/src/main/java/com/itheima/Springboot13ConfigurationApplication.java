package com.itheima;

import com.alibaba.druid.pool.DruidDataSource;
import config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableConfigurationProperties注解可以将使用@ConfigurationProperties注解对应的类加入Spring容器管控
@EnableConfigurationProperties(ServerConfig.class)
public class Springboot13ConfigurationApplication {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DruidDataSource datasource(){
        DruidDataSource ds = new DruidDataSource();
        //ds.setDriverClassName("com.mysql.jdbc.Driver123");
        return ds;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Springboot13ConfigurationApplication.class, args);

        ServerConfig bean = ctx.getBean(ServerConfig.class);
        System.out.println(bean);
        //ServerConfig(ipAddress=192.168.0.2, port=2345, timeout=-1, serverTimeOut=PT3H, dataSize=10485760B,
        // host=Host(ipAddress=192.168.1.1, port=4567))

        DruidDataSource ds = ctx.getBean(DruidDataSource.class);
        System.out.println(ds.getDriverClassName());
        //com.mysql.jdbc.Driver789
    }

}
