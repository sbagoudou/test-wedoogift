package com.sbagoudou.evaluation1.utils;

import java.util.Date;

import com.sbagoudou.evaluation1.model.Company;
import com.sbagoudou.evaluation1.model.Distribution;
import com.sbagoudou.evaluation1.model.User;

public class UserUtils {

	private static int idDistribution = 0;

	/**
	 * Function that distributes gift cards to the specified user by the company. If
	 * the company balance is not enough, no distribution made
	 */
	public static void distribuateGift(User user, Company company, int amount, Date startDate) {

		System.out.println("Company " + company.getId() + " is about to give a " + amount + "€ gift card value to user "
				+ user.getId() + "; his previous balance is : " + user.getBalance() + "€");

		if (company.getBalance() >= amount) {

			company.setBalance(company.getBalance() - amount);
			user.setBalance(user.getBalance() + amount);

			Date endDate = DateUtils.addYear(DateUtils.getPreviousDay(startDate), 1);

			Distribution distribution = new Distribution(idDistribution++, amount, startDate, endDate, company.getId(),
					user.getId());

			JsonUtils.distributions.add(distribution);
		} else {
			System.out.println(
					"The Company's balance (" + company.getBalance() + "€) is not enough to make this operation");
		}
	}

}
