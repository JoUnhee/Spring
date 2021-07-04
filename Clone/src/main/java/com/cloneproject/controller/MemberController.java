package com.cloneproject.controller;

import com.cloneproject.domain.Member;
import com.cloneproject.domain.SearchInfo;
import com.cloneproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController (MemberService memberService){
        this.memberService = memberService;
    }


    @GetMapping("/members")
    public String ListMember(Model model) {
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("members",memberList);
        return "/members/memberList";
    }

    /* 회원 가입 */
    @GetMapping("/members/new")
    public String newMember(Model model) {
        MemberForm memberForm = new MemberForm();
        model.addAttribute("member",memberForm);
        return "/members/createORupdateMember";
    }
    @PostMapping("/members/new")
    public String create(MemberForm memberForm){
        Long memberId = memberService.join(memberForm);
        return "redirect:/members/" + memberId;
    }

    /* 회원 정보 및 수정 */
    @GetMapping("/members/{memberId}")
    public ModelAndView detailsMember(@PathVariable Long memberId ) {
        ModelAndView mav = new ModelAndView("members/memberDetails");
        Member member = memberService.findOne(memberId).get();
        mav.addObject(member);
        return mav;
    }

    @GetMapping("/members/{memberId}/edit")
    public String editMember(@PathVariable Long memberId, Model model) {
        Member member = memberService.findOne(memberId).get();
        model.addAttribute("member",member);
        return "/members/createORupdateMember";
    }

    @PostMapping("/members/{memberId}/edit")
    public String updateMember(MemberForm member, @PathVariable Long memberId) {

        memberService.update(memberId, member);
        return "redirect:/members/{memberId}";
    }

    @GetMapping("/members/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.deleteOne(memberId);
        return "redirect:/members";
    }


    @GetMapping("/members/searchMembers")
    public String initBeforeSearch(Model model){
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setSearchFlag(false);
        searchInfo.setSearchKey("");
        model.addAttribute("searchInfo",searchInfo);
        return "/members/findMembers";
    }

    @PostMapping("/members/searchMembers")
    public String searchMember(SearchInfo searchInfo, Model model){

        List<Member> foundMembers = new ArrayList<Member>();

        if( searchInfo.getSearchFlag() ) {
            foundMembers = memberService.findTeamMember(searchInfo.getSearchKey());
        } else {
            Member m = memberService.findByName(searchInfo.getSearchKey()).get();
            foundMembers.add(m);
        }

        model.addAttribute("members",foundMembers);
        return "/members/memberList";
    }
}
