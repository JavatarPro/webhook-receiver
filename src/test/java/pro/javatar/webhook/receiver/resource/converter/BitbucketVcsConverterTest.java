/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import pro.javatar.commons.reader.JsonReader;

import java.util.*;

import static org.junit.Assert.assertThat;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
class BitbucketVcsConverterTest {

    VcsConverter converter;

    Map body;

    JsonReader jsonReader = new JsonReader();

    @Before
    void setUp() throws Exception {
        converter = new BitbucketVcsConverter();
        body = jsonReader.getObjectFromFile("bitbucket/bitbucket-push-event.json", HashMap.class);
    }

    @Test
    void retrieveCommitter() {
        String expected = "bzora";
        String committer = converter.retrieveCommitter(body);
        assertThat(committer, Is.is(expected));
    }

    @Test
    void retrieveAuthors() {
        Set<String> expected = new HashSet<>(List.of("andrii_murashkin", "bzora"));
        Set<String> actual = converter.retrieveAuthors(body);
        assertThat(actual, Is.is(expected));
    }

    @Test
    void retrieveCommittedBranch() {
        String expected = "develop";
        String actual = converter.retrieveCommittedBranch(body);
        assertThat(actual, Is.is(expected));
    }

    @Test
    void retrieveCommittedRepo() {
        String expected = "work-service";
        String actual = converter.retrieveCommittedRepo(body);
        assertThat(actual, Is.is(expected));
    }

    @Test
    void retrieveCommittedRepoOwner() {
        String expected = "javatar-pro-work";
        String actual = converter.retrieveCommittedRepoOwner(body);
        assertThat(actual, Is.is(expected));
    }
}