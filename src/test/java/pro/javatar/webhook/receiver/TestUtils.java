/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author : Borys Zora
 * Date Created: 4/8/18 21:22
 */
public class TestUtils {

    public static String getFileAsString(String fileClasspath) throws IOException {
        String fileLocation = TestUtils.class.getClassLoader().getResource(fileClasspath).getFile();
        return new String(Files.readAllBytes(Paths.get(fileLocation))); // TODO can fail on ubuntu
    }

}
