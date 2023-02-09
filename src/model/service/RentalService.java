package model.service;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double PricePerHour;
	private Double PricePerDay;
	
	private BrazilTaxService taxService;

	public RentalService() {
		
	}

	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
	
		PricePerHour = pricePerHour;
		PricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return PricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		PricePerHour = pricePerHour;
	}

	public Double getPricePerDay() {
		return PricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		PricePerDay = pricePerDay;
	}

	public BrazilTaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(BrazilTaxService taxService) {
		this.taxService = taxService;
	} 
	
	public void processInvoice(CarRental carRental) {
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes/60;
		
		double basicPayment;
		if(hours <= 12) {
			basicPayment = PricePerHour * Math.ceil(hours);
		}
		else {
			basicPayment = PricePerDay * Math.ceil(hours/24);
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
