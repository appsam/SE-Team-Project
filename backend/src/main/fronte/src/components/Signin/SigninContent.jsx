    import React, { useState } from 'react';
    import './SigninContent.css';
    import axios from 'axios';

    const SigninContent = () => {

      const [formData, setFormData] = useState({
        loginId: '',
        password: ''
      });
    
      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value,
        });
      };
    
      const handleSignupClick = () => {
        window.location.href = 'http://localhost:5173/signup';
      };

      const pullData = (userId) => {
        axios
          .get("http://localhost:8080/api/recommend", {
                params: { userId: userId },
              })
          .then((res) => { // 수정된 부분
            console.log(res.data);
          })
          .catch((error) => {
            console.error("데이터 가져오기 에러:", error);
          });
      };


      const onClickLogin = () => {
          console.log("click login");
          console.log("loginId : ", formData.loginId);
          console.log("password : ", formData.password);
          axios
            .post("http://localhost:8080/jwt-login/login", {
              loginId: formData.loginId,
              password: formData.password
            })
            .then((res) => {
                               console.log(res.data);
                               if (res.data.success) {
                                 console.log("로그인 성공");
                                 alert(res.data.message);
                                 localStorage.setItem("Authorization", "Bearer " + res.data.token);
                                 window.location.href = 'http://localhost:5173/';
                               } else {
                                 console.log("로그인 실패:", res.data.message);
                                 alert(res.data.message || "로그인 중 오류가 발생했습니다.");
                               }
                             })
                             .catch((error) => {
                               console.error("회원가입 에러:", error);
                               alert("회원가입 중 에러가 발생했습니다.");
                             });
                         };

      const getPoster = () => {
          var xhr = new XMLHttpRequest();
          var url = 'http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2';/*URL*/
          var queryParams = '?' + encodeURIComponent('ServiceKey=G3I1782G6761W2A7UV99'); /*Service Key*/
          queryParams += '&' + encodeURIComponent('title') + '=' + encodeURIComponent('Toy Story');/*상영년도*/
          queryParams += '&' + encodeURIComponent('genre') + '=' + encodeURIComponent('Fantasy');/*상영월*/
          xhr.open('GET', url + queryParams);
          xhr.onreadystatechange = function () {
           if (this.readyState == 4) {
            alert('Status: '+this.status+'Headers: '+JSON.stringify(this.getAllResponseHeaders())+'Body: '+this.responseText);
            }
          };
            xhr.send('');
        }
    
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
                <div className="user-info-loginId">
                  <div className="input-wrapper">
                    <input
                      type="text"
                      name="loginId"
                      placeholder="아이디"
                      value={formData.loginId}
                      onChange={handleChange}
                    />
                    <div className="blur-overlay"></div>
                  </div>
                </div>
                <div className="user-info-password">
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
                <button onClick={onClickLogin}>로그인</button>
              </div>
              <div className="btn">
                <button onClick={getPoster}>야옹</button>
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