package de.jakobsieger.ghostnetfishing.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.jakobsieger.ghostnetfishing.model.GhostNet;
import de.jakobsieger.ghostnetfishing.model.GhostNetStatus;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class GhostNetController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<GhostNet> ghostNets = new ArrayList<GhostNet>();
	
	public List<GhostNet> getGhostNets() {
		return ghostNets;
	}
	
	public GhostNetController() {
		GhostNet ghostNetTest1 = new GhostNet();
		ghostNetTest1.setId(1);
		ghostNetTest1.setLatitude(55.2345);
		ghostNetTest1.setLongitude(10.2345);
		ghostNetTest1.setSize(56.0);
		ghostNetTest1.setStatus(GhostNetStatus.REPORTED);
		
		ghostNets.add(ghostNetTest1);
	}
}