package org.spring.managinglibrary.managinglibrary.service;

import org.spring.managinglibrary.managinglibrary.dto.MemberRequestDTO;
import org.spring.managinglibrary.managinglibrary.dto.MemberResponseDTO;
import org.spring.managinglibrary.managinglibrary.mapper.MemberMapper;
import org.spring.managinglibrary.managinglibrary.model.Member;
import org.spring.managinglibrary.managinglibrary.exception.DuplicateResourceException;
import org.spring.managinglibrary.managinglibrary.exception.ResourceNotFoundException;
import org.spring.managinglibrary.managinglibrary.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public MemberResponseDTO addMember(MemberRequestDTO memberRequestDTO) {
        if (memberRepository.existsByEmail(memberRequestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + memberRequestDTO.getEmail());
        }
        Member member = memberMapper.toEntity(memberRequestDTO);
        Member saved=memberRepository.save(member);
        return memberMapper.toResponseDTO(saved);
    }

    public List<MemberResponseDTO> getAllMember() {
        return memberRepository.findAll().stream().map(memberMapper::toResponseDTO).collect(Collectors.toList());
    }

    public MemberResponseDTO getMemberById(Long id) throws ResourceNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return memberMapper.toResponseDTO(member);
    }

    public MemberResponseDTO getMemberByName(String name) throws ResourceNotFoundException {
        Member member = memberRepository.findByNameContainingIgnoreCase(name).orElseThrow(() -> new ResourceNotFoundException("Member not found with name: " + name));
        return memberMapper.toResponseDTO(member);
    }

    public MemberResponseDTO updateMember(Long id, MemberRequestDTO memberRequestDTO) throws ResourceNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        memberMapper.updateEntity(memberRequestDTO, member);
        return memberMapper.toResponseDTO(memberRepository.save(member));
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
