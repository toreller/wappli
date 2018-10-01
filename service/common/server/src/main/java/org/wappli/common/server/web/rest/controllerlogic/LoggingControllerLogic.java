package org.wappli.common.server.web.rest.controllerlogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.dto.output.IdDTO;
import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;
import org.wappli.common.server.logging.crud.CrudLogMessage;
import org.wappli.common.server.logging.LogMessage;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.service.CrudService;
import org.wappli.common.server.service.mapper.IOEntityMapper;
import org.wappli.common.server.service.query.QueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

public class LoggingControllerLogic<ENTITY extends AbstractEntity & HasId,
        EDTO extends EntityDTO,
        CRITERIA,
        SERVICE extends CrudService<ENTITY>,
        QUERY_SERVICE extends QueryService<ENTITY, CRITERIA>,
        MAPPER extends IOEntityMapper<EDTO, ENTITY>>
        implements ControllerLogic<ENTITY, EDTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER> {
    private static final Logger log = LoggerFactory.getLogger(LoggingControllerLogic.class);
    private final ControllerLogic<ENTITY, EDTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER> controllerLogic;
    private final MessageLogger messageLogger;
    private final String entityName;

    public LoggingControllerLogic(String entityName, ControllerLogic<ENTITY, EDTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER> controllerLogic, MessageLogger log) {
        this.controllerLogic = controllerLogic;
        this.messageLogger = log;
        this.entityName = entityName;
    }

    @Override
    public ResponseEntity<IdDTO> create(EDTO inputDTO) throws URISyntaxException {
        ResponseEntity<IdDTO> response;

        try {
            response = controllerLogic.create(inputDTO);
        } catch(Exception exception) {
            logException(CrudLogMessage.OBJECT_CREATE_OK, null, exception.getMessage());

            throw exception;
        }

        logSuccessOrFailure(response, CrudLogMessage.OBJECT_CREATE_OK, CrudLogMessage.OBJECT_CREATE_NOK, extractId(response), null);

        return response;
    }

    @Override
    public ResponseEntity<IdDTO> update(Long id, EDTO entityInputDTO) {
        ResponseEntity<IdDTO> response;

        try {
            response = controllerLogic.update(id, entityInputDTO);
        } catch(Exception exception) {
            logException(CrudLogMessage.OBJECT_UPDATE_NOK, id, exception.getMessage());

            throw exception;
        }

        logSuccessOrFailure(response, CrudLogMessage.OBJECT_UPDATE_OK, CrudLogMessage.OBJECT_UPDATE_NOK, id, id);

        return response;
    }

    @Override
    public ResponseEntity<List<EntityWithIdOutputDTO<EDTO>>> getAll(CRITERIA criteria, Pageable pageable) {
        ResponseEntity<List<EntityWithIdOutputDTO<EDTO>>> response;

        try {
            response = controllerLogic.getAll(criteria, pageable);
        } catch (Exception exception) {
            logException(CrudLogMessage.OBJECT_READ_NOK, null, exception.getMessage());

            throw exception;
        }

        logSuccessOrFailure(response, CrudLogMessage.OBJECT_READ_OK, CrudLogMessage.OBJECT_READ_NOK, null, null);

        return response;
    }

    @Override
    public ResponseEntity<EntityWithIdOutputDTO<EDTO>> get(Long id) {
        ResponseEntity<EntityWithIdOutputDTO<EDTO>> response;

        try {
            response = controllerLogic.get(id);
        } catch (Exception exception) {
            logException(CrudLogMessage.OBJECT_READ_NOK, id, exception.getMessage());

            throw exception;
        }

        logSuccessOrFailure(response, CrudLogMessage.OBJECT_READ_OK, CrudLogMessage.OBJECT_READ_NOK, id, id);

        return response;
    }

    @Override
    public ResponseEntity<IdDTO> delete(Long id) {
        ResponseEntity<IdDTO> response;

        try {
            response = controllerLogic.delete(id);
        } catch (Exception exception) {
            logException(CrudLogMessage.OBJECT_DELETE_NOK, id, exception.getMessage());

            throw exception;
        }

        logSuccessOrFailure(response, CrudLogMessage.OBJECT_DELETE_NOK, CrudLogMessage.OBJECT_DELETE_OK, id, id);

        return response;
    }

    private Long extractId(ResponseEntity<IdDTO> response) {
        return response != null
                ? (response.getBody() != null)
                    ? response.getBody().getId()
                    : null
                : null;
    }

    private void logSuccessOrFailure(ResponseEntity<?> response, LogMessage okMessage, CrudLogMessage nokMessage, Long okId, Long nokId) {
        if (isSuccessful(response)) {
            messageLogger.log(log, okMessage, entityName, formatId(okId));
        } else {
            messageLogger.log(log, nokMessage, entityName, formatId(nokId), formatHttpStatus(response.getStatusCode()));
        }
    }

    private String formatId(Long id) {
        return id != null ? id.toString() : "";
    }

    private void logException(CrudLogMessage logMessage, Long id, String message) {
        messageLogger.log(log, logMessage, entityName, formatId(id), message);
    }

    private String formatHttpStatus(HttpStatus status) {
        return "" + status.value() + " " + status.getReasonPhrase();
    }


    private boolean isSuccessful(ResponseEntity<?> response) {
        return response.getStatusCode().is2xxSuccessful();
    }
}
