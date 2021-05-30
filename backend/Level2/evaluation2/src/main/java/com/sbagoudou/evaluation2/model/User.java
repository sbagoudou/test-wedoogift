package com.sbagoudou.evaluation2.model;

import java.util.ArrayList;
import java.util.List;

public class User {

	int id;
	List<int[]> balance;

	/**
	 * Default constructor
	 */
	public User() {
		super();
	}

	/**
	 * Constructor using fields
	 * 
	 * @param id
	 * @param balance
	 */
	public User(int id, List<int[]> balance) {
		this.id = id;
		this.balance = balance;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the balance
	 */
	public List<int[]> getBalance() {
		if (balance == null) {
			balance = new ArrayList<>();
		}
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(List<int[]> balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		return true;
	}

}
