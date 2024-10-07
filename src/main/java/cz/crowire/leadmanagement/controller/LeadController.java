package cz.crowire.leadmanagement.controller;

import cz.crowire.leadmanagement.entity.Lead;
import cz.crowire.leadmanagement.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

  @Autowired private LeadRepository leadRepository;

  @GetMapping
  public List<Lead> getAllLeads() {
    return leadRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
    Optional<Lead> lead = leadRepository.findById(id);
    return lead.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Lead createLead(@RequestBody Lead lead) {
    return leadRepository.save(lead);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Lead> updateLead(@PathVariable Long id, @RequestBody Lead leadDetails) {
    Optional<Lead> leadOptional = leadRepository.findById(id);

    if (leadOptional.isPresent()) {
      Lead lead = leadOptional.get();
      lead.setName(leadDetails.getName());
      lead.setEmail(leadDetails.getEmail());
      lead.setStatus(leadDetails.getStatus());
      return new ResponseEntity<>(leadRepository.save(lead), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
    if (leadRepository.existsById(id)) {
      leadRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
