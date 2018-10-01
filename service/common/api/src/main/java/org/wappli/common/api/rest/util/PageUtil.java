package org.wappli.common.api.rest.util;

import org.wappli.common.api.rest.dto.input.OrderDTO;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

public final class PageUtil {

  private static final String SORT = "sort";
  private static final String SIZE = "size";
  private static final String PAGE = "page";

  private PageUtil() {
  }

  public static MultiValueMap<String, String> getPagingParams(final PageableDTO pageable) {
      final LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

      if (pageable != null) {
          if (pageable.getSort() != null && !pageable.getSort().isEmpty()) {
              map.put(SORT, getSort(pageable.getSort()));
          }
          map.add(SIZE, String.valueOf(pageable.getSize()));
          map.add(PAGE, String.valueOf(pageable.getPage()));
      }
      return map;
  }

  private static List<String> getSort(List<OrderDTO> orderDTOList){
      return orderDTOList.stream().map(OrderDTO::toString).collect(Collectors.toList());
  }
}
