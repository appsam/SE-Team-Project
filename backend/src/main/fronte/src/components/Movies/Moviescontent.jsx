import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import StarRating from './StarRating';

import './Moviescontent.css';

Modal.setAppElement('#root');
const Moviescontent = () => {
  const [movies, setMovies] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [currentBlock, setCurrentBlock] = useState(1);
  const [pageNumbers, setPageNumbers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [movieDetails, setMovieDetails] = useState(null);

  const moviesPerPage = 20;
  const pagesPerBlock = 10;

  // 영화 데이터 가져오는 함수
  const pullData = () => {
    axios
      .get('http://localhost:8080/api/movies')
      .then((res) => {
        console.log(res.data);
        setMovies(res.data);
        const totalPages = Math.ceil(res.data.length / moviesPerPage);
        setPageNumbers(Array.from({ length: totalPages }, (_, i) => i + 1));
      })
      .catch((error) => {
        console.error('데이터 가져오기 에러:', error);
      });
  };

  // 페이지가 처음 로드될 때 데이터 가져오기
  useEffect(() => {
    pullData();
  }, []);

  // 현재 페이지의 영화 목록 계산
  const indexOfLastMovie = currentPage * moviesPerPage;
  const indexOfFirstMovie = indexOfLastMovie - moviesPerPage;
  const currentMovies = movies.slice(indexOfFirstMovie, indexOfLastMovie);

  // 페이지 번호 클릭 시 처리 함수
  const handleClick = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // 다음 블록으로 이동 처리 함수
  const handleNextBlock = () => {
    if (currentBlock < Math.ceil(pageNumbers.length / pagesPerBlock)) {
      setCurrentBlock(currentBlock + 1);
    }
  };

  // 이전 블록으로 이동 처리 함수
  const handlePrevBlock = () => {
    if (currentBlock > 1) {
      setCurrentBlock(currentBlock - 1);
    }
  };

  // 현재 블록의 페이지 번호 목록 계산
  const currentBlockPageNumbers = pageNumbers.slice(
    (currentBlock - 1) * pagesPerBlock,
    currentBlock * pagesPerBlock
  );

  // 페이지 번호 렌더링
  const renderPageNumbers = currentBlockPageNumbers.map((number) => {
    return (
      <span
        key={number}
        onClick={() => handleClick(number)}
        style={{ cursor: 'pointer', margin: '0 5px', color: currentPage === number ? 'blue' : 'black' }}
      >
        {number}
      </span>
    );
  });

  // 영화 제목 클릭 시 처리 함수
 const handleMovieClick = (movie) => {
     console.log('Movie clicked:', movie);
     axios
       .get(`http://localhost:8080/api/${movie.movieId}`,{
        headers: {
                  'Content-Type': 'application/json',
                }
        })
       .then((res) => {
         setMovieDetails(res.data);
         setShowModal(true);
       })
       .catch((error) => {
         console.error('영화 상세 정보 가져오기 에러:', error);
       });
   };

  // 모달 닫기 함수
  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <div className="container">
      <div>
        <table>
          <thead>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>장르</th>
            </tr>
          </thead>
          <tbody>
            {currentMovies.map((movie) => (
              <tr key={movie.movieId}>
                <td>{movie.movieId}</td>
                <td onClick={() => handleMovieClick(movie)} style={{ cursor: 'pointer' }}>
                  {movie.title}
                </td>
                <td>{movie.genres}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <div style={{ marginTop: '20px' }}>
          {currentBlock > 1 && (
            <span onClick={handlePrevBlock} style={{ cursor: 'pointer', margin: '0 5px' }}>
              &laquo; 이전
            </span>
          )}
          {renderPageNumbers}
          {currentBlock < Math.ceil(pageNumbers.length / pagesPerBlock) && (
            <span onClick={handleNextBlock} style={{ cursor: 'pointer', margin: '0 5px' }}>
              다음 &raquo;
            </span>
          )}
        </div>
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
                  },
                }}
        >
          <h2>{movieDetails.title}</h2>
          {movieDetails.poster && <img src={movieDetails.poster} alt={`${movieDetails.title} poster`} style={{ width: '300px', height: '400px' }} />}
          <p>Genres: {movieDetails.genres}</p>
          <div className="rating">
                <StarRating rating={movieDetails.averageRating} />
          </div>
          <p>Tags: {movieDetails.tags.join(', ')}</p>
          <button onClick={handleCloseModal}></button>
        </Modal>
      )}
    </div>
  );
};

export default Moviescontent;