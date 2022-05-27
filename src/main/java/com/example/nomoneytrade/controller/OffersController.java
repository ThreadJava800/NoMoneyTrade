package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Offer;
import com.example.nomoneytrade.payload.requests.MakeOfferRequest;
import com.example.nomoneytrade.repository.OffersRepository;
import com.example.nomoneytrade.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OffersController {

    @Autowired
    OffersRepository offersRepository;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/get_my_offers")
    public ResponseEntity<List<Offer>> getMyOffers(@RequestBody @Valid Long user_id) {
        Optional<List<Offer>> offers = offersRepository.findByUserId(user_id);

        return offers.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(Collections.emptyList()));
    }

    @PostMapping("/new_offer")
    public ResponseEntity<?> createOffer(@RequestBody @Valid MakeOfferRequest makeOfferRequest) {
        Long postId = makeOfferRequest.getPostId();
        String city = makeOfferRequest.getCity();
        String time = makeOfferRequest.getTime();
        Long userId = makeOfferRequest.getUserId();

        Offer offer = new Offer(postId, city, time, userId);

        offersRepository.save(offer);

        return ResponseEntity.ok("Post has been created.");
    }
}
