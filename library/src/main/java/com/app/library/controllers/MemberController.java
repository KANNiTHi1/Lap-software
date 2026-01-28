package com.app.library.controller;

import com.app.library.model.Member;
import com.app.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final LibraryService libraryService;

    public MemberController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // GET /api/members
    @GetMapping
    public List<Member> getAllMembers() {
        return libraryService.getAllMembers();
    }

    // POST /api/members
    @PostMapping
    public Member addMember(@RequestBody Member member) {
        libraryService.addMember(member);
        return member;
    }

    // PUT /api/members/{id}
    @PutMapping("/{id}")
    public Member updateMember(
            @PathVariable Long id,
            @RequestBody Member member) {
        member.setId(id);
        libraryService.updateMember(member);
        return member;
    }

    // DELETE /api/members/{id}
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        libraryService.deleteMember(id);
    }
}
