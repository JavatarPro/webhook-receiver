package pro.javatar.webhook.receiver.resource;

import org.hamcrest.core.Is;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Borys Zora
 * @version 2019-02-23
 */
@RestController
public class JenkinsServerTestResource {

    @GetMapping(path = "/job/common/job/configuration-service/build")
    ResponseEntity getConfiguration(@RequestParam String token,
                                @RequestParam String repo,
                                @RequestParam String repoOwner,
                                @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        assertThat(token, Is.is("pipeline"));
        assertThat(repo, Is.is("configuration-service"));
        assertThat(repoOwner, Is.is("services"));
        assertThat(auth, Is.is("Basic amVua2luc1dlYkhvb2s6amVua2luc1dlYkhvb2s="));
        return ResponseEntity.ok("repo: " + repo);
    }

    @GetMapping(path = "/job/backend/job/localization-service/buildWithParameters")
    ResponseEntity getLocalization(@RequestParam String token,
                                   @RequestParam String repo,
                                   @RequestParam String repoOwner,
                                   @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        assertThat(token, Is.is("pipeline"));
        assertThat(repo, Is.is("localization-service"));
        assertThat(repoOwner, Is.is("javatar-pro-work"));
        assertThat(auth, Is.is("Basic amVua2luc1dlYkhvb2s6amVua2luc1dlYkhvb2s="));
        return ResponseEntity.ok("repo: " + repo);
    }

}
