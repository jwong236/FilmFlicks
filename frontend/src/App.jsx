/**
 * The App component serves as the main layout for the application.
 * It sets up the routing for the application using the Routes and Route components from 'react-router-dom'.
 */
import { ThemeProvider, CssBaseline } from '@mui/material';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import theme from './theme/theme.js'

import SingleStar from './pages/SingleStar';

import Homepage from './pages/Homepage';
import MovieListPage from './pages/MovieListPage.jsx';


import ShoppingCart from './pages/ShoppingCartPage.jsx';
import PaymentInfo from './pages/PaymentPage.jsx';
import Confirmation from './pages/Confirmation';

import Dashboard from './pages/Dashboard.jsx';
import HomePage from "./pages/Homepage";
import TopMoviesPage from "./pages/TopMoviesPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import SingleMoviePage from "./pages/SingleMoviePage.jsx";

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
                <Routes>
                    <Route path="/" element={<Homepage />} />

                    <Route path="/topmovies" element={<TopMoviesPage />} />
                    <Route path="/singlemovie" element={<SingleMoviePage />} />
                    <Route path="/singlestar" element={<SingleStar />} />

                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/homepage" element={<HomePage />} />
                    <Route path="/movielist" element={<MovieListPage />} />

                    <Route path="/shoppingcart" element={<ShoppingCart />} />
                    <Route path="/paymentinfo" element={<PaymentInfo />} />
                    <Route path="/confirmation" element={<Confirmation />} />

                    <Route path="/_dashboard" element = {<Dashboard />} />
                </Routes>
        </ThemeProvider>
    );
}

export default App;