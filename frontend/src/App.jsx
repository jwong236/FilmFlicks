/**
 * The App component serves as the main layout for the application.
 * It sets up the routing for the application using the Routes and Route components from 'react-router-dom'.
 */
import { ThemeProvider, CssBaseline } from '@mui/material';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import theme from './theme/theme.js'

import SingleStarPage from './pages/SingleStarPage.jsx';

import HomePage from './pages/HomePage.jsx';
import MovieListPage from './pages/MovieListPage.jsx';


import ShoppingCart from './pages/ShoppingCartPage.jsx';
import PaymentInfo from './pages/PaymentPage.jsx';
import ConfirmationPage from './pages/ConfirmationPage.jsx';

import DashboardPage from './pages/DashboardPage.jsx';
import TopMoviesPage from "./pages/TopMoviesPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import SingleMoviePage from "./pages/SingleMoviePage.jsx";

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
                <Routes>
                    <Route path="/" element={<HomePage />} />

                    <Route path="/topmovies" element={<TopMoviesPage />} />
                    <Route path="/singlemovie" element={<SingleMoviePage />} />
                    <Route path="/singlestar" element={<SingleStarPage />} />

                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/homepage" element={<HomePage />} />
                    <Route path="/movielist" element={<MovieListPage />} />

                    <Route path="/shoppingcart" element={<ShoppingCart />} />
                    <Route path="/paymentinfo" element={<PaymentInfo />} />
                    <Route path="/confirmation" element={<ConfirmationPage />} />

                    <Route path="/_dashboard" element = {<DashboardPage />} />
                </Routes>
        </ThemeProvider>
    );
}

export default App;