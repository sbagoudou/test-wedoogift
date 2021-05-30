package com.sbagoudou.evaluation3.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sbagoudou.evaluation3.model.User;
import com.sbagoudou.evaluation3.utilis.JsonUtils;
import com.sbagoudou.evaluation3.utilis.UserUtils;

@Controller
public class UserController {

	@GetMapping("operate")
	public String operate() {
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
		return "result";
	}
}
