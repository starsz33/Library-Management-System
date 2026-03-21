package org.spring.managinglibrary.managinglibrary.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.spring.managinglibrary.managinglibrary.dto.MemberRequestDTO;
import org.spring.managinglibrary.managinglibrary.dto.MemberResponseDTO;
import org.spring.managinglibrary.managinglibrary.exception.ResourceNotFoundException;
import org.spring.managinglibrary.managinglibrary.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Members")
@RequiredArgsConstructor
public class MemberController {
   private final MemberService memberService;
   @PostMapping
   public ResponseEntity<MemberResponseDTO>addMember(@RequestBody MemberRequestDTO memberRequestDTO){
       MemberResponseDTO memberResponseDTO= memberService.addMember(memberRequestDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body(memberResponseDTO);
   }
   @GetMapping
   public ResponseEntity<List<MemberResponseDTO>>getAllMember(){
       return ResponseEntity.ok(memberService.getAllMember());
   }
   @GetMapping("/{Id}")
   public MemberResponseDTO getMemberById(@PathVariable Long Id) throws ResourceNotFoundException {
       return memberService.getMemberById(Id);
   }
   @PutMapping("/{Id}")
   public MemberResponseDTO updateMember(@PathVariable Long Id, @RequestBody MemberRequestDTO memberRequestDTO) throws ResourceNotFoundException {
       return memberService.updateMember(Id, memberRequestDTO);
   }
   @DeleteMapping("/{Id}")
   public ResponseEntity<String>deleteMember(@PathVariable Long Id){
       memberService.deleteMember(Id);
       return ResponseEntity.ok("Member Deleted successfull");
   }
}
