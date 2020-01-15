/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pro.javatar.commons.reader.JsonReader;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BitbucketVcsConverterTest {

    VcsConverter converter;

    String body;

    JsonReader jsonReader = new JsonReader();

    @BeforeAll
    public void setUp() throws Exception {
        converter = new BitbucketVcsConverter();
        body = jsonReader.getStringFromFile("bitbucket/bitbucket-push-event.json");
    }

    @Test
    public void retrieveCommitter() {
        String expected = "Borys Zora";
        String committer = converter.retrieveCommitter(body);
        assertThat(committer, Is.is(expected));
    }

    @Test
    public void retrieveAuthors() {
        Set<String> expected = new HashSet<>(List.of("Andrii Murashkin", "Borys Zora"));
        Set<String> actual = converter.retrieveAuthors(body);
        assertThat(actual, Is.is(expected));
    }

    @Test
    public void retrieveCommittedBranch() {
        String expected = "develop";
        String actual = converter.retrieveCommittedBranch(body);
        assertThat(actual, Is.is(expected));
    }

    @Test
    public void retrieveCommittedRepo() {
        String expected = "work-service";
        String actual = converter.retrieveCommittedRepo(body);
        assertThat(actual, Is.is(expected));
    }

    @Test
    public void retrieveCommittedRepoOwner() {
        String expected = "javatar-pro-work";
        String actual = converter.retrieveCommittedRepoOwner(body);
        assertThat(actual, Is.is(expected));
    }
}