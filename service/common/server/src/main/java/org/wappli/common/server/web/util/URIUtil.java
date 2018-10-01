package org.wappli.common.server.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class URIUtil {
    private static Logger log = LoggerFactory.getLogger(URIUtil.class);

    public static URI createURI(String str) {
        URI uri;
        try {
            uri = new URI(str);
        } catch (URISyntaxException e) {
            log.error("wrong URI syntax {}", str, e);

            throw new RuntimeException(e);
        }

        return uri;
    }
}
