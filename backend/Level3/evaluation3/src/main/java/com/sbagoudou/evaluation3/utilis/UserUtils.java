package com.sbagoudou.evaluation3.utilis;

import java.util.Date;

import com.sbagoudou.evaluation3.model.Company;
import com.sbagoudou.evaluation3.model.Distribution;
import com.sbagoudou.evaluation3.model.User;
import com.sbagoudou.evaluation3.model.Wallet;

public class UserUtils {

	private static int idDistribution = 0;

	/**
	 * Function that distributes gift cards to the specified user by the company. If
	 * the company amount is not enough, no distribution made
	 */
	public static void distribuateGift(User user, Company company, Wallet wallet, int amount, Date startDate) {

		System.out.println("Company " + company.getId() + " is about to give a " + amount + "€ " + wallet.getName()
				+ " value to user " + user.getId());

		if (company.getBalance() >= amount) {

			company.setBalance(company.getBalance() - amount);

			// Check if the wallet already exists for this user
			boolean walletFound = false;
			for (int[] balance : user.getBalance()) {
				if (balance[0] == wallet.getId()) {
					walletFound = true;

					// If we find the wallet, directly add money to it
					balance[1] = balance[1] + amount;
					break;
				}
			}

			// If no balance yet, add a new one
			if (user.getBalance().isEmpty() || !walletFound) {
				int[] balance = new int[] { wallet.getId(), amount };
				user.getBalance().add(balance);
			}

			// Calculate endDate according to wallet type
			Date endDate;
			if (Wallet.GIFT_TYPE.equals(wallet.getType())) {
				// endDate is next year
				endDate = DateUtils.addYear(DateUtils.getPreviousDay(startDate), 1);
			} else {
				// endDate is last day of February of next year
				endDate = DateUtils.getLastDayFebruaryNextMonth(startDate);
			}

			Distribution distribution = new Distribution(idDistribution++, wallet.getId(), amount, startDate, endDate,
					company.getId(), user.getId());

			JsonUtils.distributions.add(distribution);
		} else {
			System.out.println(
					"The Company's amount (" + company.getBalance() + "€) is not enough to make this operation");
		}
	}

}
