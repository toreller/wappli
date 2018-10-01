package org.wappli.common.api.rest.util;

import org.wappli.common.api.rest.dto.input.PageableDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.MultiValueMap;
import org.wappli.common.api.rest.filter.StringFilter;

public class CriteriaMapBuilderTest {

    @Test
    public void buildParam() {
        TestCriteria testCriteria = new TestCriteria();
        PageableDTO pageableDTO = org.wappli.common.api.rest.util.TestPageableDTOFactory.createPageableDTO();

        MultiValueMap<String, String> multiValueMap = CriteriaMapBuilder.buildParam(testCriteria, pageableDTO);

        Assert.assertEquals(
                "{name.contains=[name], sort=[id, asc], size=[2], page=[1]}",
                multiValueMap.toString());
    }

    private static class TestCriteria {
        StringFilter name;

        public TestCriteria() {
            name = new StringFilter();
            name.setContains("name");
        }

        public StringFilter getName() {
            return name;
        }

        public void setName(StringFilter name) {
            this.name = name;
        }
    }
}