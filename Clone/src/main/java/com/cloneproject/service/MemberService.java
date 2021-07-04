package com.cloneproject.service;

import com.cloneproject.controller.MemberForm;
import com.cloneproject.domain.Member;
import com.cloneproject.repository.MemberRepository;
import com.cloneproject.repository.SpringDataMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
 * 1. Create (join)
 * 2. Read (Find Series)
 * 3. Update (Update)
 * 4. Delete (delete series)
 */
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 */
    public Long join(MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setTeam(memberForm.getTeam());

        CheckDuplicateName(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void CheckDuplicateName(Member member) {
        /* 시간 남으면, A, B등 붙여서 해보기*/
        memberRepository.findByName(member.getName()).ifPresent(m-> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    public Optional<Member> findByName (String name) { return memberRepository.findByName(name);}
    public List<Member> findMembers () { return memberRepository.findAll();}
    public Optional<Member> findOne (Long memberId) { return memberRepository.findById(memberId); }
    public List<Member> findTeamMember(String team) { return memberRepository.findByTeam(team); }
    /* error handling 물어보기 */
    public Long update(MemberForm oldForm,MemberForm newForm) {
        Optional<Member> objMember = memberRepository.findByName(oldForm.getName());

        objMember.ifPresent(changed -> {
            objMember.get().setName(newForm.getName());
            objMember.get().setTeam(newForm.getTeam());
            memberRepository.save(objMember.get());
        });

        return objMember.get().getId();
    }

    public Long update(Long targetMemberId, MemberForm newForm) {
        Optional<Member> objMember = memberRepository.findById(targetMemberId);

        objMember.ifPresent(changed -> {
            objMember.get().setName(newForm.getName());
            objMember.get().setTeam(newForm.getTeam());
            objMember.get().setId(targetMemberId);
            memberRepository.save(objMember.get());
        });

        return objMember.get().getId();
    }

    public void deleteOne(Long id) {
        memberRepository.delete(memberRepository.findById(id).get());
    }
    public void deleteAll() {
        List<Member> Members = memberRepository.findAll();
        for (Member member : Members) {
            memberRepository.delete(member);
        }
    }

}
