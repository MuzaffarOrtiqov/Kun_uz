package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.history.SmsHistoryDTO;
import com.example.Kun_uz.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/smsHistory")
@RestController
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;

    // get sms history by the phone of the sender
    @GetMapping("/getByPhone")
    public ResponseEntity<List<SmsHistoryDTO>> getByPhone(@RequestParam String phone) {
        return ResponseEntity.ok().body(smsHistoryService.getByPhone(phone));
    }
    @GetMapping("/getSmsByGivenDate")
    public ResponseEntity<List<SmsHistoryDTO>> getSmsByGivenDate(@RequestParam(name = "given_date") LocalDate givenDate) {
        return ResponseEntity.ok().body(smsHistoryService.getSmsByGivenDate(givenDate));
    }
    @PostMapping("/pagination")
    public ResponseEntity<Page<SmsHistoryDTO>> pagination(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(smsHistoryService.pagination(page-1, size));
    }
}
