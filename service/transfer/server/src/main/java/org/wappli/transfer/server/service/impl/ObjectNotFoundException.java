package org.wappli.transfer.server.service.impl;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(Long id, Class<?> type) {
        super("object " + type.getSimpleName() + " with id " + id + " not found");
    }
}
