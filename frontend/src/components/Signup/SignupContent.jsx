import React, { useState } from 'react';
import './SignupContent.css';
import axios from 'axios';

const SignupContent = () => {
  const [formData, setFormData] = useState({
    loginId: '',
    password: '',
    passwordCheck: '',
    name: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleLoginClick = () => {
    window.location.href = 'http://localhost:5173/signin';
  };



  const onClickJoin = () => {
    console.log("click join");
    console.log("id : ", formData.loginId);
    console.log("password : ", formData.password);
    console.log("passwordCheck: ",formData.passwordCheck);
    console.log("name: ",formData.name);
    axios
      .post("http://localhost:8080/jwt-login/join", {
        loginId: formData.loginId,
        password: formData.password,
        passwordCheck: formData.passwordCheck,
        name: formData.name
      })
       .then((res) => {
               console.log(res.data);
               if (res.data.success) {
                 console.log("회원가입 성공");
                 alert("회원가입이 성공적으로 완료되었습니다.");
                 window.location.href = 'http://localhost:5173/signin';
               } else {
                 console.log("회원가입 실패:", res.data.message);
                 alert(res.data.message || "회원가입 중 오류가 발생했습니다.");
               }
             })
             .catch((error) => {
               console.error("회원가입 에러:", error);
               alert("회원가입 중 에러가 발생했습니다.");
             });
         };



  return (
    <div>
      {/* 이메일 회원가입 폼 */}
      <div className="container">
        <div className="member-container">
          <div className="header">
            <div className='top'>CinemaBot</div>
            <div>회원가입</div>
          </div>
          <div className="user-info">
            <div className="user-info-name">
              <div>* 이름</div>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
              />
            </div>
            <div className="user-info-loginId">
              <div>* ID</div>
              <input
                type="text"
                name="loginId"
                value={formData.loginId}
                onChange={handleChange}
              />
            </div>
            <div className="user-info-password">
              <div>* 비밀번호</div>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
              />
            </div>
            <div className="user-info-passwordCheck">
              <div>* 비밀번호 확인</div>
              <input
                type="password"
                name="passwordCheck"
                value={formData.passwordCheck}
                onChange={handleChange}
              />
            </div>
          </div>
          <div className="agree-check">
            <input type="checkbox" /> 이용약관 개인정보 수집 및 이용, 마케팅 활용 선택에 모두 동의합니다.
          </div>
          <div className="btn">
            <button onClick={onClickJoin}>가입하기</button>
          </div>
          <div className="already-member">
            이미 가입하셨나요? <button className="login-button" onClick={handleLoginClick}>로그인</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignupContent;