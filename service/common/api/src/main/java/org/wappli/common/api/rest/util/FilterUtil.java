package org.wappli.common.api.rest.util;

import org.wappli.common.api.rest.filter.Filter;
import org.wappli.common.api.rest.filter.RangeFilter;
import org.wappli.common.api.rest.filter.StringFilter;
import org.wappli.common.api.rest.util.params.RequestParameter;
import org.apache.commons.lang.StringUtils;

public final class FilterUtil {

  private FilterUtil() {
  }

  public static RequestParameter toRequestParam(String attributeName, Filter filter) {
    if (filter != null) {
      //Filter
      if (filter.getEquals() != null) {
        return new RequestParameter(attributeName + ".equals", filter.getEquals());
      }
      if (filter.getIn() != null) {
        return new RequestParameter(attributeName + ".in", StringUtils.join(filter.getIn(), ","));
      }
      if (filter.getSpecified() != null) {
        return new RequestParameter(attributeName + ".specified", filter.getSpecified());
      }
      //RangeFilter
      if (filter instanceof RangeFilter) {
        final RangeFilter rangeFilter = (RangeFilter) filter;

        if (rangeFilter.getGreaterThan() != null) {
          return new RequestParameter(attributeName + ".greaterThan", rangeFilter.getGreaterThan());
        }
        if (rangeFilter.getGreaterOrEqualThan() != null) {
          return new RequestParameter(attributeName + ".greaterOrEqualThan",
              rangeFilter.getGreaterOrEqualThan());
        }
        if (rangeFilter.getLessThan() != null) {
          return new RequestParameter(attributeName + ".lessThan", rangeFilter.getLessThan());
        }
        if (rangeFilter.getLessOrEqualThan() != null) {
          return new RequestParameter(attributeName + ".lessOrEqualThan",
              rangeFilter.getLessOrEqualThan());
        }
      }
      //StringFilter
      if (filter instanceof StringFilter) {
        final StringFilter stringFilter = (StringFilter) filter;
        if (stringFilter.getContains() != null) {
          return new RequestParameter(attributeName + ".contains", stringFilter.getContains());
        }
      }
    }
    return null;
  }
}
