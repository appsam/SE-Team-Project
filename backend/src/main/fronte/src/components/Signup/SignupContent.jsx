 import React, { useState } from 'react';
    import './SignupContent.css';
    import axios from 'axios';
    import Modal from 'react-modal';

    const SignupContent = () => {
      const [showModal, setShowModal] = useState(false);
      const genres = [
        'Action', 'Adventure', 'Animation', 'Children', 'Comedy', 'Crime',
        'Documentary', 'Drama', 'Fantasy', 'Film-Noir', 'Horror', 'IMAX',
        'Musical', 'Mystery', 'Romance', 'Sci-Fi', 'Thriller', 'War', 'Western'
      ];

      const [formData, setFormData] = useState({
        loginId: '',
        password: '',
        passwordCheck: '',
        name: '',
        selectedGenres: [],
      });

      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value,
        });
      };

      const handleGenreChange = (genre) => {
          setFormData((prev) => {
            const selectedGenres = prev.selectedGenres.includes(genre)
              ? prev.selectedGenres.filter((g) => g !== genre)
              : [...prev.selectedGenres, genre];
            return { ...prev, selectedGenres };
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
        console.log("selectedGenres: ",formData.selectedGenres);
        axios
          .post("http://localhost:8080/jwt-login/join", {
            loginId: formData.loginId,
            password: formData.password,
            passwordCheck: formData.passwordCheck,
            name: formData.name,
            selectedGenres: formData.selectedGenres
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
          <div className="genrebtn">
            <button className="genre-select-button" onClick={() => setShowModal(true)}>선호 장르 선택</button>
          </div>
          <div className="btn">
            <button className="join-button" onClick={onClickJoin}>가입하기</button>
          </div>
          <div className="already-member">
            이미 가입하셨나요? <button className="login-button" onClick={handleLoginClick}>로그인</button>
          </div>
        </div>
      </div>

      {/* 장르 선택 모달 */}
      <Modal
        isOpen={showModal}
        onRequestClose={() => setShowModal(false)}
        contentLabel="Select Favorite Genres"
      >
        <div className="modal-content">
          <h3>Select Your Favorite Genres</h3>
          <div className="genre-list">
            {genres.map((genre) => (
              <label key={genre} className="genre-item">
                <input
                  type="checkbox"
                  value={genre}
                  checked={formData.selectedGenres.includes(genre)}
                  onChange={() => handleGenreChange(genre)}
                />
                {genre}
              </label>
            ))}
          </div>
          <button onClick={() => setShowModal(false)}>선택 완료</button>
        </div>
      </Modal>
    </div>
  );
};

export default SignupContent;