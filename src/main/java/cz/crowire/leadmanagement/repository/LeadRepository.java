package cz.crowire.leadmanagement.repository;

import cz.crowire.leadmanagement.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}