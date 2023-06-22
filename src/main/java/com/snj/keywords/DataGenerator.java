package com.snj.keywords;

import java.util.Locale;

import net.datafaker.Faker;

public class DataGenerator {

	/***
	 * Class to the test data generator methods related to Name
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Name {
		/**
		 * Method to get the prefix
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getPrefix() {
			return new Faker().name().prefix();
		}

		/**
		 * Method to get the suffix
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getSuffix() {
			return new Faker().name().suffix();
		}

		/**
		 * Method to get the first name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getFirstName() {
			return new Faker().name().firstName();
		}

		/**
		 * Method to get the first name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @param locale
		 * @since 11-05-2023
		 * @return
		 */
		public static String getFirstName(Locale locale) {
			return new Faker(locale).name().firstName();
		}

		/**
		 * Method to get the last name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getLastName() {
			return new Faker().name().lastName();
		}

		/**
		 * Method to get the last name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @param locale
		 * @since 11-05-2023
		 * @return
		 */
		public static String getLastName(Locale locale) {
			return new Faker(locale).name().lastName();
		}

		/**
		 * Method to get the full name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getFullName() {
			return new Faker().name().fullName();
		}

		/**
		 * Method to get the full name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @param locale
		 * @since 11-05-2023
		 * @return
		 */
		public static String getFullName(Locale locale) {
			return new Faker(locale).name().fullName();
		}

