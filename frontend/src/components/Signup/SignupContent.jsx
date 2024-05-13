    import React, { useState } from 'react';
    import './SignupContent.css';

    const SignupContent = () => {
      const [formData, setFormData] = useState({
        name: '',
        ID: '',
        password: '',
        passwordConfirm: ''
      });
    
      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value,
        });
      };
    
      const handleSubmit = (e) => {
        e.preventDefault();
        console.log('회원가입 정보:', formData);
        // 여기에 회원가입 처리 로직 추가
      };
    
      const handleLoginClick = () => {
        window.location.href = 'http://localhost:5173/signin';
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
                <div className="user-info-ID">
                  <div>* ID</div>
                  <input
                    type="ID"
                    name="ID"
                    value={formData.ID}
                    onChange={handleChange}
                  />
                </div>
                <div className="user-info-pw">
                  <div>* 비밀번호</div>
                  <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                  />
                </div>
                <div className="user-info-pw-check">
                  <div>* 비밀번호 확인</div>
                  <input
                    type="password"
                    name="passwordConfirm"
                    value={formData.passwordConfirm}
                    onChange={handleChange}
                  />
                </div>
              </div>
              <div className="agree-check">
                <input type="checkbox" /> 이용약관 개인정보 수집 및 이용, 마케팅 활용 선택에 모두 동의합니다.
              </div>
              <div className="btn">
                <button onClick={handleSubmit}>가입하기</button>
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