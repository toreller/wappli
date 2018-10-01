package org.wappli.common.server.web.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wappli.common.api.rest.util.TestPageableDTOFactory;
import org.wappli.common.api.rest.dto.entities.EntityDTO;
import org.wappli.common.api.rest.webinterfaces.CrudWebInterface;
import org.wappli.common.server.service.AbstractSinglesDb;

import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
abstract public class AbstractClientIntTest<EDTO extends EntityDTO, CRITERIA, CLIENT extends CrudWebInterface<EDTO, CRITERIA>> {
    @Autowired
    protected AbstractSinglesDb singlesDb;

    protected CLIENT client;

    @Before
    public void createSinglesDb() {
        singlesDb.create();
    }

    @After
    public void purgeDb() {
        singlesDb.truncateAll();
    }

    @Before
    abstract public void setClient();

    abstract protected EDTO createEntityDTO();

    abstract protected CRITERIA createCriteria();

    @Test
    public void testClient() throws URISyntaxException {
        EDTO dto = createEntityDTO();

        long id = client.create(dto).getBody().getId();
        dto = client.get(id).getBody().getItem();
        id = client.update(id, dto).getBody().getId();
        dto = client.getAll(createCriteria(), TestPageableDTOFactory.createPageableZeroDTO()).getBody().get(0).getItem();
        id = client.delete(id).getBody().getId();
    }
}
