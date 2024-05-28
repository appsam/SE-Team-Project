import { Link, useLocation } from 'react-router-dom';
import { IoMdMenu } from 'react-icons/io';
import { useState, useEffect } from 'react';
import classNames from 'classnames';
import './Navbar.css';

const Navbar = () => {
  const location = useLocation();
  const [isOpenMenu, setIsOpenMenu] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
      // 로컬 스토리지에서 토큰을 확인하여 인증 상태를 설정
      const token = localStorage.getItem('Authorization');
      if (token) {
        // 토큰이 유효한지 서버에 검증 요청을 보내는 방법도 있음
        setIsAuthenticated(true);
      } else {
        setIsAuthenticated(false);
      }
    }, []);

  const selected = (name) => {
    return classNames('hover:font-bold transition', {
      'font-bold underline underline-offset-8': location.pathname === name,
    });
  };

  const toggleMenu = () => {
    setIsOpenMenu(!isOpenMenu);
  };

  const handleLogout = () => {
      // 로컬 스토리지에서 토큰 제거
      localStorage.removeItem('Authorization');
      setIsAuthenticated(false);
    };

  return (
    <nav className="fixed top-0 left-0 w-full z-50">
      <div className="bg-white border-b">
        <div className="flex items-center justify-between h-full px-6 py-2">
          <div className="flex items-center">
            <Link className="font-bold flex items-center mr-4 movies-recommendation" to="/">
              <span className="mx-auto">CinemaBot</span>
            </Link>
          </div>
          <div className="flex justify-center flex-grow space-x-8">
            <Link className={selected('/')} to="/">
              HOME
            </Link>
            <Link className={selected('/movies')} to="/movies">
              Movies
            </Link>
            <Link className={selected('/chatbot')} to="/chatbot">
              ChatBot
            </Link>
          </div>

          <IoMdMenu
            className="w-6 h-6 sm:hidden cursor-pointer"
            onClick={toggleMenu}
          />
          <div className={`sm:flex gap-6 text-sm items-center ${isOpenMenu ? '' : 'hidden'}`}>
            {isAuthenticated ? (
                            <button onClick={handleLogout} className="hover:font-bold transition">
                              로그아웃
                            </button>
                          ) : (
                            <>
                              <Link className={selected('/signup')} to="/signup">
                                회원가입
                              </Link>
                              <Link className={selected('/signin')} to="/signin">
                                로그인
                              </Link>
                            </>
                          )}
            </div>
          </div>
        </div>

    </nav>
  );
};

export default Navbar;