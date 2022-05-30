package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Offer;
import com.example.nomoneytrade.entity.Post;
import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.payload.requests.GetOffersRequest;
import com.example.nomoneytrade.payload.requests.MakeOfferRequest;
import com.example.nomoneytrade.payload.requests.SetOfferStatus;
import com.example.nomoneytrade.payload.responses.*;
import com.example.nomoneytrade.repository.OffersRepository;
import com.example.nomoneytrade.repository.PostRepository;
import com.example.nomoneytrade.repository.UserRepository;
import com.example.nomoneytrade.utils.ConditionEnum;
import com.example.nomoneytrade.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OffersController {

    @Autowired
    OffersRepository offersRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/get_my_offers")
    public ResponseEntity<?> getMyOffers(@RequestBody @Valid GetOffersRequest getOffersRequest) {
        List<OfferResponse> offerResponses = new ArrayList<>();
        Long user_id = getOffersRequest.getUserId();
        try {
            List<Offer> offers = offersRepository.findByBuyerId(user_id).orElseThrow(() -> new RuntimeException("Offers not found"));
            for (Offer offer : offers) {
                User buyer = userRepository.findById(offer.getBuyerId()).get();
                User customer = userRepository.findById(offer.getCustomerId()).get();
                String city = offer.getCity();
                String time = offer.getTime();
                Post buyerPost = postRepository.findById(offer.getBuyerPostId()).get();
                Post customerPost = postRepository.findById(offer.getCustomerPostId()).get();
                ConditionEnum state = offer.getState();
                offerResponses.add(new OfferResponse(buyerPost, customerPost, city, time, buyer, customer, state));
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            return new ResponseEntity<String>("Offers not found", HttpStatus.BAD_REQUEST);
        }

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllOfferResponse(offerResponses));
    }

    @PostMapping("/get_to_me_offers")
    public ResponseEntity<?> getToMeOffers(@RequestBody @Valid GetOffersRequest getOffersRequest) {
        List<OfferResponse> offerResponses = new ArrayList<>();
        Long user_id = getOffersRequest.getUserId();
        try {
            List<Offer> offers = offersRepository.findByCustomerId(user_id).orElseThrow(() -> new RuntimeException("Offers not found"));
            for (Offer offer : offers) {
                User buyer = userRepository.findById(offer.getBuyerId()).get();
                User customer = userRepository.findById(offer.getCustomerId()).get();
                String city = offer.getCity();
                String time = offer.getTime();
                Post buyerPost = postRepository.findById(offer.getBuyerPostId()).get();
                Post customerPost = postRepository.findById(offer.getCustomerPostId()).get();
                ConditionEnum state = offer.getState();
                offerResponses.add(new OfferResponse(buyerPost, customerPost, city, time, buyer, customer, state));
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("Offer not found", HttpStatus.BAD_REQUEST);
        }

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllOfferResponse(offerResponses));
    }

    @PostMapping("/new_offer")
    public ResponseEntity<?> createOffer(@RequestBody @Valid MakeOfferRequest makeOfferRequest) {
        Long buyerPostId = makeOfferRequest.getBuyerPostId();
        Long customerPostId = makeOfferRequest.getCustomerPostId();
        String city = makeOfferRequest.getCity();
        String time = makeOfferRequest.getTime();
        Long buyerId = makeOfferRequest.getBuyerId();
        Long customerId = makeOfferRequest.getCustomerId();
        ConditionEnum state = makeOfferRequest.getState();
        
        Offer offer = new Offer(buyerPostId, customerPostId, city, time, buyerId, customerId, state);

        offersRepository.save(offer);

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new BaseResponse("Offer has been created"));
    }

    @PostMapping("/set_offer_status")
    public ResponseEntity<?> setOfferStatus(@RequestBody @Valid SetOfferStatus setOfferStatus) {
        Long offerId = setOfferStatus.getOfferId();
        ConditionEnum state = setOfferStatus.getState();

        Offer offer = offersRepository.getById(offerId);
        offer.setState(state);

        offersRepository.save(offer);

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new BaseResponse("Offer has been changed"));
    }
}
