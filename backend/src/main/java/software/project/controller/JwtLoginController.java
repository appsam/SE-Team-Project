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



    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginRequest loginRequest){

        Member member = memberService.login(loginRequest);
        Map<String, Object> response = new HashMap<>();


        if(member==null){
            response.put("success", false);
            response.put("message", "아이디 및 비밀번호를 다시 확인하세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        else{
            String token = jwtUtil.createJwt(member.getLoginId(), String.valueOf(member.getRole()), 1000 * 60 * 60L);
            response.put("success", true);
            response.put("token", token);
            response.put("message", "로그인 성공!");
            return ResponseEntity.ok(response);}
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
