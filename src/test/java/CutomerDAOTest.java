import cz.kvasnickakb.flush.interceptor.example.ApplicationConfig;
import cz.kvasnickakb.flush.interceptor.example.dao.Customer;
import cz.kvasnickakb.flush.interceptor.example.dao.CustomerDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author : Daniel Kvasnička
 * @inheritDoc
 * @since : 24.02.2021
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@ActiveProfiles("test")
@Transactional
public class CutomerDAOTest {

    @Inject
    private CustomerDAO dao;

    @Test(expected = MyBatisSystemException.class)
    public void testOptimisticLockingConcurrent() {
        Customer customer = getCustomer();

        dao.insert(customer);

        // get initial version + do some change
        Customer customer1 = dao.findById(customer.getId());
        customer1.setName("UpdatedCustomer1");

        // get initial version + do some change
        Customer customer2 = dao.findById(customer.getId());
        customer2.setName("UpdatedCustomer2");

        // process first update
        dao.update(customer1);

        // try to process second update - it must throws optimistic lock MyBatisSystemException
        dao.update(customer2);

        TestTransaction.flagForCommit();
        TestTransaction.end();
    }

    private Customer getCustomer() {
        Customer c = new Customer();
        c.setId(1L);
        c.setName("Customer");
        c.setVersion(1L);
        return c;
    }
}
