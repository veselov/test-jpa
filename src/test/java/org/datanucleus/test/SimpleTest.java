package org.datanucleus.test;

import java.util.*;

import mydomain.SimpleCF;
import org.datanucleus.api.jpa.JPAPropertyNames;
import org.junit.*;
import javax.persistence.*;

import static org.junit.Assert.*;
import mydomain.model.*;
import org.datanucleus.util.NucleusLogger;

public class SimpleTest
{
    @Test
    public void testSimple()
    {
        NucleusLogger.GENERAL.info(">> test START");
        Map props = new HashMap();
        props.put("datanucleus.ConnectionFactory", new SimpleCF());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyTest", props);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();

            // [INSERT code here to persist object required for testing]
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
        }
        finally 
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close();
        }

        emf.close();
        NucleusLogger.GENERAL.info(">> test END");
    }
}
