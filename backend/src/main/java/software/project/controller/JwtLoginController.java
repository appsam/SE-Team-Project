package software.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import software.project.config.JwtUtil;
import software.project.domain.Member;
import software.project.dto.JoinRequest;
import software.project.dto.LoginRequest;
import software.project.service.MemberService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
@CrossOrigin(origins = "http://localhost:5173")
public class JwtLoginController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;


    @GetMapping("/join")
    public String joinPage(Model model) {

        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");

        // 회원가입을 위해서 model 통해서 joinRequest 전달
        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String,Object>> join(@Valid @RequestBody JoinRequest joinRequest,
                                            BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();

        // ID 중복 여부 확인
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            response.put("success", false);
            response.put("message", "이미 존재하는 아이디입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 비밀번호 = 비밀번호 체크 여부 확인
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            response.put("success", false);
            response.put("message", "비밀번호를 다시 확인하세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 에러가 존재하지 않을 시 joinRequest 통해서 회원가입 완료
        memberService.join(joinRequest);

        // 회원가입 시 성공 메시지
        response.put("success", true);
        response.put("message", "회원가입 성공!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");

        // 회원가입을 위해서 model 통해서 joinRequest 전달
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){

        Member member = memberService.login(loginRequest);


        if(member==null){
            return "ID 또는 비밀번호가 일치하지 않습니다!";
        }

        String token = jwtUtil.createJwt(member.getLoginId(), String.valueOf(member.getRole()), 1000 * 60 * 60L);
        return token;
    }

    @GetMapping("/info")
    public String memberInfo(Authentication auth, Model model) {

        Member loginMember = memberService.getLoginMemberByLoginId(auth.getName());

        return "ID : " + loginMember.getLoginId() + "\n이름 : " + loginMember.getName() + "\nrole : " + loginMember.getRole();
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {

        return "인가 성공!";
    }
}
