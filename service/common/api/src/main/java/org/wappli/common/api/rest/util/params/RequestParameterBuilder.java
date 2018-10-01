package org.wappli.common.api.rest.util.params;

import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.filter.Filter;
import org.wappli.common.api.rest.util.FilterUtil;
import org.wappli.common.api.rest.util.PageUtil;
import org.springframework.util.LinkedMultiValueMap;

public class RequestParameterBuilder {

  private LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

  public RequestParameterBuilder addParameter(String attributeName, Filter filter) {
    if (filter != null) {
      final RequestParameter idParam = FilterUtil.toRequestParam(attributeName, filter);
      params.add(idParam.getName(), idParam.getValue());
    }
    return this;
  }

  public RequestParameterBuilder addPagination(PageableDTO pageable) {
    params.putAll(PageUtil.getPagingParams(pageable));
    return this;
  }

  public LinkedMultiValueMap<String, String> build() {
    return params;
  }
}
