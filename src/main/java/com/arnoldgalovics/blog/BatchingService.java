package com.arnoldgalovics.blog;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.arnoldgalovics.blog.batch.JdbcBatchSize;

public class BatchingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateWithoutBatching() {
        final List<BasicEntity> entities = entityManager.createQuery("FROM BasicEntity", BasicEntity.class).getResultList();
        entities.forEach(basic -> basic.setStr("b"));
    }

    @Transactional
    @JdbcBatchSize(15)
    public void updateWithSessionBatching() {
        final List<BasicEntity> entities = entityManager.createQuery("FROM BasicEntity", BasicEntity.class).getResultList();
        entities.forEach(basic -> basic.setStr("c"));
    }
}
