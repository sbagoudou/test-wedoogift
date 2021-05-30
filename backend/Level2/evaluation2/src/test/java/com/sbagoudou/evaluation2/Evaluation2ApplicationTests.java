package com.sbagoudou.evaluation2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbagoudou.evaluation2.model.Company;
import com.sbagoudou.evaluation2.model.User;
import com.sbagoudou.evaluation2.model.Wallet;
import com.sbagoudou.evaluation2.utilis.JsonUtils;
import com.sbagoudou.evaluation2.utilis.UserUtils;

@SpringBootTest
class Evaluation2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testReadInputFile() {
		// Call the method to test
		JsonUtils.readInputFile();

		// Check results
		// Variables users, wallets and companies should not be empty
		assertThat(JsonUtils.users).isNotEmpty();
		assertThat(JsonUtils.companies).isNotEmpty();
		assertThat(JsonUtils.wallets).isNotEmpty();

	}

	@Test
	void testDistribuateGift() {

		// Populate test variables
		JsonUtils.users = new ArrayList<>();
		JsonUtils.companies = new ArrayList<>();
		JsonUtils.wallets = new ArrayList<>();

		User user = createTestUser();
		Company company = new Company(1, "TestCompany", 100);
		Wallet wallet = new Wallet(1, "TestWallet", "FOOD");

		JsonUtils.users.add(user);
		JsonUtils.companies.add(company);
		JsonUtils.wallets.add(wallet);

		// Call the method to test
		UserUtils.distribuateGift(user, JsonUtils.companies.get(0), JsonUtils.wallets.get(0), 100, new Date());

		// Check results
		// Variables users and companies should not be empty
		assertThat(JsonUtils.users.get(0).getBalance().get(0)[1]).isEqualTo(200);
		assertThat(JsonUtils.companies.get(0).getBalance()).isEqualTo(0);

	}

	@Test
	void testDistribuateGiftNoEnoughBalance() {
		// Populate test variables
		JsonUtils.users = new ArrayList<>();
		JsonUtils.companies = new ArrayList<>();
		JsonUtils.wallets = new ArrayList<>();

		User user = createTestUser();
		Company company = new Company(1, "TestCompany", 50);
		Wallet wallet = new Wallet(1, "TestWallet", "FOOD");

		JsonUtils.users.add(user);
		JsonUtils.companies.add(company);
		JsonUtils.wallets.add(wallet);

		// Call the method to test
		UserUtils.distribuateGift(user, JsonUtils.companies.get(0), JsonUtils.wallets.get(0), 100, new Date());

		// Check results
		// The amount of the user and the company should not change
		assertThat(JsonUtils.users.get(0).getBalance().get(0)[1]).isEqualTo(100);
		assertThat(JsonUtils.companies.get(0).getBalance()).isEqualTo(50);
	}

	@Test
	void testWriteOutputFile() {
		// Populate test variables
		JsonUtils.users = new ArrayList<>();
		JsonUtils.companies = new ArrayList<>();
		JsonUtils.wallets = new ArrayList<>();

		User user = createTestUser();
		Company company = new Company(1, "TestCompany", 50);
		Wallet wallet = new Wallet(1, "TestWallet", "FOOD");

		JsonUtils.users.add(user);
		JsonUtils.companies.add(company);
		JsonUtils.wallets.add(wallet);

		// Call the method to test
		UserUtils.distribuateGift(user, JsonUtils.companies.get(0), JsonUtils.wallets.get(0), 100, new Date());
		JsonUtils.writeOutputFile();

		// Check results
		// File output.json should exist
		File file = new File("src/main/resources/output.json");
		assertThat(file.exists());

	}

	private User createTestUser() {
		User user = new User(1, null);
		user.getBalance().add(new int[] { 1, 100 });
		return user;
	}

}
