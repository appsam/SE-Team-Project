import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar/Navbar';
import ScrollToTop from '../components/ScrollToTop/ScrollToTop';
import Footer from '../components/Footer/Footer';
import React from 'react';

const AppLayout = () => {
  return (
    <ScrollToTop>
      <Navbar />
      <Outlet />
      <Footer />
    </ScrollToTop>
  );
};

export default AppLayout;
