package com.filmflicks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.filmflicks.models.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
