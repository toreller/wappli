package org.wappli.common.server.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSinglesDb {
    @Autowired
    protected EntityManager em;

    abstract public void create();

    @Transactional
    public void truncateAll(){
        List<String> tablenames = retrieveTablenames();

        em.flush();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        tablenames.forEach(tableName -> em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate());

        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

        detachObjects();
    }

    abstract protected void detachObjects();

    private List<String> retrieveTablenames() {
        List<String> tableNames = new ArrayList<>();
        Session session = em.unwrap(Session.class);

        session.doWork(connection -> {
            ResultSet tables = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            if (tables != null) {
                while (tables.next()) {
                    tableNames.add(tables.getString("TABLE_NAME"));
                }
            }
        });

        return tableNames;
    }

}
