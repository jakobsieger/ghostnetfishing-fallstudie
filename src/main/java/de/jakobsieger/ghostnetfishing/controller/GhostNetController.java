package de.jakobsieger.ghostnetfishing.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.jakobsieger.ghostnetfishing.dao.GhostNetDAO;
import de.jakobsieger.ghostnetfishing.dao.PersonDAO;
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

	private GhostNetDAO ghostNetDAO = new GhostNetDAO();
	private PersonDAO personDAO = new PersonDAO();

	private List<GhostNet> ghostNets = new ArrayList<GhostNet>();
	private GhostNet newGhostNet = new GhostNet();
	private Person formPerson = new Person();
	private GhostNet selectedGhostNet;

	private boolean reportFormVisible = false;
	private boolean salvagingFormVisible = false;
	private boolean salvagedFormVisible = false;
	private boolean lostFormVisible = false;

	private enum FormVisibility {
		REPORT_FORM, SALVAGING_FORM, SALVAGED_FORM, LOST_FORM, NONE
	}

	public GhostNetController() {
		ghostNets = ghostNetDAO.findAll();
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

	public void changeFormVisibility(FormVisibility visibility) {
		if (visibility.equals(FormVisibility.REPORT_FORM)) {
			reportFormVisible = true;
			salvagingFormVisible = false;
			salvagedFormVisible = false;
			lostFormVisible = false;
		} else if (visibility.equals(FormVisibility.SALVAGING_FORM)) {
			reportFormVisible = false;
			salvagingFormVisible = true;
			salvagedFormVisible = false;
			lostFormVisible = false;
		} else if (visibility.equals(FormVisibility.SALVAGED_FORM)) {
			reportFormVisible = false;
			salvagingFormVisible = false;
			salvagedFormVisible = true;
			lostFormVisible = false;
		} else if (visibility.equals(FormVisibility.LOST_FORM)) {
			reportFormVisible = false;
			salvagingFormVisible = false;
			salvagedFormVisible = false;
			lostFormVisible = true;
		} else if (visibility.equals(FormVisibility.NONE)) {
			reportFormVisible = false;
			salvagingFormVisible = false;
			salvagedFormVisible = false;
			lostFormVisible = false;
		}
	}

	// report form

	public void showReportForm() {
		changeFormVisibility(FormVisibility.REPORT_FORM);
	}

	public void reportNewGhostNet() {

		if (formPerson.getName().equals(null) || formPerson.getName().isBlank() || formPerson.getPhone().equals(null)
				|| formPerson.getPhone().isBlank()) {
			newGhostNet.setReportedBy(null);
		} else {
			Person existingPerson = personDAO.findByNameAndPhone(formPerson.getName(), formPerson.getPhone());
			newGhostNet.setReportedBy(existingPerson != null ? existingPerson : formPerson);
		}
		newGhostNet.setStatus(GhostNetStatus.REPORTED);
		ghostNetDAO.save(newGhostNet);
		ghostNets = ghostNetDAO.findAll();

		newGhostNet = new GhostNet();
		formPerson = new Person();
		changeFormVisibility(FormVisibility.NONE);
	}

	// salvaging form

	public void showSalvagingForm(GhostNet ghostNet) {
		changeFormVisibility(FormVisibility.SALVAGING_FORM);
		selectedGhostNet = ghostNet;
	}

	public void registerSalvaging() {
		Person existingPerson = personDAO.findByNameAndPhone(formPerson.getName(), formPerson.getPhone());
		selectedGhostNet.setSalvageRegisteredBy(existingPerson != null ? existingPerson : formPerson);
		selectedGhostNet.setStatus(GhostNetStatus.SALVAGE_REGISTERED);

		ghostNetDAO.update(selectedGhostNet);
		ghostNets = ghostNetDAO.findAll();

		selectedGhostNet = null;
		formPerson = new Person();

		changeFormVisibility(FormVisibility.NONE);
	}

	// salvaged form

	public void showSalvagedForm(GhostNet ghostNet) {
		changeFormVisibility(FormVisibility.SALVAGED_FORM);
		selectedGhostNet = ghostNet;
	}

	public void reportAsSalvaged() {
		Person salvageRegisteredBy = selectedGhostNet.getSalvageRegisteredBy();

		if (formPerson.getName().equals(salvageRegisteredBy.getName())
				&& formPerson.getPhone().equals(salvageRegisteredBy.getPhone())) {
			selectedGhostNet.setStatus(GhostNetStatus.SALVAGED);
			ghostNetDAO.update(selectedGhostNet);
			formPerson = new Person();
			changeFormVisibility(FormVisibility.NONE);
		} else {
			FacesContext.getCurrentInstance().addMessage("salvagedFormMessages", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Personendaten müssen mit Anmeldung übereinstimmen", null));
		}
	}

	// lost form

	public void showLostForm(GhostNet ghostNet) {
		changeFormVisibility(FormVisibility.LOST_FORM);
		selectedGhostNet = ghostNet;
	}

	public void reportAsLost() {
		selectedGhostNet.setStatus(GhostNetStatus.LOST);
		ghostNetDAO.update(selectedGhostNet);

		selectedGhostNet = null;
		formPerson = new Person();

		changeFormVisibility(FormVisibility.NONE);
	}
}