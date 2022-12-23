package com.binar.finalproject.BEFlightTicket.controller;

import com.binar.finalproject.BEFlightTicket.dto.*;
import com.binar.finalproject.BEFlightTicket.service.TravelerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/traveler-list")
public class TravelerListController {
    @Autowired
    private TravelerListService travelerListService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerTravelerList(@RequestBody TravelerListRequest travelerListRequest) {
        MessageModel messageModel = new MessageModel();

        TravelerListResponse travelerListResponse = travelerListService.registerTravelerList(travelerListRequest);
        if(travelerListResponse == null)
        {
            messageModel.setMessage("Failed register new traveler list");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new traveler list");
            messageModel.setData(travelerListResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @PostMapping("/add/from-order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerTravelerListFromOrder(@RequestBody List<TravelerListDetailRequest> travelerListDetailRequest) {
        MessageModel messageModel = new MessageModel();

        List<TravelerListDetailResponse> travelerListDetailResponse = travelerListService.registerTravelerListFromOrder(travelerListDetailRequest);
        if(travelerListDetailResponse == null)
        {
            messageModel.setMessage("Failed register new traveler list from order");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new traveler list from order");
            messageModel.setData(travelerListDetailResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping("/findby-userId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllUserTravelerList(@RequestParam("User Id") UUID userId){
        MessageModel messageModel = new MessageModel();
        try {
            List<TravelerListResponse> travelerListGet = travelerListService.searchAllUserTravelerList(userId);
            messageModel.setMessage("Success get all traveler list by userId : " + userId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(travelerListGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all traveler list by userId, " + userId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllTravelerList(){
        MessageModel messageModel = new MessageModel();
        try {
            List<TravelerListResponse> travelerListGet = travelerListService.searchAllTravelerList();
            messageModel.setMessage("Success get all traveler list");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(travelerListGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all traveler list");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> updateUser(@RequestParam("TravelerId") UUID travelerId, @RequestBody TravelerListUpdateRequest travelerListUpdateRequest) {
        MessageModel messageModel = new MessageModel();
        TravelerListResponse travelerListResponse = travelerListService.updateTravelerList(travelerListUpdateRequest, travelerId);

        if(travelerListResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed traveler-list with id : " + travelerId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update traveler-list with id : " + travelerId);
            messageModel.setData(travelerListResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
