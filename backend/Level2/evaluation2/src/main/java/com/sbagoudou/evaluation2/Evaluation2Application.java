package com.sbagoudou.evaluation2;

import java.util.Date;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sbagoudou.evaluation2.model.User;
import com.sbagoudou.evaluation2.utilis.JsonUtils;
import com.sbagoudou.evaluation2.utilis.UserUtils;

@SpringBootApplication
public class Evaluation2Application {

	public static void main(String[] args) {
		SpringApplication.run(Evaluation2Application.class, args);

		// Read the input file and retrieve data
		JsonUtils.readInputFile();

		int numberOfCompanies = JsonUtils.companies.size();
		int numberOfWallets = JsonUtils.wallets.size();

		for (User user : JsonUtils.users) {
			// Randomly associate the user to a company
			Random r = new Random();
			int randomCompanyIndex = r.nextInt(numberOfCompanies);
			r = new Random();
			int randomWalletIndex = r.nextInt(numberOfWallets);

			// Perform distribution
			UserUtils.distribuateGift(user, JsonUtils.companies.get(randomCompanyIndex),
					JsonUtils.wallets.get(randomWalletIndex), 500, new Date());

			// Check user's amount
			System.out.println("The amount of user " + user.getId() + " is : ");
			for (int[] balance : user.getBalance()) {
				System.out.println("Wallet ID : " + balance[0] + "; Balance : " + balance[1] + "€");
			}
		}

		// Write the output file
		JsonUtils.writeOutputFile();

	}

}
