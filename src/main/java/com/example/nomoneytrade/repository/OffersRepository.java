package com.example.nomoneytrade.repository;

import com.example.nomoneytrade.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OffersRepository extends JpaRepository<Offer, Long> {
    Optional<List<Offer>> findByUserId(Long id);
}
