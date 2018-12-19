package org.wappli.auth.api.client;

import org.wappli.auth.api.criteria.UzerCriteria;
import org.wappli.auth.api.dto.entities.UzerDTO;
import org.wappli.auth.api.interfaces.UzerInterface;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.dto.output.EntityWithIdOutputDTO;
import org.wappli.common.api.rest.util.CriteriaMapBuilder;

import java.util.List;

import static org.wappli.auth.api.constant.Constants.API_BASE_URL;

@FeignClient(name = "auth", url = "${application.rest.auth}", path = API_BASE_URL)
public interface UzerClient extends UzerInterface {

    @Override
    default ResponseEntity<List<EntityWithIdOutputDTO<UzerDTO>>> getAllUsers(UzerCriteria criteria, PageableDTO pageableDTO) {
        return getAllUsers(CriteriaMapBuilder.buildParam(criteria, pageableDTO));
    }

    @Deprecated
    @GetMapping(ENTITY_URL)
    ResponseEntity<List<EntityWithIdOutputDTO<UzerDTO>>> getAllUsers(@RequestParam MultiValueMap<String, String> params);
}
