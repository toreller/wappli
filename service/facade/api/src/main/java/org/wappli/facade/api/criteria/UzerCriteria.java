package org.wappli.facade.api.criteria;

import org.wappli.facade.api.filter.UzerStatusEnumFilter;
import org.wappli.common.api.rest.filter.LongFilter;
import org.wappli.common.api.rest.filter.StringFilter;
import lombok.Data;

@Data
public class UzerCriteria {

    private LongFilter id;

    private StringFilter username;

    private UzerStatusEnumFilter status;
}
