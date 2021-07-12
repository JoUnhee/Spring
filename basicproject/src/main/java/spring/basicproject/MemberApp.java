package spring.basicproject;

import spring.basicproject.member.Grade;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;

public class MemberApp {
    /* psvm + enter */
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "조운희", Grade.VIP);
        memberService.join(member);

        Member findMember= memberService.findMember(1L);
        System.out.println("new Member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
