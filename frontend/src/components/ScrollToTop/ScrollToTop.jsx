import { useLayoutEffect } from 'react';
import { useLocation } from 'react-router-dom';
import PropTypes from 'prop-types';

const ScrollToTop = ({ children }) => {
  const location = useLocation();
  useLayoutEffect(() => {
    document.documentElement.scrollTo(0, 0);
  }, [location.pathname]);
  
  return children;
};

ScrollToTop.propTypes = {
  children: PropTypes.node,
};
export default ScrollToTop;
