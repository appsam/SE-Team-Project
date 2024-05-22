  import React, { useState, useEffect } from 'react';
  import './Maincontent.css';

  const ReviewSlide = ({ review }) => (
    <div className="review-box">
      <div className="single-review">
        <div>사용자: {review.name}</div>
        <div>별점: {review.rating}</div>
        <div>{review.mvname}</div>
        <div>리뷰: {review.text}</div>
      </div>
    </div>
  );

  const Maincontent = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [reviews, setReviews] = useState([]);
    const [currentPage, setCurrentPage] = useState(0); 
    const reviewsPerPage = 4; 

    useEffect(() => {
      // 리뷰를 받아오는 API 호출
      fetchReviews();
    }, []);

    // 검색어 입력 시 핸들러
    const handleInputChange = (e) => {
      setSearchTerm(e.target.value);
    };

    // 리뷰를 받아오는 함수
    const fetchReviews = () => {
      // 실제로는 서버로부터 리뷰를 받아오는 로직
      // 여기서는 임시적으로 더미 데이터를 사용
      const dummyReviews = [
        { id: 1, name: '사용자1', rating: 4, mvname: '영화 제목1', text: '이 영화 정말 재미있어요!' },
        { id: 2, name: '사용자2', rating: 5, mvname: '영화 제목2', text: '두 번째 리뷰입니다.' },
        { id: 3, name: '사용자3', rating: 5, mvname: '영화 제목3', text: '세 번째 리뷰입니다.' },
        { id: 4, name: '사용자4', rating: 4, mvname: '영화 제목4', text: '네 번째 리뷰입니다.' },
        { id: 5, name: '사용자5', rating: 3, mvname: '영화 제목5', text: '다섯 번째 리뷰입니다.' },
        { id: 6, name: '사용자6', rating: 5, mvname: '영화 제목6', text: '여섯 번째 리뷰입니다.' },
        { id: 7, name: '사용자7', rating: 4, mvname: '영화 제목7', text: '일곱 번째 리뷰입니다.' },
        { id: 8, name: '사용자8', rating: 5, mvname: '영화 제목8', text: '여덟 번째 리뷰입니다.' },
      ];
      setReviews(dummyReviews);
    };

    const prevPage = () => {
      setCurrentPage((prevPage) => (prevPage === 0 ? Math.ceil(reviews.length / reviewsPerPage) - 1 : prevPage - 1));
    };

    const nextPage = () => {
      setCurrentPage((prevPage) => (prevPage === Math.ceil(reviews.length / reviewsPerPage) - 1 ? 0 : prevPage + 1));
    };

    const currentReviews = reviews.slice(currentPage * reviewsPerPage, (currentPage + 1) * reviewsPerPage);

    return (
      <div>
        <div className="search">
          <input
            type="text"
            placeholder="  검색어 입력"
            value={searchTerm}
            onChange={handleInputChange}
          />
          <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" alt="검색" />
        </div>
        <div className="review-container">
          <div className="arrow left-arrow" onClick={prevPage}>
            &lt;
          </div>
          <div className="review-slide">
            {currentReviews.map((review) => (
              <ReviewSlide key={review.id} review={review} />
            ))}
          </div>
          <div className="arrow right-arrow" onClick={nextPage}>
            &gt;
          </div>
        </div>
        <div className="moviecontainer">
          <div className="recommend">
            {/* 이미지 경로 수정 */}
            <div className="poster"><img src="./images/건축학개론.jpg" alt="포스터 1" /></div>
            <div className="poster"><img src="/images/기생충.jpg" alt="포스터 2" /></div>
            <div className="poster"><img src="/images/타짜.jpg" alt="포스터 3" /></div>
            <div className="poster"><img src="/images/신세계.jpg" alt="포스터 4" /></div>
          </div>
          
          <div className="ranking">
            <div className="poster"><img src="/images/남자가사랑할때.jpg" alt="포스터 5" /></div>
            <div className="poster"><img src="/images/주만지.jpg" alt="포스터 6" /></div>
            <div className="poster"><img src="/images/토이스토리.jpg" alt="포스터 7" /></div>
            <div className="poster"><img src="/images/히트.jpg" alt="포스터 8" /></div>
          </div>
        </div>
        <div className="more-button">
          <a href="http://localhost:5173/movies">더보기</a>
        </div>
      </div>
    );
  };

  export default Maincontent;
