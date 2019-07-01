package it.polito.tdp.flight.model;

public class LatLon {
	
	private Double latitudine1;
	private Double longitudine1;
	private Double latitudine2;
	private Double longitudine2;
	private Airport a1;
	private Airport a2;
	public LatLon(Double latitudine1, Double longitudine1, Double latitudine2, Double longitudine2, Airport a1,
			Airport a2) {
		super();
		this.latitudine1 = latitudine1;
		this.longitudine1 = longitudine1;
		this.latitudine2 = latitudine2;
		this.longitudine2 = longitudine2;
		this.a1 = a1;
		this.a2 = a2;
	}
	public Double getLatitudine1() {
		return latitudine1;
	}
	public void setLatitudine1(Double latitudine1) {
		this.latitudine1 = latitudine1;
	}
	public Double getLongitudine1() {
		return longitudine1;
	}
	public void setLongitudine1(Double longitudine1) {
		this.longitudine1 = longitudine1;
	}
	public Double getLatitudine2() {
		return latitudine2;
	}
	public void setLatitudine2(Double latitudine2) {
		this.latitudine2 = latitudine2;
	}
	public Double getLongitudine2() {
		return longitudine2;
	}
	public void setLongitudine2(Double longitudine2) {
		this.longitudine2 = longitudine2;
	}
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	
	

}
