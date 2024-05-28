import React, { useState, useEffect } from 'react';
import './Maincontent.css';
import axios from 'axios';
import Modal from 'react-modal';

const Maincontent = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(0);

  const [showModal, setShowModal] = useState(false);
  const [movieDetails, setMovieDetails] = useState(null);
  const [top4Posters, setTop4Posters] = useState([]);

  useEffect(() => {
      // 별점 TOP4 포스터 가져오기
      axios
          .get('http://localhost:8080/api/rating4')
          .then((res) => {
              setTop4Posters(res.data);
          })
          .catch((error) => {
              console.error('별점 TOP4 포스터 가져오기 에러:', error);
          });
  }, []);

  const handleMovieClick = (movieId) => {
      console.log('Movie clicked:', movieId);
      axios
        .get(`http://localhost:8080/api/${movieId}`)
        .then((res) => {
          setMovieDetails(res.data);
          setShowModal(true);
        })
        .catch((error) => {
          console.error('영화 상세 정보 가져오기 에러:', error);
        });
    };

    const handleCloseModal = () => {
      setShowModal(false);
    };

  // 내부 컴포넌트 정의
  const MovieAutocomplete = () => {
    const [keyword, setKeyword] = useState('');
    const [suggestions, setSuggestions] = useState([]);

    useEffect(() => {
      if (keyword.length > 2) {
        axios.get(`http://localhost:8080/api/autocomplete?keyword=${keyword}`)
          .then(response => {
            setSuggestions(response.data);
            console.log(response.data);
          })
          .catch(error => {
            console.error('Error fetching autocomplete suggestions:', error);
          });
      } else {
        setSuggestions([]);
      }
    }, [keyword]);

    const handleChange = (e) => {
      setKeyword(e.target.value);
    };

    const handleSuggestionClick = (movieId) => {
          handleMovieClick(movieId);
        };

    return (
      <div className="search">
        <input
          type="text"
          placeholder="검색어 입력"
          value={keyword}
          onChange={handleChange}
        />
        <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" alt="검색" />
        <ul>
          {suggestions.map((movie) => (
            <li key={movie.movieId} onClick={() => handleSuggestionClick(movie.movieId)}>
                  {movie.title}
            </li>
          ))}
        </ul>

      </div>
    );
  };

  return (
    <div>
      <MovieAutocomplete />
      <div className="moviecontainer">
        <div className="sectionContainer">
            <h2>별점 TOP4</h2>
            <div className="recommend">
              {/* 이미지 경로 수정 */}
              {top4Posters.map((poster, index) => (
                  <div key={index} className="poster">
                    <img src={poster} alt={`포스터 ${index + 1}`} />
                  </div>
               ))}
            </div>
        </div>
        <div className="sectionContainer">
          <h2>추천 TOP4</h2>
          <div className="ranking">
              <div className="poster"><img src="/images/남자가사랑할때.jpg" alt="포스터 5" /></div>
              <div className="poster"><img src="/images/주만지.jpg" alt="포스터 6" /></div>
              <div className="poster"><img src="/images/토이스토리.jpg" alt="포스터 7" /></div>
              <div className="poster"><img src="/images/히트.jpg" alt="포스터 8" /></div>
          </div>
        </div>
      </div>
      <div className="more-button">
        <a href="http://localhost:5173/movies">더보기</a>
      </div>

      {movieDetails && (
                    <Modal
                      isOpen={showModal}
                      onRequestClose={handleCloseModal}
                      shouldCloseOnOverlayClick={false}
                      contentLabel="Movie Detail Modal"
                      style={{
                        overlay: {
                          backgroundColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        content: {
                          backgroundColor: '#fffff',
                          width: '50%',
                          height: '70%',
                          margin: 'auto',
                          overflow: 'hidden',
                          zIndex: '1000',
                        },
                      }}
              >
                <h2>{movieDetails.title}</h2>
                {movieDetails.poster && <img src={movieDetails.poster} alt={`${movieDetails.title} poster`} style={{ width: '300px', height: '400px' }} />}
                <p>Genres: {movieDetails.genres}</p>
                <p>Average Rating: {movieDetails.averageRating}</p>
                <p>Tags: {movieDetails.tags.join(', ')}</p>
                <button onClick={handleCloseModal}></button>
              </Modal>
            )}
    </div>
  );
};

export default Maincontent;