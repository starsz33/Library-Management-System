package org.spring.managinglibrary.managinglibrary.controller;

import org.spring.managinglibrary.managinglibrary.dto.MemberRequestDTO;
import org.spring.managinglibrary.managinglibrary.dto.MemberResponseDTO;
import org.spring.managinglibrary.managinglibrary.exception.ResourceNotFoundException;
import org.spring.managinglibrary.managinglibrary.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Members")
public class MemberController {
   private final MemberService memberService;

   public MemberController(MemberService memberService) {
       this.memberService = memberService;
   }

   @PostMapping
   public ResponseEntity<MemberResponseDTO> addMember(@Valid @RequestBody MemberRequestDTO memberRequestDTO){
       return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(memberRequestDTO));
   }
   @GetMapping
   public ResponseEntity<List<MemberResponseDTO>> getAllMember(){
       return ResponseEntity.ok(memberService.getAllMember());
   }
   @GetMapping("/{id}")
   public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) throws ResourceNotFoundException {
       return ResponseEntity.ok(memberService.getMemberById(id));
   }
   @GetMapping("/search")
   public ResponseEntity<MemberResponseDTO> getMemberByName(@RequestParam String name) throws ResourceNotFoundException {
       return ResponseEntity.ok(memberService.getMemberByName(name));
   }
   @PutMapping("/{id}")
   public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO memberRequestDTO) throws ResourceNotFoundException {
       return ResponseEntity.ok(memberService.updateMember(id, memberRequestDTO));
   }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
