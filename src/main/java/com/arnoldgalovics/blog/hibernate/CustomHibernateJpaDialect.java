package com.arnoldgalovics.blog.hibernate;

import javax.persistence.PersistenceException;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

public class CustomHibernateJpaDialect extends HibernateJpaDialect {
    private static final long serialVersionUID = -3483592073952499113L;

    @Override
    protected FlushMode prepareFlushMode(final Session session, final boolean readOnly) throws PersistenceException {
        final FlushMode flushMode = session.getHibernateFlushMode();
        if (readOnly) {
            // We should suppress flushing for a read-only transaction.
            if (!flushMode.equals(FlushMode.MANUAL)) {
                session.setFlushMode(FlushMode.MANUAL);
                return flushMode;
            }
        } else {
            // We need AUTO or COMMIT for a non-read-only transaction.
            if (flushMode.lessThan(FlushMode.COMMIT)) {
                session.setFlushMode(FlushMode.AUTO);
                return flushMode;
            }
        }
        // No FlushMode change needed...
        return null;
    }
}
