package com.arnoldgalovics.blog.batch;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.Transactional;

public class JdbcBatchSizeAdvisor extends AbstractPointcutAdvisor {
    private static final long serialVersionUID = -8466931120118556794L;

    private final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcutAdvisor() {
        private static final long serialVersionUID = 4458743456797695036L;

        @Override
        public boolean matches(final Method method, final Class<?> targetClass) {
            return method.isAnnotationPresent(JdbcBatchSize.class) && method.isAnnotationPresent(Transactional.class);
        }
    };

    @Autowired
    private JdbcBatchSizeMethodInterceptor interceptor;

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return interceptor;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
