/**
 * The App component serves as the main layout for the application.
 * It sets up the routing for the application using the Routes and Route components from 'react-router-dom'.
 */
import { createTheme, ThemeProvider, CssBaseline, Box } from '@mui/material';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import theme from './theme'
import MovieList from './pages/MovieList';
import SingleMovie from './pages/SingleMovie';
import SingleStar from './pages/SingleStar';

import Login from './pages/SingleStar';
import Homepage from './pages/SingleStar';

import ShoppingCart from './pages/SingleStar';
import PaymentInfo from './pages/SingleStar';
import Confirmation from './pages/SingleStar';

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            {/*<Box sx={{ minHeight: '100vh', display: 'flex',  justifyContent: 'center', alignItems: 'center', }}>*/}
                <Routes>
                    <Route path="/" element={<MovieList />} />

                    <Route path="/movielist" element={<MovieList />} />
                    <Route path="/singlemovie" element={<SingleMovie />} />
                    <Route path="/singlestar" element={<SingleStar />} />

                    <Route path="/login" element={<Login />} />
                    <Route path="/homepage" element={<Homepage />} />

                    <Route path="/shoppingcart" element={<ShoppingCart />} />
                    <Route path="/paymentinfo" element={<PaymentInfo />} />
                    <Route path="/confirmation" element={<Confirmation />} />
                </Routes>
            {/*</Box>*/}
        </ThemeProvider>
    );
}

export default App;