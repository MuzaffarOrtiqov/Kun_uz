package com.example.Kun_uz.controller;

import com.example.Kun_uz.dto.history.EmailHistoryDTO;
import com.example.Kun_uz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/emailHistory")
@RestController
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    // get email history by the email of the sender

    @GetMapping("/getByEmail")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(emailHistoryService.getByEmail(email));
    }
    @GetMapping("/getEmailByGivenDate")
    public ResponseEntity<List<EmailHistoryDTO>> getEmailByGivenDate(@RequestParam(name = "given_date") LocalDate givenDate) {
        return ResponseEntity.ok().body(emailHistoryService.getEmailByGivenDate(givenDate));
    }
    @PostMapping("/pagination")
    public ResponseEntity<Page<EmailHistoryDTO>> pagination(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(emailHistoryService.pagination(page-1, size));
    }


}
