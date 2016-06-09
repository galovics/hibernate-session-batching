package com.arnoldgalovics.blog.batch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;

public class JdbcBatchSizeMethodInterceptor implements MethodInterceptor {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Session session = entityManager.unwrap(Session.class);
        final int originalBatchSize = session.getJdbcBatchSize() == null ? 0 : session.getJdbcBatchSize();
        session.setJdbcBatchSize(invocation.getMethod().getAnnotation(JdbcBatchSize.class).value());
        Object result = null;
        try {
            result = invocation.proceed();
        } finally {
            session.setJdbcBatchSize(originalBatchSize);
        }
        return result;
    }
}
