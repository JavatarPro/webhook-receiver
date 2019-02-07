webhook-receiver
---

### Gitlab configuration
- Gitlab -> Integrations -> WebHooks
- URL: http://54.93.114.16:7272/gitlab
- Secret Token: **THE_URL** (e.g. /job/common/job/configuration-service/build?token=pipeline)
- Use this token to validate received payloads. It will be sent with the request in the X-Gitlab-Token HTTP header.
- Trigger: Push events
- SSL verification: turn off

### Jenkins configuration
- Jenkins job type: pipeline
- Trigger builds remotely (e.g., from scripts)
- Authentication Token: pipeline
- Use the following URL to trigger build remotely: **THE_URL** 
    (e.g. JENKINS_URL/job/common/job/configuration-service/build?token=TOKEN_NAME 
    or /buildWithParameters?token=TOKEN_NAME)
    
## TODO

- support different version of bitbucket, gitlab
    - add filter that amend content type depends on verison header
- support jenkins webhooks
- add persistence storage (mysql, if not specified h2)

## docker image release
[webhook-receiver on docker hub](https://hub.docker.com/r/javatar/webhook-receiver/)

    mvn clean package
    docker build
    imageName=webhook-receiver
    version=0.1
    image=${imageName}:${version}
    registry=javatar
    docker build -t ${image} .
    docker tag ${image} ${registry}/${image}
    docker push ${registry}/${image}   
      