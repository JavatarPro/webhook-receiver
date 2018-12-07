/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver

import pro.javatar.webhook.receiver.config.WebHookConfig
import groovy.util.logging.Slf4j
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@Slf4j("logger")
@RunWith(SpringRunner)
@SpringBootTest
class WebHookReceiverApplicationTests {

	@Autowired
	WebHookConfig webHookConfig

	@Test
	void contextLoads() {
		logger.info(webHookConfig.toString())
	}

}
