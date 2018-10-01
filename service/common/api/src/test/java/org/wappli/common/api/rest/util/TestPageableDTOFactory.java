package org.wappli.common.api.rest.util;

import org.wappli.common.api.rest.dto.input.OrderDTO;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.enums.SortDirection;

import java.util.Arrays;

public class TestPageableDTOFactory {
    public static PageableDTO createPageableDTO() {
        return new PageableDTO().page(1).size(2).sort(Arrays.asList(new OrderDTO().name("id").direction(SortDirection.ASC)));
    }
    public static PageableDTO createPageableZeroDTO() {
        return new PageableDTO().page(0).size(10).sort(Arrays.asList(new OrderDTO().name("id").direction(SortDirection.DESC)));
    }
}
