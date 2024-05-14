import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import AppLayout from './AppLayout';
import MainPage from '../pages/MainPage';
import SignupPage1 from '../pages/SignupPage1';
import SigninPage1 from '../pages/SigninPage1';
import React from 'react';

const AppRouter = () => {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <AppLayout />,
      children: [
        {
          path: '/',
          element: <MainPage />,
        },
        {
          path: '/signup',
          element: <SignupPage1 />,
        },
        {
          path: '/signin',
          element: <SigninPage1 />,
        },
      ],
    },
  ]);
  return <RouterProvider router={router} />;
};

export default AppRouter;
