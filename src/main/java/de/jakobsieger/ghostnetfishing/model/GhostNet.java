package de.jakobsieger.ghostnetfishing.model;

import java.io.Serializable;

public class GhostNet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private double latitude;
	private double longitude;
	private double size;
	private GhostNetStatus status;
	private Person reportedBy;
	private Person salvageRegisteredBy;

	public GhostNet() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public GhostNetStatus getStatus() {
		return status;
	}

	public void setStatus(GhostNetStatus status) {
		this.status = status;
	}

	public Person getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(Person reportedBy) {
		this.reportedBy = reportedBy;
	}

	public Person getSalvageRegisteredBy() {
		return salvageRegisteredBy;
	}

	public void setSalvageRegisteredBy(Person salvageRegisteredBy) {
		this.salvageRegisteredBy = salvageRegisteredBy;
	}
}