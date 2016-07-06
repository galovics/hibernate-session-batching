package com.arnoldgalovics.blog.batch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcBatchSizeMethodInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JdbcBatchSizeMethodInterceptor.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Session session = entityManager.unwrap(Session.class);
        final Integer originalBatchSize = session.getJdbcBatchSize();
        session.setJdbcBatchSize(invocation.getMethod().getAnnotation(JdbcBatchSize.class).value());
        Object result = null;
        try {
            result = invocation.proceed();
            entityManager.flush();
        } finally {
            session.setJdbcBatchSize(originalBatchSize);
        }
        return result;
    }
}
