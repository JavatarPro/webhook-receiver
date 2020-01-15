/*
 * Copyright (c) 2019 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Borys Zora
 * @version 2020-01-15
 */
@RestController
@RequestMapping
public class SwaggerRedirectResource {

    @GetMapping("/")
    public void swaggerUI(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

}
