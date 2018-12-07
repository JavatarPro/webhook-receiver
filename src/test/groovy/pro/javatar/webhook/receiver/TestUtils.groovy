/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Author : Borys Zora
 * Date Created: 4/8/18 21:22
 */
class TestUtils {

    static String getFileAsString(String fileClasspath) {
        String fileLocation = TestUtils.class.getClassLoader().getResource(fileClasspath).getFile()
        return new String(Files.readAllBytes(Paths.get(fileLocation)))
    }

}
