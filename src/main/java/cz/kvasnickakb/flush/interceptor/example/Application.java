package cz.kvasnickakb.flush.interceptor.example;

import cz.kvasnickakb.flush.interceptor.example.optimistic.OptimisticLockingFlushInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author : Daniel Kvasnička
 * @inheritDoc
 * @since : 24.02.2021
 **/
@SpringBootApplication
public class Application {

    @Bean
    @ConditionalOnClass(SqlSessionFactory.class)
    @ConditionalOnMissingBean
    public OptimisticLockingFlushInterceptor optimisticLockingFlushInterceptor() {
        return new OptimisticLockingFlushInterceptor();
    }
}