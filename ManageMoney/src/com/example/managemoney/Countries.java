package com.example.managemoney;

import java.util.*;

public class Countries {
	private final List<String> countries;

	public Countries() {
		this.countries = new ArrayList<String>();
		getLocaleCountries();
	}

	public List<String> getCountries() {
		return countries;
	}

	private void getLocaleCountries() {
		Locale[] locales = Locale.getAvailableLocales();

		for (int i = 0, len = locales.length; i < len; i++) {
			String country = locales[i].getDisplayCountry();
			if (country.trim().length() > 0 && !countries.contains(country)) {
				countries.add(country);
			}
		}
		Collections.sort(countries);
	}
}
