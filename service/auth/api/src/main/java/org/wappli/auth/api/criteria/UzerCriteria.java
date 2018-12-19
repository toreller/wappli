package org.wappli.auth.api.criteria;

import org.wappli.auth.api.filter.UzerStatusEnumFilter;
import org.wappli.common.api.rest.filter.LongFilter;
import org.wappli.common.api.rest.filter.StringFilter;
import lombok.Data;

@Data
public class UzerCriteria {

    private LongFilter id;

    private StringFilter username;

    private UzerStatusEnumFilter status;
}
