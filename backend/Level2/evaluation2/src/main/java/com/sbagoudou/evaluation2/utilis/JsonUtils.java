package com.sbagoudou.evaluation2.utilis;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbagoudou.evaluation2.model.Company;
import com.sbagoudou.evaluation2.model.Distribution;
import com.sbagoudou.evaluation2.model.User;
import com.sbagoudou.evaluation2.model.Wallet;

/**
 * Utility class for json files
 * 
 * @author sbagoudou
 *
 */
public class JsonUtils {

	public static List<Wallet> wallets = new ArrayList<>();
	public static List<User> users = new ArrayList<>();
	public static List<Company> companies = new ArrayList<>();
	public static List<Distribution> distributions = new ArrayList<>();

	/**
	 * Reads the input file and parse datas to users and companies list
	 */
	public static void readInputFile() {
		try {

			System.out.println("Reading file...");
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode input = objectMapper.readValue(new File("src/main/resources/input.json"), JsonNode.class);

			// Parse wallets
			JsonNode walletsNode = input.get("wallets");
			Iterator<JsonNode> walletsNodeIt = walletsNode.elements();
			while (walletsNodeIt.hasNext()) {
				JsonNode walletNode = walletsNodeIt.next();
				Wallet wallet = new Wallet(walletNode.get("id").asInt(), walletNode.get("name").asText(),
						walletNode.get("type").asText());
				wallets.add(wallet);
			}

			// Parse companies
			JsonNode companiesNode = input.get("companies");
			Iterator<JsonNode> companiesNodeIt = companiesNode.elements();
			while (companiesNodeIt.hasNext()) {
				JsonNode companyNode = companiesNodeIt.next();
				Company company = new Company(companyNode.get("id").asInt(), companyNode.get("name").asText(),
						companyNode.get("balance").asInt());
				companies.add(company);
			}

			// Parse users
			JsonNode usersNode = input.get("users");
			Iterator<JsonNode> usersNodeIt = usersNode.elements();
			while (usersNodeIt.hasNext()) {
				JsonNode userNode = usersNodeIt.next();
				List<int[]> balances = new ArrayList<>();
				JsonNode balanceNode = userNode.get("balance");
				Iterator<JsonNode> balanceNodeIt = balanceNode.elements();

				while (balanceNodeIt.hasNext()) {
					JsonNode walletNode = balanceNodeIt.next();
					int[] balance = new int[] { walletNode.get("wallet_id").asInt(), walletNode.get("amount").asInt() };
					balances.add(balance);
				}

				User user = new User(userNode.get("id").asInt(), balances);
				users.add(user);
			}

			System.out.println("End of Reading file.");

		} catch (Exception e) {
			System.out.println("ERROR WHILE READING: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Writes the output file after operations
	 */
	public static void writeOutputFile() {

		try {
			System.out.println("Writing file...");

			// create a map
			Map<Object, Object> map = new HashMap<>();

			// Parse companies
			List<Object> listCompanies = new ArrayList<>();
			for (Company company : companies) {
				Map<Object, Object> mapCompanies = new HashMap<>();
				mapCompanies.put("id", company.getId());
				mapCompanies.put("name", company.getName());
				mapCompanies.put("balance", company.getBalance());
				listCompanies.add(mapCompanies);
			}
			map.put("companies", listCompanies);

			// Parse users
			List<Object> listUsers = new ArrayList<>();
			for (User user : users) {
				Map<Object, Object> mapUsers = new HashMap<>();
				mapUsers.put("id", user.getId());

				// Parse balances
				List<Object> listBalances = new ArrayList<>();
				for (int[] balance : user.getBalance()) {
					Map<Object, Object> mapBalances = new HashMap<>();
					mapBalances.put("wallet_id", balance[0]);
					mapBalances.put("amount", balance[1]);
					listBalances.add(mapBalances);
				}
				mapUsers.put("balance", listBalances);
				listUsers.add(mapUsers);
			}
			map.put("users", listUsers);

			// Parse distributions
			List<Object> listDistributions = new ArrayList<>();
			for (Distribution distribution : distributions) {
				Map<Object, Object> mapDistributions = new HashMap<>();
				mapDistributions.put("id", distribution.getId());
				mapDistributions.put("wallet_id", distribution.getWalletId());
				mapDistributions.put("amount", distribution.getAmount());
				mapDistributions.put("start_date",
						DateUtils.format(distribution.getStartDate(), DateUtils.YYYY_MM_DD_FORMAT));
				mapDistributions.put("end_date",
						DateUtils.format(distribution.getEndDate(), DateUtils.YYYY_MM_DD_FORMAT));
				mapDistributions.put("company_id", distribution.getCompanyId());
				mapDistributions.put("user_id", distribution.getUserId());
				listDistributions.add(mapDistributions);
			}
			map.put("distributions", listDistributions);

			// create object mapper instance
			ObjectMapper mapper = new ObjectMapper();

			// convert map to JSON file
			mapper.writeValue(Paths.get("src/main/resources/output.json").toFile(), map);

			System.out.println("End of Writing file.");
		} catch (Exception ex) {
			System.out.println("ERROR WHILE WRITING : " + ex.getMessage());
			ex.printStackTrace();
		}

	}

}
