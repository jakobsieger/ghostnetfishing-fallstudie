package de.jakobsieger.ghostnetfishing.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class GhostNet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private double latitude;
	private double longitude;
	private double size;

	@Enumerated(EnumType.STRING)
	private GhostNetStatus status;

	@ManyToOne
	private Person reportedBy;

	@ManyToOne
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