package com.cloneproject.service;

import com.cloneproject.controller.MemberForm;
import com.cloneproject.domain.Member;
import com.cloneproject.repository.SpringDataMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private SpringDataMemberRepository memberRepository;
    @Autowired private MemberService memberService;

    @Test
    void 회원_가입() {
        MemberForm memberInfo = new MemberForm();

        memberInfo.setName("jounhee");
        memberInfo.setTeam("MW3-4");

        Long newId = memberService.join(memberInfo);
        Member findMember = memberService.findOne(newId).get();
        Assertions.assertThat(memberInfo.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 멤버_이름으로_조회() {
        MemberForm memberInfo = new MemberForm();

        memberInfo.setName("조운희");
        memberInfo.setTeam("MW3-4");

        Long newId = memberService.join(memberInfo);

        MemberForm memberInfo2 = new MemberForm();

        memberInfo2.setName("jean");
        memberInfo2.setTeam("MW3-4");

        Long newId2 = memberService.join(memberInfo2);

        MemberForm memberInfo3 = new MemberForm();

        memberInfo3.setName("someone");
        memberInfo3.setTeam("MW3-3");

        Long newId3 = memberService.join(memberInfo3);

        Member findMember = memberService.findByName("조운희").get();
        Assertions.assertThat(findMember.getName()).isEqualTo("조운희");
    }

    @Test
    void 팀_이름으로_조회() {
        MemberForm memberInfo = new MemberForm();

        memberInfo.setName("jounhee");
        memberInfo.setTeam("MW3-4");

        Long newId = memberService.join(memberInfo);

        MemberForm memberInfo2 = new MemberForm();

        memberInfo2.setName("jean");
        memberInfo2.setTeam("MW3-4");

        Long newId2 = memberService.join(memberInfo2);

        MemberForm memberInfo3 = new MemberForm();

        memberInfo3.setName("someone");
        memberInfo3.setTeam("MW3-3");

        Long newId3 = memberService.join(memberInfo3);

        List<Member> MW34 = memberRepository.findByTeam(memberInfo2.getTeam());
        Assertions.assertThat(MW34.size()).isEqualTo(2);

    }

    @Test
    void 모든_멤버_조회() {
        MemberForm memberInfo = new MemberForm();

        memberInfo.setName("jounhee");
        memberInfo.setTeam("MW3-4");

        System.out.println(memberService);
        Long newId = memberService.join(memberInfo);

        MemberForm memberInfo2 = new MemberForm();

        memberInfo2.setName("jean");
        memberInfo2.setTeam("MW3-4");

        Long newId2 = memberService.join(memberInfo2);

        List<Member> MW34 = memberService.findMembers();
        Assertions.assertThat(MW34.size()).isEqualTo(2);
    }

    @Test
    void 멤버_정보_업데이트() {
        MemberForm memberForm = new MemberForm();

        memberForm.setName("jounhee");
        memberForm.setTeam("MW3-4");

        memberService.join(memberForm);

        MemberForm newForm = new MemberForm();

        newForm.setName("COOLUNHEE");
        newForm.setTeam("MW3-4");
        Long newId = memberService.update(memberForm, newForm);
        Optional<Member> checkMember = memberService.findOne(newId);
        Assertions.assertThat(checkMember.get().getName()).isEqualTo("COOLUNHEE");

        memberForm.setName("COOLUNHEE");
        newForm.setTeam("AD Carry");
        newId = memberService.update(memberForm, newForm);
        checkMember = memberService.findOne(newId);
        Assertions.assertThat(checkMember.get().getTeam()).isEqualTo("AD Carry");

        List<Member> allMembers = memberService.findMembers();
        Assertions.assertThat(allMembers.size()).isEqualTo(1);

    }
    @Test
    void 멤버_정보_업데이트2() {
        MemberForm memberForm = new MemberForm();

        memberForm.setName("jounhee");
        memberForm.setTeam("MW3-4");

        Long oldId= memberService.join(memberForm);

        memberForm.setName("COOL");
        Long newId = memberService.update(oldId, memberForm);
        Optional<Member> checkMember = memberService.findOne(newId);
        Assertions.assertThat(checkMember.get().getName()).isEqualTo("COOL");

        List<Member> allMembers = memberService.findMembers();
        Assertions.assertThat(allMembers.size()).isEqualTo(1);

    }


    @Test
    void 단일_멤버_삭제() {
        MemberForm memberForm = new MemberForm();

        memberForm.setName("jounhee");
        memberForm.setTeam("MW3-4");

        Long newId = memberService.join(memberForm);

        memberService.deleteOne(newId);
        Assertions.assertThat(memberService.findOne(newId).isEmpty());
    }

    @Test
    void 모든_멤버_삭제() {
        MemberForm memberForm = new MemberForm();

        memberForm.setName("jounhee");
        memberForm.setTeam("MW3-4");

        Long newId = memberService.join(memberForm);

        MemberForm memberInfo2 = new MemberForm();

        memberInfo2.setName("jean");
        memberInfo2.setTeam("MW3-4");

        Long newId2 = memberService.join(memberInfo2);

        memberService.deleteAll();

        List<Member> members = memberService.findMembers();
        Assertions.assertThat(members.size()).isEqualTo(0);
    }
}