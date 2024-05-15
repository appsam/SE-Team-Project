import { Link, useLocation } from 'react-router-dom';
import { IoMdMenu } from 'react-icons/io';
import { useState } from 'react';
import classNames from 'classnames';
import './Navbar.css';

const Navbar = () => {
  const location = useLocation();
  const [isOpenMenu, setIsOpenMenu] = useState(false);

  const selected = (name) => {
    return classNames('hover:font-bold transition', {
      'font-bold underline underline-offset-8': location.pathname === name,
    });
  };

  const toggleMenu = () => {  
    setIsOpenMenu(!isOpenMenu);
  };

  return (
    <nav className="fixed top-0 left-0 w-full line-height: 1.15; -webkit-text-size-adjust: 100%; font-family: 'Pretendard', 'Apple SD Gothic Neo', 'Nanum Gothic', 'Malgun Gothic', sans-serif; color: #fff; text-align: center; -webkit-font-smoothing: antialiased; box-sizing: border-box; margin-block: 0 0; -webkit-margin-after: 0; -webkit-margin-before: 0; -webkit-margin-start: 0; -webkit-margin-end: 0; -webkit-padding-start: 0; list-style: none; padding: 0; margin: 0; display: flex; overflow: hidden;">
      <div className="bg-white border-b">
        <div className="flex items-center justify-between h-full px-6 py-2">
          <div className="flex items-center"> {/* 변경된 부분 */}
            <Link className="font-bold flex items-center mr-4 movie-recommendation" to="/">
              <span className="mx-auto">CinemaBot</span>
            </Link>
          </div>
          <div className="flex justify-center flex-grow space-x-8"> {/* 변경된 부분 */}
            <Link className={selected('/')} to="/">
              HOME
            </Link>
            <Link className={selected('/updates')} to="/updates">
              Updates
            </Link>
            <Link className={selected('/services')} to="/services">
              Services
            </Link>
            <Link className={selected('/features')} to="/features">
              Features
            </Link>
            <Link className={selected('/about')} to="/about">
              About Us
            </Link>
          </div>

          <IoMdMenu
            className="w-6 h-6 sm:hidden cursor-pointer"
            onClick={toggleMenu}
          />
          <div className={`sm:flex gap-6 text-sm items-center ${isOpenMenu ? '' : 'hidden'}`}>
            <div className="ml-auto flex gap-6">
              <Link className={selected('/signup')} to="/signup">
                회원가입
              </Link>
              <Link className={selected('/signin')} to="/signin">
                로그인
              </Link>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
