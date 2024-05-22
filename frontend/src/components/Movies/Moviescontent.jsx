import React, { useState } from 'react';

const Moviescontent = () => {
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
    // 로그인 처리 로직 추가
  };

  const handleSignupClick = () => {
    window.location.href = 'http://localhost:5174/movies';
  };

  return (
    <div>
      {/* 로그인 폼 */}
      <div className="container">
        <div className="member-container">
          {/* 내용 생략 */}
        </div>
      </div>
    </div>
  );
};

export default Moviescontent;
