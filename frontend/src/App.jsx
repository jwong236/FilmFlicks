/**
 * The App component serves as the main layout for the application.
 * It sets up the routing for the application using the Routes and Route components from 'react-router-dom'.
 */
import { createTheme, ThemeProvider, CssBaseline, Box } from '@mui/material';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import theme from './theme/theme.js'

import TopMovies from './pages/TopMovies';
import SingleMovie from './pages/SingleMovie';
import SingleStar from './pages/SingleStar';

import Login from './pages/LoginPage.jsx';
import Homepage from './pages/Homepage';
import MovieList from './pages/MovieList';


import ShoppingCart from './pages/ShoppingCart';
import PaymentInfo from './pages/PaymentInfo';
import Confirmation from './pages/Confirmation';

import Dashboard from './pages/Dashboard.jsx';

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
                <Routes>
                    <Route path="/" element={<Homepage />} />

                    <Route path="/topmovies" element={<TopMovies />} />
                    <Route path="/singlemovie" element={<SingleMovie />} />
                    <Route path="/singlestar" element={<SingleStar />} />

                    <Route path="/login" element={<Login />} />
                    <Route path="/homepage" element={<Homepage />} />
                    <Route path="/movielist" element={<MovieList />} />

                    <Route path="/shoppingcart" element={<ShoppingCart />} />
                    <Route path="/paymentinfo" element={<PaymentInfo />} />
                    <Route path="/confirmation" element={<Confirmation />} />

                    <Route path="/_dashboard" element = {<Dashboard />} />
                </Routes>
        </ThemeProvider>
    );
}

export default App;