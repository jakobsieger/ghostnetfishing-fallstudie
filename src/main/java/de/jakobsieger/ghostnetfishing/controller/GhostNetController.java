package de.jakobsieger.ghostnetfishing.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.jakobsieger.ghostnetfishing.model.GhostNet;
import de.jakobsieger.ghostnetfishing.model.GhostNetStatus;
import de.jakobsieger.ghostnetfishing.model.Person;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class GhostNetController implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<GhostNet> ghostNets = new ArrayList<GhostNet>();
	private GhostNet newGhostNet = new GhostNet();
	private Person newReportingPerson = new Person();
	private boolean reportFormVisible = false;

	public GhostNetController() {
		GhostNet ghostNetTest1 = new GhostNet();
		ghostNetTest1.setId(1);
		ghostNetTest1.setLatitude(55.2345);
		ghostNetTest1.setLongitude(10.2345);
		ghostNetTest1.setSize(56.0);
		ghostNetTest1.setStatus(GhostNetStatus.REPORTED);

		ghostNets.add(ghostNetTest1);
	}

	public List<GhostNet> getGhostNets() {
		return ghostNets;
	}

	public GhostNet getNewGhostNet() {
		return newGhostNet;
	}

	public Person getNewReportingPerson() {
		return newReportingPerson;
	}

	public void setNewReportingPerson(Person newReportingPerson) {
		this.newReportingPerson = newReportingPerson;
	}

	public void setNewGhostNet(GhostNet newGhostNet) {
		this.newGhostNet = newGhostNet;
	}

	public boolean isReportFormVisible() {
		return reportFormVisible;
	}

	public void showReportForm() {
		reportFormVisible = true;
	}

	public void reportNewGhostNet() {
		newGhostNet.setId(ghostNets.size() + 1);
		newGhostNet.setStatus(GhostNetStatus.REPORTED);

		if (newReportingPerson.getName() == null || newReportingPerson.getName() == ""
				|| newReportingPerson.getPhone() == null || newReportingPerson.getPhone() == "") {
			newGhostNet.setReportedBy(null);
		} else {
			newReportingPerson.setId(newGhostNet.getId());
			newGhostNet.setReportedBy(newReportingPerson);
		}

		ghostNets.add(newGhostNet);

		newGhostNet = new GhostNet();
		newReportingPerson = new Person();
		reportFormVisible = false;
	}
}