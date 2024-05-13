    import React, { useState } from 'react';
    import './SigninContent.css';

    const SigninContent = () => {
      const [formData, setFormData] = useState({
        ID: '',
        password: ''
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
        console.log('로그인 정보:', formData);
        // 여기에 로그인 처리 로직 추가
      };
    
      const handleSignupClick = () => {
        window.location.href = 'http://localhost:5174/signup';
      };
    
      return (
        <div>
          {/* 이메일 로그인 폼 */}
          <div className="container">
            <div className="member-container">
              <div className="header">
                <div className='top'>CinemaBot</div>
                <div>로그인</div>
              </div>
              <div className="user-info">
                <div className="user-info-ID">
                  <div className="input-wrapper">
                    <input
                      type="ID"
                      name="ID"
                      placeholder="아이디"
                      value={formData.ID}
                      onChange={handleChange}
                    />
                    <div className="blur-overlay"></div>
                  </div>
                </div>
                <div className="user-info-pw">
                  <div className="input-wrapper">
                    <input
                      type="password"
                      name="password"
                      placeholder="비밀번호"
                      value={formData.password}
                      onChange={handleChange}
                    />
                    <div className="blur-overlay"></div>
                  </div>
                </div>
              </div>
              <div className="btn">
                <button onClick={handleSubmit}>로그인</button>
              </div>
              <div className="already-member">
                아직 회원이 아니신가요? <button className="signup-button" onClick={handleSignupClick}>회원가입</button>
              </div>
            </div>
          </div>
        </div>
      );
    };
    
    export default SigninContent;