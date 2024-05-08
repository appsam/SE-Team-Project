package software.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import software.project.dto.JoinRequest;
import software.project.dto.LoginRequest;
import software.project.domain.Member;
import software.project.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/session-login")
public class SessionLoginController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, @SessionAttribute(name = "memberId", required = false) Long memberId) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member loginMember = memberService.getLoginMemberById(memberId);

        if(loginMember != null) {
            model.addAttribute("name", loginMember.getName());
        }

        return "member";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        // loginId 중복 체크
        if(memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "바밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "join";
        }

        memberService.join(joinRequest);
        return "redirect:/session-login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member member = memberService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(member == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "login";
        }

        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
        // 세션에 userId를 넣어줌
        session.setAttribute("memberId", member.getMemberId());
        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

        return "redirect:/session-login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/session-login";
    }

    @GetMapping("/info")
    public String memberInfo(@SessionAttribute(name = "memberId", required = false) Long memberId, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member loginMember = memberService.getLoginMemberById(memberId);

        if(loginMember == null) {
            return "redirect:/session-login/login";
        }

        model.addAttribute("member", loginMember);
        return "info";
    }

}