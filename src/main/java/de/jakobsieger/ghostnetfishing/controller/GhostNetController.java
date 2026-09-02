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
	private Person formPerson = new Person();
	private boolean reportFormVisible = false;

	private GhostNet selectedGhostNet;
	private boolean salvagingFormVisible = false;
	private boolean salvagedFormVisible = false;
	private boolean lostFormVisible = false;

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

	public Person getFormPerson() {
		return formPerson;
	}

	public void setNewReportingPerson(Person newFormPerson) {
		this.formPerson = newFormPerson;
	}

	public boolean isReportFormVisible() {
		return reportFormVisible;
	}

	public GhostNet getSelectedGhostNet() {
		return selectedGhostNet;
	}

	public boolean isSalvagingFormVisible() {
		return salvagingFormVisible;
	}

	public boolean isSalvagedFormVisible() {
		return salvagedFormVisible;
	}
	
	public boolean isLostFormVisible() {
		return lostFormVisible;
	}

	// report form

	public void showReportForm() {
		reportFormVisible = true;
	}

	public void reportNewGhostNet() {
		newGhostNet.setId(ghostNets.size() + 1);
		newGhostNet.setStatus(GhostNetStatus.REPORTED);

		if (formPerson.getName().equals(null) || formPerson.getName().isBlank()
				|| formPerson.getPhone().equals(null) || formPerson.getPhone().isBlank()) {
			newGhostNet.setReportedBy(null);
		} else {
			formPerson.setId(newGhostNet.getId());
			newGhostNet.setReportedBy(formPerson);
		}

		ghostNets.add(newGhostNet);

		newGhostNet = new GhostNet();
		formPerson = new Person();
		reportFormVisible = false;
	}

	// salvaging form

	public void showSalvagingForm(GhostNet ghostNet) {
		salvagingFormVisible = true;
		selectedGhostNet = ghostNet;
	}

	public void registerSalvaging() {
		formPerson.setId(123);
		selectedGhostNet.setSalvageRegisteredBy(formPerson);
		selectedGhostNet.setStatus(GhostNetStatus.SALVAGE_REGISTERED);

		selectedGhostNet = null;
		formPerson = new Person();

		salvagingFormVisible = false;
	}

	// salvaged form

	public void showSalvagedForm(GhostNet ghostNet) {
		salvagedFormVisible = true;
		selectedGhostNet = ghostNet;
	}

	public void reportAsSalvaged() {
		formPerson.setId(123);
		Person salvageRegisteredBy = selectedGhostNet.getSalvageRegisteredBy();

		if (formPerson.getName().equals(salvageRegisteredBy.getName())
				&& formPerson.getPhone().equals(salvageRegisteredBy.getPhone())) {
			selectedGhostNet.setStatus(GhostNetStatus.SALVAGED);
			formPerson = new Person();
			salvagedFormVisible = false;
		} else {
			FacesContext.getCurrentInstance().addMessage("salvagedFormMessages", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Personendaten müssen mit Anmeldung übereinstimmen", null));
		}
	}
	
	// lost form
	
	public void showLostForm(GhostNet ghostNet) {
		lostFormVisible = true;
		selectedGhostNet = ghostNet;
	}
	
	public void reportAsLost() {
		formPerson.setId(123);
		selectedGhostNet.setStatus(GhostNetStatus.LOST);
		
		selectedGhostNet = null;
		formPerson = new Person();
		
		lostFormVisible = false;
	}
}