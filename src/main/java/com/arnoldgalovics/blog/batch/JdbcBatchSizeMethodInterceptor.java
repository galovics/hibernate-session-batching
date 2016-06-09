package com.arnoldgalovics.blog.batch;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.hibernate.internal.AbstractSharedSessionContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

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
        } finally {
            try {
                final Field field = AbstractSharedSessionContract.class.getDeclaredField("jdbcBatchSize");
                field.setAccessible(true);
                ReflectionUtils.setField(field, session, originalBatchSize);
            } catch (final Exception e) {
                logger.error("Error when setting original jdbc batch size for the session.", e);
            }
        }
        return result;
    }
}
