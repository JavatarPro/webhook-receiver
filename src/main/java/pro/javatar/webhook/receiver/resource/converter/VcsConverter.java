/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
public abstract class VcsConverter {

    private static final Logger logger = LoggerFactory.getLogger(VcsConverter.class);

    public VcsPushRequestBO toVcsPushRequestBO(VcsPushRequestTO pushRequestTO) {
        Map body = pushRequestTO.getBody();
        return new VcsPushRequestBO()
                        .withAuthors(retrieveAuthors(body))
                        .withCommitter(retrieveCommitter(body))
                        .withCommittedBranch(retrieveCommittedBranch(body))
                        .withRepo(retrieveCommittedRepo(body))
                        .withRepoOwner(retrieveCommittedRepoOwner(body));
    }

    abstract String retrieveCommitter(Map<String, Object> body);

    abstract Set<String> retrieveAuthors(Map<String, Object> body);

    abstract String retrieveCommittedBranch(Map<String, Object> body);

    abstract String retrieveCommittedRepo(Map<String, Object> body);

    abstract String retrieveCommittedRepoOwner(Map<String, Object> body);

    public <T> T getByDotNotationFistItem(String line, Map<String, Object> map, Class<T> clazz) {
        var items = getByDotNotation(line, map, List.class);
        if (items == null) return null;
        return (T) items.get(0);
    }

    public Object getByDotNotationFistItem(String line, Map<String, Object> map) {
        var items = getByDotNotation(line, map, List.class);
        if (items == null) return null;
        return items.get(0);
    }

    public String getByDotNotation(String line, Map<String, Object> map) {
        return getByDotNotation(line, map, String.class);
    }

    public <T> T getByDotNotation(String line, Map<String, Object> map, Class<T> clazz) {
        try {
            Map<String, Object> tmp = map;
            String[] items = line.split("\\.");
            for(int i = 0; i < items.length - 1; i++) {
                tmp = (Map<String, Object>) tmp.get(items[i]);
                if (tmp == null) {
                    return null;
                }
            }
            Object result = tmp.get(items[items.length - 1]);
            if(result == null) {
                return null;
            }
            return (T) result;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }
}
