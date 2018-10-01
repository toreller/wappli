package org.wappli.common.server.web.rest;

import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.dto.input.OrderDTO;
import org.wappli.common.api.rest.util.HasId;
import org.wappli.common.server.domain.AbstractEntity;
import org.wappli.common.server.logging.MessageLogger;
import org.wappli.common.server.service.CrudService;
import org.wappli.common.server.service.mapper.IOEntityMapper;
import org.wappli.common.server.service.query.QueryService;
import org.wappli.common.server.web.rest.controllerlogic.ControllerLogic;
import org.wappli.common.server.web.rest.controllerlogic.LoggingControllerLogic;
import org.wappli.common.server.web.rest.controllerlogic.CrudControllerLogic;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.wappli.common.server.config.Constants.BE_API_V;

@RequestMapping(value = {BE_API_V})
public abstract class ResourceWithControllerLogic<ENTITY extends AbstractEntity & HasId,
        EDTO extends EntityDTO,
        CRITERIA,
        SERVICE extends CrudService<ENTITY>,
        QUERY_SERVICE extends QueryService<ENTITY, CRITERIA>,
        MAPPER extends IOEntityMapper<EDTO, ENTITY>> {
    protected ControllerLogic<ENTITY, EDTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER> controllerLogic;

    public ResourceWithControllerLogic(ControllerLogic<ENTITY, EDTO, CRITERIA, SERVICE, QUERY_SERVICE, MAPPER> controllerLogic) {
        this.controllerLogic = controllerLogic;
    }

    protected static Sort getSort(final List<OrderDTO> sort) {
        if (sort != null && !sort.isEmpty()) {
            return new Sort(sort.stream()
                    .filter(sortList -> StringUtils.isNotEmpty(sortList.getName()))
                    .map(sortList ->
                            new Sort.Order(sortList.getDirection() != null
                                    ? Sort.Direction.fromOptionalString(sortList.getDirection().getUrlEncodedName()).orElse(null)
                                    : null,
                                    sortList.getName().trim())
                        ).collect(Collectors.toList()));
        }
        return null;
    }

    protected static <_ENTITY extends AbstractEntity & HasId,
            _EDTO extends EntityDTO,
            _CRITERIA,
            _SERVICE extends CrudService<_ENTITY>,
            _QUERY_SERVICE extends QueryService<_ENTITY, _CRITERIA>,
            _MAPPER extends IOEntityMapper<_EDTO, _ENTITY>>
                ControllerLogic<_ENTITY, _EDTO, _CRITERIA, _SERVICE, _QUERY_SERVICE, _MAPPER>
        createControllerLogic(_SERVICE service, _QUERY_SERVICE queryService, _MAPPER mapper,
                              MessageLogger log, String entityName, String entityUrl) {
        return new LoggingControllerLogic<>(
                entityName,
                new CrudControllerLogic<>(entityName,
                        entityUrl,
                        service,
                        queryService,
                        mapper
                ),
                log
        );
    }
}
