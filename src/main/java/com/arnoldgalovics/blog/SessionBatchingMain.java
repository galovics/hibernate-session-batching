package com.arnoldgalovics.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SessionBatchingMain {
    private static final Logger logger = LoggerFactory.getLogger(SessionBatchingMain.class);

    public static void main(final String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/session-level-batching.xml");
        final BatchingService service = context.getBean(BatchingService.class);

        logger.info("Executing non-batching update");
        service.updateWithoutBatching();
        logger.info("Executed non-batching update");

        logger.info("Executing batching update");
        service.updateWithSessionBatching();
        logger.info("Executed batching update");
    }
}
