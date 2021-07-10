package com.practice.libraryservice.contract;

import com.practice.libraryservice.LibraryServiceApplication;
import com.practice.libraryservice.client.feign.BookServiceClient;
import com.practice.libraryservice.entity.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryServiceApplication.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = { "com.practice:book-service:+:8081" })
public class ExternalContracts {

    @Autowired
    BookServiceClient bookServiceClient;

    @Test
    public void testContractToBookStoreServer() {

        List<Book> result = bookServiceClient.getBooks();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size()>0);
        Assert.assertEquals(1,result.get(0).getId());
        Assert.assertEquals("Test Book",result.get(0).getName());
        Assert.assertEquals("Test Author",result.get(0).getAuthor());


    }


}
