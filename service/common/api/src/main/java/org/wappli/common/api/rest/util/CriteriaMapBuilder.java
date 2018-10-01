package org.wappli.common.api.rest.util;

import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.wappli.common.api.rest.filter.Filter;
import org.wappli.common.api.rest.util.params.RequestParameterBuilder;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CriteriaMapBuilder {
    public static <T> MultiValueMap<String, String> buildParam(T criteria, PageableDTO pageableDTO) {
        final RequestParameterBuilder builder = new RequestParameterBuilder();

        if (criteria != null) {
            for(Field field : criteria.getClass().getDeclaredFields()){
                for(Method method : criteria.getClass().getMethods()){
                    if ((method.getName().startsWith("get"))
                            && (method.getName().length() == (field.getName().length() + 3))
                            && method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {

                        try {
                            Object getFilter = method.invoke(criteria);
                            builder.addParameter(field.getName(), getFilter instanceof Filter ? (Filter)getFilter : null);
                        } catch (InvocationTargetException | IllegalAccessException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        builder.addPagination(pageableDTO);

        return builder.build();
    }
}
