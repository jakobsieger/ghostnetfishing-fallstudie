package de.jakobsieger.ghostnetfishing.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.jakobsieger.ghostnetfishing.model.GhostNet;
import de.jakobsieger.ghostnetfishing.model.GhostNetStatus;
import de.jakobsieger.ghostnetfishing.model.Person;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

	private GhostNet selectedGhostNet;
	private Person newSalvagingPerson = new Person();
	private boolean salvagingFormVisible = false;
	private boolean salvagedFormVisible = false;

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

	public void setNewGhostNet(GhostNet newGhostNet) {
		this.newGhostNet = newGhostNet;
	}

	public Person getNewReportingPerson() {
		return newReportingPerson;
	}

	public void setNewReportingPerson(Person newReportingPerson) {
		this.newReportingPerson = newReportingPerson;
	}

	public boolean isReportFormVisible() {
		return reportFormVisible;
	}

	public GhostNet getSelectedGhostNet() {
		return selectedGhostNet;
	}

	public Person getNewSalvagingPerson() {
		return newSalvagingPerson;
	}

	public boolean isSalvagingFormVisible() {
		return salvagingFormVisible;
	}

	public boolean isSalvagedFormVisible() {
		return salvagedFormVisible;
	}

	// report form

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

	// salvaging form

	public void showSalvagingForm(GhostNet ghostNet) {
		salvagingFormVisible = true;
		selectedGhostNet = ghostNet;
	}

	public void registerSalvaging() {
		newSalvagingPerson.setId(123);
		selectedGhostNet.setSalvageRegisteredBy(newSalvagingPerson);
		selectedGhostNet.setStatus(GhostNetStatus.SALVAGE_REGISTERED);

		selectedGhostNet = null;
		newSalvagingPerson = new Person();

		salvagingFormVisible = false;
	}

	// salvaged form

	public void showSalvagedForm(GhostNet ghostNet) {
		salvagedFormVisible = true;
		selectedGhostNet = ghostNet;
	}

	public void reportAsSalvaged() {
		// check if salvaging person is the one who registered the salvage
		newSalvagingPerson.setId(123);
		Person salvageRegisteredBy = selectedGhostNet.getSalvageRegisteredBy();

		if (newSalvagingPerson.getName().equals(salvageRegisteredBy.getName())
				&& newSalvagingPerson.getPhone().equals(salvageRegisteredBy.getPhone())) {
			selectedGhostNet.setStatus(GhostNetStatus.SALVAGED);
			newSalvagingPerson = new Person();
			salvagedFormVisible = false;
		} else {
			FacesContext.getCurrentInstance().addMessage("salvagedFormMessages", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Personendaten müssen mit Anmeldung übereinstimmen", null));
		}
	}
}