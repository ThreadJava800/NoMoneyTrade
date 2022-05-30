package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Offer;
import com.example.nomoneytrade.payload.requests.GetOffersRequest;
import com.example.nomoneytrade.payload.requests.MakeOfferRequest;
import com.example.nomoneytrade.payload.requests.SetOfferStatus;
import com.example.nomoneytrade.payload.responses.AllOfferResponse;
import com.example.nomoneytrade.payload.responses.AllPostResponse;
import com.example.nomoneytrade.payload.responses.BaseResponse;
import com.example.nomoneytrade.payload.responses.UserCredentials;
import com.example.nomoneytrade.repository.OffersRepository;
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
    JwtUtils jwtUtils;

    @PostMapping("/get_my_offers")
    public ResponseEntity<?> getMyOffers(@RequestBody @Valid GetOffersRequest getOffersRequest) {
        List<Offer> offers;
        Long user_id = getOffersRequest.getUserId();
        try {
            offers = offersRepository.findByBuyerId(user_id).orElseThrow(() -> new RuntimeException("Offer not found"));
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("Offer not found", HttpStatus.BAD_REQUEST);
        }

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllOfferResponse(offers));
    }

    @PostMapping("/get_to_me_offers")
    public ResponseEntity<?> getToMeOffers(@RequestBody @Valid GetOffersRequest getOffersRequest) {
        List<Offer> offers;
        Long user_id = getOffersRequest.getUserId();
        try {
            offers = offersRepository.findByCustomerId(user_id).orElseThrow(() -> new RuntimeException("Offer not found"));
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("Offer not found", HttpStatus.BAD_REQUEST);
        }

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllOfferResponse(offers));
    }

    @PostMapping("/new_offer")
    public ResponseEntity<?> createOffer(@RequestBody @Valid MakeOfferRequest makeOfferRequest) {
        Long buyerPostId = makeOfferRequest.getBuyerPostId();
        Long customerPostId = makeOfferRequest.getBuyerPostId();
        String city = makeOfferRequest.getCity();
        String time = makeOfferRequest.getTime();
        Long buyerId = makeOfferRequest.getBuyerId();
        Long customerId = makeOfferRequest.getCustomerId();
        ConditionEnum state = makeOfferRequest.getState();

        //customer, then buyer
        Offer offer = new Offer(customerPostId, buyerPostId, city, time, buyerId, customerId, state);

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
