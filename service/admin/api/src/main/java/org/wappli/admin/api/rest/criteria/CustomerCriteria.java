package org.wappli.admin.api.rest.criteria;

import org.wappli.common.api.rest.filter.LongFilter;
import org.wappli.common.api.rest.filter.StringFilter;

import java.io.Serializable;

public class CustomerCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    public CustomerCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomerCriteria{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
