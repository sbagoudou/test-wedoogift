package com.sbagoudou.evaluation1;

import java.util.Date;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sbagoudou.evaluation1.model.User;
import com.sbagoudou.evaluation1.utils.JsonUtils;
import com.sbagoudou.evaluation1.utils.UserUtils;

@SpringBootApplication
public class Evaluation1Application {

	public static void main(String[] args) {
		SpringApplication.run(Evaluation1Application.class, args);

		// Read the input file and retrieve data
		JsonUtils.readInputFile();

		int numberOfCompanies = JsonUtils.companies.size();

		for (User user : JsonUtils.users) {
			// Randomly associate the user to a company
			Random r = new Random();
			int randomCompanyIndex = r.nextInt(numberOfCompanies);

			// Perform distribution
			UserUtils.distribuateGift(user, JsonUtils.companies.get(randomCompanyIndex), 500, new Date());

			// Check user's balance
			System.out.println("The balance of user " + user.getId() + " is : " + user.getBalance() + "€");
		}

		// Write the output file
		JsonUtils.writeOutputFile();

	}

}
