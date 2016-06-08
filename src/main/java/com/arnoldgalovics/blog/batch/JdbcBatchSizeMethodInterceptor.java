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
        session.setJdbcBatchSize(invocation.getMethod().getAnnotation(JdbcBatchSize.class).value());
        return invocation.proceed();
    }
}
