package com.arnoldgalovics.blog.hibernate;

import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public class CustomJpaVendorAdapter extends HibernateJpaVendorAdapter {
    private final HibernateJpaDialect jpaDialect = new CustomHibernateJpaDialect();

    @Override
    public HibernateJpaDialect getJpaDialect() {
        return jpaDialect;
    }
}
