package com.example.IMS.Store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class StoreRepositoryTest {
	
	@Autowired
	private StoreRepository underTest;
	
	@AfterEach
	void tearDown() {underTest.deleteAll();}

	@Test
	void test() {
//		fail("Not yet implemented");
	}
	
	@Test
	void canSaveAndRetrieveStores()
	{
		List<Store> stores = generateStores();
		
		int numStores = stores.size();
		
		underTest.saveAll(stores);
		
		List<Store> fromDb = underTest.findAll();
		assertThat(fromDb.size()).isEqualTo(numStores);
	}
	
	@Test
	void givenStoreIdReturnsStoreWithGivenId()
	{
		List<Store> stores = generateStores();
		
		Store store = stores.get(2);
		
		underTest.save(store);
		
		Store storeFromDb = underTest.getById(store.getId());
		
		assertThat(storeFromDb.getId()).isEqualTo(store.getId());
		assertThat(storeFromDb).isEqualTo(store);
	}
	
	private List<Store> generateStores()
	{
		List<Store> stores = new ArrayList<>();
		
		for (int i = 0; i < 5; i++)
		{
			stores.add(new Store("Store - " + i, null));
		}
		
		return stores;
	}

}
