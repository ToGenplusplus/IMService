package com.example.IMS.Store;


import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
	
	@Mock private StoreRepository storeRepo;
	
	private StoreService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new StoreService(storeRepo);
	}

	@AfterEach
	void tearDown() throws Exception {
		storeRepo.deleteAll();
	}

	@Test
	@Disabled
	void test() {
//		fail("Not yet implemented");
	}

	@Test
	void canGetAllStores()
	{
		underTest.getAllStoresInCompany();
		
		verify(storeRepo).findAll();
	}
	
	@Test
	void canGetStoreGivenStoreId()
	{
		long storeId = 1L;
		Store store = new Store(storeId,"Store 1", null);
		doReturn(store).when(storeRepo).getById(storeId);
		
		Store fromSvc = underTest.getStoreById(storeId);
		
		assertThat(fromSvc).isNotEqualTo(null);
		assertThat(fromSvc.getId()).isEqualTo(storeId);
		assertThat(fromSvc.getStoreName()).isEqualTo("Store 1");
	}
}