		/**
		 * Method to get the username
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getUsername() {
			return new Faker().name().username();
		}

		/**
		 * Method to get the username based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @param locale
		 * @since 11-05-2023
		 * @return
		 */
		public static String getUsername(Locale locale) {
			return new Faker(locale).name().username();
		}
	}

	/***
	 * Class to the test data generator methods related to Address
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Address {

		/**
		 * Method to get the building number
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getBuildingNumber() {
			return new Faker().address().buildingNumber();
		}

		/**
		 * Method to get the building number based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getBuildingNumber(Locale locale) {
			return new Faker(locale).address().buildingNumber();
		}

		/**
		 * Method to get the street address
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getStreetAddress() {
			return new Faker().address().streetAddress();
		}

		/**
		 * Method to get the street address based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getStreetAddress(Locale locale) {
			return new Faker(locale).address().streetAddress();
		}

		/**
		 * Method to get the secondary address
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getSecondaryAddress() {
			return new Faker().address().secondaryAddress();
		}

		/**
		 * Method to get the secondary address based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getSecondaryAddress(Locale locale) {
			return new Faker(locale).address().secondaryAddress();
		}

		/**
		 * Method to get the city
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getCity() {
			return new Faker().address().city();
		}

		/**
		 * Method to get the city based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getCity(Locale locale) {
			return new Faker(locale).address().city();
		}

		/**
		 * Method to get the state
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getState() {
			return new Faker().address().state();
		}

		/**
		 * Method to get the state based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getState(Locale locale) {
			return new Faker(locale).address().state();
		}

		/**
		 * Method to get the country
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getCountry() {
			return new Faker().address().country();
		}

		/**
		 * Method to get the country based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getCountry(Locale locale) {
			return new Faker(locale).address().country();
		}

		/**
		 * Method to get the city code
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getCountryCode() {
			return new Faker().address().countryCode();
		}

		/**
		 * Method to get the country code based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getCountryCode(Locale locale) {
			return new Faker(locale).address().countryCode();
		}

		/**
		 * Method to get the zipcode
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getZipCode() {
			return new Faker().address().zipCode();
		}

		/**
		 * Method to get the zipcode based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getZipCode(Locale locale) {
			return new Faker(locale).address().zipCode();
		}

		/**
		 * Method to get the full address
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getFullAddress() {
			return new Faker().address().fullAddress();
		}

		/**
		 * Method to get the full address based on the locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getFullAddress(Locale locale) {
			return new Faker(locale).address().fullAddress();
		}

		/**
		 * Method to get the time-zone
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getTimeZone() {
			return new Faker().address().timeZone();
		}

		/**
		 * Method to get the time-zone based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getTimeZone(Locale locale) {
			return new Faker(locale).address().timeZone();
		}

		/**
		 * Method to get latitude
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getLatitude() {
			return new Faker().address().latitude();
		}

		/**
		 * Method to get the latitude based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getLatitude(Locale locale) {
			return new Faker(locale).address().latitude();
		}

		/**
		 * Method to get the longitude
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getLongitude() {
			return new Faker().address().longitude();
		}

		/**
		 * Method to get the longitude based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getLongitude(Locale locale) {
			return new Faker(locale).address().longitude();
		}

		/**
		 * Method to get the latitude-longitude
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getLatitudeLongitude() {
			return new Faker().address().latLon();
		}

		/**
		 * Method to get the latitude-longitude based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getLatitudeLongitude(Locale locale) {
			return new Faker(locale).address().latLon();
		}
	}

	/***
	 * Class to the test data generator methods related to Phone
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Phone {
		/**
		 * Method to get the cell phone
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getCellPhone() {
			return new Faker().phoneNumber().cellPhone();
		}

		/**
		 * Method to get the cell phone based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getCellPhone(Locale locale) {
			return new Faker(locale).phoneNumber().cellPhone();
		}

		/**
		 * Method to get the phone number
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getPhoneNumber() {
			return new Faker().phoneNumber().phoneNumber();
		}

		/**
		 * Method to get the phone number based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getPhoneNumber(Locale locale) {
			return new Faker(locale).phoneNumber().phoneNumber();
		}
	}

	/***
	 * Class to the test data generator methods related to Appliance
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Appliance {
		/**
		 * Method to get the brand name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getBrand() {
			return new Faker().appliance().brand();
		}

		/**
		 * Method to get the brand name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getBrand(Locale locale) {
			return new Faker(locale).appliance().brand();
		}

		/**
		 * Method to get the equipment name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getEquipment() {
			return new Faker().appliance().equipment();
		}

		/**
		 * Method to get the equipment name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getEquipment(Locale locale) {
			return new Faker(locale).appliance().equipment();
		}
	}

	/***
	 * Class to the test data generator methods related to Artist
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Artist {
		/**
		 * Method to get the artist name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getArtistName() {
			return new Faker().artist().name();
		}

		/**
		 * Method to get the artist name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getArtistName(Locale locale) {
			return new Faker(locale).artist().name();
		}
	}

	/***
	 * Class to the test data generator methods related to Avatar
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Avatar {
		/**
		 * Method to get the avatar image as link
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getAvatar() {
			return new Faker().avatar().image();
		}
	}

	/***
	 * Class to the test data generator methods related to Aviation
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Aviation {
		/**
		 * Method to get the Airport name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getAirport() {
			return new Faker().aviation().airport();
		}

		/**
		 * Method to get the Airport name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getAirport(Locale locale) {
			return new Faker().aviation().airport();
		}

		/**
		 * Method to get the Aircraft name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getAircraft() {
			return new Faker().aviation().aircraft();
		}

		/**
		 * Method to get the Aircraft name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getAircraft(Locale locale) {
			return new Faker().aviation().aircraft();
		}

		/**
		 * Method to get the Airline name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getAirline() {
			return new Faker().aviation().airline();
		}

		/**
		 * Method to get the Airline name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getAirline(Locale locale) {
			return new Faker().aviation().airline();
		}

		/**
		 * Method to get the flight name
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getFlight() {
			return new Faker().aviation().flight();
		}

		/**
		 * Method to get the flight name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param locale
		 * @return
		 */
		public static String getFlight(Locale locale) {
			return new Faker().aviation().flight();
		}
	}

	/***
	 * Class to the test data generator methods related to Barcode
	 * 
	 * @author sanoj.swaminathan
	 * @since 11-05-2023
	 *
	 */
	public static class Barcode {
		/**
		 * Method to get the barcode type
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @return
		 */
		public static String getBarcodeType() {
			return new Faker().barcode().type();
		}

		/**
		 * Method to get the barcode based on ean or gtin number, it can be ean13 or
		 * ean8 or gtin8 or gtin12 or gtin13 or gtin14
		 * 
		 * @author sanoj.swaminathan
		 * @since 11-05-2023
		 * @param ean_gtin
		 * @return
		 */
		public static long getBarcode(String ean_gtin) {
			long barcodeData = 0;
			switch (ean_gtin.toLowerCase()) {
			case "ean13":
				barcodeData = new Faker().barcode().ean13();
				break;
			case "ean8":
				barcodeData = new Faker().barcode().ean8();
				break;
			case "gtin8":
				barcodeData = new Faker().barcode().gtin8();
				break;
			case "gtin12":
				barcodeData = new Faker().barcode().gtin12();
				break;
			case "gtin13":
				barcodeData = new Faker().barcode().gtin13();
				break;
			case "gtin14":
				barcodeData = new Faker().barcode().gtin14();
				break;
			default:
				break;
			}
			return barcodeData;
		}
	}

	/***
	 * Class to the test data generator methods related to Book
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Book {
		/**
		 * Method to get the book title
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getBookTitle() {
			return new Faker().book().title();
		}

		/**
		 * Method to get the book title based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param locale
		 * @return
		 */
		public static String getBookTitle(Locale locale) {
			return new Faker(locale).book().title();
		}

		/**
		 * Method to get the author name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getAuthor() {
			return new Faker().book().author();
		}

		/**
		 * Method to get the author name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param locale
		 * @return
		 */
		public static String getAuthor(Locale locale) {
			return new Faker(locale).book().author();
		}

		/**
		 * Method to get the genre name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getGenre() {
			return new Faker().book().genre();
		}

		/**
		 * Method to get the publisher name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getPublisher() {
			return new Faker().book().publisher();
		}

		/**
		 * Method to get the publisher name based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param locale
		 * @return
		 */
		public static String getPublisher(Locale locale) {
			return new Faker(locale).book().publisher();
		}
	}

	/***
	 * Class to the test data generator methods related to Brand
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Brand {
		/**
		 * Method to get the car brand
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCarBrand() {
			return new Faker().brand().car();
		}

		/**
		 * Method to get the car brand based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param locale
		 * @return
		 */
		public static String getCarBrand(Locale locale) {
			return new Faker(locale).brand().car();
		}

		/**
		 * Method to get the watch brand
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getWatchBrand() {
			return new Faker().brand().watch();
		}

		/**
		 * Method to get the watch brand based on locale
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param locale
		 * @return
		 */
		public static String getWatchBrand(Locale locale) {
			return new Faker(locale).brand().watch();
		}

		/**
		 * Method to get the sport brand
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getSportBrand() {
			return new Faker().brand().sport();
		}
	}

	/***
	 * Class to the test data generator methods related to BankingOrFinance
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class BankingOrFinance {
		/**
		 * Method to get the credit card type
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCreditCardType() {
			return new Faker().business().creditCardType();
		}

		/**
		 * Method to get the credit card number
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCreditCardNumber() {
			return new Faker().business().creditCardNumber();
		}

		/**
		 * Method to get the credit card expiry
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCreditCardExpiry() {
			return new Faker().business().creditCardExpiry();
		}

		/**
		 * Method to get the security code
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getSecurityCode() {
			return new Faker().business().securityCode();
		}

		/**
		 * Method to get the Business Identification Code
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getBusinessIdentificationCode() {
			return new Faker().finance().bic();
		}

		/**
		 * Method to get the Stock Market Name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getStockMarketName() {
			return new Faker().finance().stockMarket();
		}
	}

	/***
	 * Class to the test data generator methods related to Color
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Color {
		/**
		 * Method to get the name of the color
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getColorName() {
			return new Faker().color().name();
		}

		/**
		 * Method to get the hex color code
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getHexColorCode() {
			return new Faker().color().hex();
		}

		/**
		 * Method to get the hex color code based on the flag includeHashSign
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param includeHashSign
		 * @return
		 */
		public static String getHexColorCode(boolean includeHashSign) {
			return new Faker().color().hex(includeHashSign);
		}
	}

	/***
	 * Class to the test data generator methods related to Commerce
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Commerce {

		/**
		 * Method to get the department name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDepartmentName() {
			return new Faker().commerce().department();
		}

		/**
		 * Method to get the product brand
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getProductBrand() {
			return new Faker().commerce().brand();
		}

		/**
		 * Method to get the product name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getProductName() {
			return new Faker().commerce().productName();
		}

		/**
		 * Method to get the material name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMaterial() {
			return new Faker().commerce().material();
		}
	}

	/***
	 * Class to the test data generator methods related to Company
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Company {

		/**
		 * Method to get the company name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCompanyName() {
			return new Faker().company().name();
		}

		/**
		 * Method to get the company logo as link
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCompanyLogo() {
			return new Faker().company().logo();
		}

		/**
		 * Method to get the company URL
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCompanyURL() {
			return new Faker().company().url();
		}

		/**
		 * Method to get the industry name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getIndustryName() {
			return new Faker().company().industry();
		}

		/**
		 * Method to get the profession name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getProfession() {
			return new Faker().company().profession();
		}
	}

	/***
	 * Class to the test data generator methods related to Country
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Country {

		/**
		 * Method to get the country name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCountryName() {
			return new Faker().country().name();
		}

		/**
		 * Method to get the country flag as link
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCountryFlag() {
			return new Faker().country().flag();
		}

		/**
		 * Method to get the country currency
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCountryCurrency() {
			return new Faker().country().currency();
		}

		/**
		 * Method to get the country currency code
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCountryCurrencyCode() {
			return new Faker().country().currencyCode();
		}

		/**
		 * Method to get the country capital
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCountryCapital() {
			return new Faker().country().capital();
		}

		/**
		 * Method to get the capital city
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getCapitalCity() {
			return new Faker().nation().capitalCity();
		}

		/**
		 * Method to get the language
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getLanguage() {
			return new Faker().nation().language();
		}

		/**
		 * Method to get the nationality
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param locale
		 * @return
		 */
		public static String getNationality() {
			return new Faker().nation().nationality();
		}
	}

	/***
	 * Class to the test data generator methods related to Demographic
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Demographic {

		/**
		 * Method to get the demonym
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDemonym() {
			return new Faker().demographic().demonym();
		}

		/**
		 * Method to get the education attained
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getEducation() {
			return new Faker().demographic().educationalAttainment();
		}

		/**
		 * Method to get the marital status
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMaritalStatus() {
			return new Faker().demographic().maritalStatus();
		}

		/**
		 * Method to get the gender
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getGender() {
			return new Faker().demographic().sex();
		}

		/**
		 * Method to get the race or ethnicity
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getRaceOrEthnicity() {
			return new Faker().demographic().race();
		}
	}

	/***
	 * Class to the test data generator methods related to Device
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Device {

		/**
		 * Method to get the platform
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getPlatform() {
			return new Faker().device().platform();
		}

		/**
		 * Method to get the manufacturer
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getManufacturer() {
			return new Faker().device().manufacturer();
		}

		/**
		 * Method to get the model name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getModelName() {
			return new Faker().device().modelName();
		}

		/**
		 * Method to get the serial number
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getSerialNumber() {
			return new Faker().device().serial();
		}
	}

	/***
	 * Class to the test data generator methods related to File
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class File {

		/**
		 * Method to get the file name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getFileName() {
			return new Faker().file().fileName();
		}

		/**
		 * Method to get the file extension
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getFileExtension() {
			return new Faker().file().extension();
		}

		/**
		 * Method to get the mime type
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMimeType() {
			return new Faker().file().mimeType();
		}
	}

	/***
	 * Class to the test data generator methods related to Restaurant
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Restaurant {

		/**
		 * Method to get the restaurant name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getRestaurantName() {
			return new Faker().restaurant().name();
		}

		/**
		 * Method to get the restaurant type
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getRestaurantType() {
			return new Faker().restaurant().type();
		}

		/**
		 * Method to get the restaurant review
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getRestaurantReview() {
			return new Faker().restaurant().review();
		}

		/**
		 * Method to get the restaurant description
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getRestaurantDescription() {
			return new Faker().restaurant().description();
		}
	}

	/***
	 * Class to the test data generator methods related to Food
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Food {

		/**
		 * Method to get the dish name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDishName() {
			return new Faker().food().dish();
		}

		/**
		 * Method to get the fruit name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getFruitName() {
			return new Faker().food().fruit();
		}

		/**
		 * Method to get the ingredient name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getIngredientName() {
			return new Faker().food().ingredient();
		}

		/**
		 * Method to get the vegetable name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVegetableName() {
			return new Faker().food().vegetable();
		}
	}

	/***
	 * Class to the test data generator methods related to Hobby
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Hobby {

		/**
		 * Method to get the activity name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getActivityName() {
			return new Faker().hobby().activity();
		}
	}

	/***
	 * Class to the test data generator methods related to Internet
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Internet {

		/**
		 * Method to get the user agent
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getUserAgent() {
			return new Faker().internet().userAgent();
		}

		/**
		 * Method to get the domain name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDomainName() {
			return new Faker().internet().domainName();
		}

		/**
		 * Method to get the email address
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getEmailAddress() {
			return new Faker().internet().safeEmailAddress();
		}

		/**
		 * Method to get the HTTP method
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getHTTPMethod() {
			return new Faker().internet().httpMethod();
		}

		/**
		 * Method to get the image URL
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getImageURL() {
			return new Faker().internet().image();
		}

		/**
		 * Method to get the image URL
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param width
		 * @param height
		 * @return
		 */
		public static String getImageURL(int width, int height) {
			return new Faker().internet().image(width, height);
		}

		/**
		 * Method to get the URL
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getURL() {
			return new Faker().internet().url();
		}

		/**
		 * Method to get the MAC Address
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMACAddress() {
			return new Faker().internet().macAddress();
		}

		/**
		 * Method to get the UUID
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getUUID() {
			return new Faker().internet().uuid();
		}

		/**
		 * Method to get the port number
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static int getPort() {
			return new Faker().internet().port();
		}
	}

	/***
	 * Class to the test data generator methods related to Job
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Job {

		/**
		 * Method to get the job title
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getJobTitle() {
			return new Faker().job().title();
		}

		/**
		 * Method to get the job field
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getJobField() {
			return new Faker().job().field();
		}

		/**
		 * Method to get the key skills
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getKeySkills() {
			return new Faker().job().keySkills();
		}

		/**
		 * Method to get the job position
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getJobPosition() {
			return new Faker().job().position();
		}
	}

	/***
	 * Class to the test data generator methods related to Measurement
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Measurement {

		/**
		 * Method to get the height measurement names
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getHeight() {
			return new Faker().measurement().height();
		}

		/**
		 * Method to get the weight measurement names
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getWeight() {
			return new Faker().measurement().weight();
		}

		/**
		 * Method to get the length measurement names
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getLength() {
			return new Faker().measurement().length();
		}

		/**
		 * Method to get the volume measurement names
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVolume() {
			return new Faker().measurement().volume();
		}
	}

	/***
	 * Class to the test data generator methods related to Medical
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Medical {

		/**
		 * Method to get the hospital name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getHospitalName() {
			return new Faker().medical().hospitalName();
		}

		/**
		 * Method to get the disease name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDiseaseName() {
			return new Faker().medical().diseaseName();
		}

		/**
		 * Method to get the medicine name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMedicineName() {
			return new Faker().medical().medicineName();
		}

		/**
		 * Method to get the diagnosis code
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDiagnosisCode() {
			return new Faker().medical().diagnosisCode();
		}

		/**
		 * Method to get the symptoms name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getSymptoms() {
			return new Faker().medical().symptoms();
		}
	}

	/***
	 * Class to the test data generator methods related to Mountain
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Mountain {

		/**
		 * Method to get the mountain name
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMountainName() {
			return new Faker().mountain().name();
		}

		/**
		 * Method to get the mountain range
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMountainRange() {
			return new Faker().mountain().range();
		}
	}

	/***
	 * Class to the test data generator methods related to Number
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Number {
		/**
		 * Method to get the digit
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getDigit() {
			return new Faker().number().digit();
		}

		/**
		 * Method to get the digits based on count
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param count
		 * @return
		 */
		public static String getDigits(int count) {
			return new Faker().number().digits(count);
		}

		/**
		 * Method to get the digits between minimum and maximum
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param min
		 * @param max
		 * @return
		 */
		public static int getDigits(int min, int max) {
			return new Faker().number().numberBetween(min, max);
		}

		/**
		 * Method to get the random double number
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param maxNumberOfDecimals
		 * @param min
		 * @param max
		 * @return
		 */
		public static double getRandomDouble(int maxNumberOfDecimals, int min, int max) {
			return new Faker().number().randomDouble(maxNumberOfDecimals, min, max);
		}

		/**
		 * Method to get the negative number
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static int getNegativeNumber() {
			return new Faker().number().negative();
		}

	}

	/***
	 * Class to the test data generator methods related to Vehicle
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Vehicle {
		/**
		 * Method to get the vehicle manufacturer
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVehicleManufacturer() {
			return new Faker().vehicle().manufacturer();
		}

		/**
		 * Method to get the vehicle model
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVehicleModel() {
			return new Faker().vehicle().model();
		}

		/**
		 * Method to get the vehicle make
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVehicleMake() {
			return new Faker().vehicle().make();
		}

		/**
		 * Method to get the vehicle make and model
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getMakeAndModel() {
			return new Faker().vehicle().makeAndModel();
		}

		/**
		 * Method to get the vehicle fuel type
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getFuelType() {
			return new Faker().vehicle().fuelType();
		}

		/**
		 * Method to get the vehicle engine
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVehicleEngine() {
			return new Faker().vehicle().engine();
		}

		/**
		 * Method to get the vehicle VIN
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVehicleVIN() {
			return new Faker().vehicle().vin();
		}

		/**
		 * Method to get the vehicle license plate
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getVehicleLicensePlate() {
			return new Faker().vehicle().licensePlate();
		}
	}

	/***
	 * Class to the test data generator methods related to Weather
	 * 
	 * @author sanoj.swaminathan
	 * @since 15-05-2023
	 *
	 */
	public static class Weather {

		/**
		 * Method to get the weather description
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getWeatherDescription() {
			return new Faker().weather().description();
		}

		/**
		 * Method to get the temperature Celsius
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getTemperatureCelsius() {
			return new Faker().weather().temperatureCelsius();
		}

		/**
		 * Method to get the temperature Celsius based on the minimum and maximum
		 * temperature
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param minTemperature
		 * @param maxTemperature
		 * @return
		 */
		public static String getTemperatureCelsius(int minTemperature, int maxTemperature) {
			return new Faker().weather().temperatureCelsius(minTemperature, maxTemperature);
		}

		/**
		 * Method to get the temperature Fahrenheit
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @return
		 */
		public static String getTemperatureFahrenheit() {
			return new Faker().weather().temperatureCelsius();
		}

		/**
		 * Method to get the temperature Fahrenheit based on the minimum and maximum
		 * temperature
		 * 
		 * @author sanoj.swaminathan
		 * @since 15-05-2023
		 * @param minTemperature
		 * @param maxTemperature
		 * @return
		 */
		public static String getTemperatureFahrenheit(int minTemperature, int maxTemperature) {
			return new Faker().weather().temperatureFahrenheit(minTemperature, maxTemperature);
		}
	}
}
