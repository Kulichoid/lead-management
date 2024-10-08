package cz.crowire.leadmanagement.controller;

import cz.crowire.leadmanagement.entity.Lead;
import cz.crowire.leadmanagement.repository.LeadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leads")
@Slf4j
public class LeadController {

  @Autowired private LeadRepository leadRepository;

  @GetMapping
  public List<Lead> getAllLeads() {
    log.info("Fetching all leads");
    return leadRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
    log.info("Fetching lead with ID: {}", id);
    Optional<Lead> lead = leadRepository.findById(id);
    return lead.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
    log.info("Creating new lead: {}", lead);
    Lead savedLead = leadRepository.save(lead);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedLead);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Lead> updateLead(@PathVariable Long id, @RequestBody Lead leadDetails) {
    log.info("Updating lead with ID: {}", id);
    Optional<Lead> leadOptional = leadRepository.findById(id);

    if (leadOptional.isPresent()) {
      Lead lead = leadOptional.get();
      lead.setName(leadDetails.getName());
      lead.setEmail(leadDetails.getEmail());
      return new ResponseEntity<>(leadRepository.save(lead), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
    log.info("Deleting lead with ID: {}", id);
    if (leadRepository.existsById(id)) {
      leadRepository.deleteById(id);
      log.info("Lead with ID: {} deleted", id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      log.warn("Lead with ID: {} not found", id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
