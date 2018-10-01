package org.wappli.common.server.web.resolver;

import org.wappli.common.api.rest.dto.input.OrderDTO;
import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.enums.SortDirection;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.LinkedList;
import java.util.List;

@Component
public class PageableDTOResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageableDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        int size;
        try {
            size = Integer.parseInt(webRequest.getParameter("size"));
        } catch (NumberFormatException nfe){
            size = 20;
        }

        int page;
        try {
            page = Integer.parseInt(webRequest.getParameter("page"));
        } catch (NumberFormatException nfe){
            page = 0;
        }

        String[] sort = webRequest.getParameterValues("sort");

        List<OrderDTO> orderDTOList = new LinkedList<>();
        if(sort != null){
            for(String s : sort){
                String[] order = s.split(",");
                orderDTOList.add(new OrderDTO().name(order[0].trim()).direction(order.length == 2 ? SortDirection.fromUrlEncodedName(order[1].trim()) : null));
            }
        }

        return new PageableDTO().size(size).page(page).sort(orderDTOList);
    }

}
