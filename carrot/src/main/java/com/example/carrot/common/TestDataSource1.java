//package com.example.carrot.common;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//
//@Configuration // 注册到springboot容器中
////@MapperScan(basePackages = "com.bdqn.dao", sqlSessionFactoryRef = "test1SqlSessionFactory")
//public class TestDataSource1 {
//
//    /**
//     *
//     * @methodDesc: 功能描述:(配置test1数据库)
//     *
//     */
//    @Bean(name = "test1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.test1")//把同类的配置信息自动封装成实体类
//    @Primary//自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常
//    public DataSource testDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    /**
//     *
//     * @methodDesc: 功能描述:(test1 sql会话工厂)
//     */
//    @Bean(name = "test1SqlSessionFactory")
//    @Primary//自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常
//    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        // bean.setMapperLocations(
//        // new
//        // PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
//        return bean.getObject();
//    }
//
//    /**
//     *
//     * @methodDesc: 功能描述:(test1 事物管理)
//     *
//     */
//    @Bean(name = "test1TransactionManager")
//    @Primary//自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "test1SqlSessionTemplate")
//    @Primary//自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常
//    public SqlSessionTemplate testSqlSessionTemplate(
//            @Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
