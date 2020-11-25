package top.gmfcj.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import top.gmfcj.datasource.DynamicDataSource;

import java.io.IOException;

@Configuration
public class MyBatisConfig {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DynamicDataSource dynamicDataSource) throws IOException {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setPlugins(new Interceptor[]{paginationInterceptor()});
        // xml
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(mapperLocations));
        return bean;
    }


    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }

//    @Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.setPort(8080);
//        return tomcat;
//    }


//    @Bean
//    public DataSource dataSource()  {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(Driver.class.getName());
//        dataSource.setUsername("root");
//        dataSource.setPassword("password");
//        dataSource.setUrl("jdbc:mysql://192.168.222.134:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true");
//        return dataSource;
//    }
}
