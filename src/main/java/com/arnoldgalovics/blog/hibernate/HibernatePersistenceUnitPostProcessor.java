package com.arnoldgalovics.blog.hibernate;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

public class HibernatePersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    @Override
    public void postProcessPersistenceUnitInfo(final MutablePersistenceUnitInfo pui) {
        pui.addProperty("hibernate.order_updates", Boolean.TRUE.toString());
        pui.addProperty("hibernate.jdbc.batch_versioned_data", Boolean.TRUE.toString());
    }

}
