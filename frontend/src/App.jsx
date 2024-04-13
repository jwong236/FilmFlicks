/**
 * The App component serves as the main layout for the application.
 * It sets up the routing for the application using the Routes and Route components from 'react-router-dom'.
 */
import { createTheme, ThemeProvider, CssBaseline, Box } from '@mui/material';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import MovieList from './pages/MovieList';
import SingleMovie from './pages/SingleMovie';
import SingleStar from './pages/SingleStar';

const theme = createTheme({
    components: {
        MuiCssBaseline: {
            styleOverrides: {
                body: {
                    backgroundColor: '#F6F6F6',
                    '#root': {
                        maxWidth: '1280px',
                        margin: '0 auto',
                        padding: '2rem',
                        height: '100%',
                    },
                },
            },
        },
    },
});

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Box sx={{ minHeight: '100vh' }}>
                <Routes>
                    <Route path="/" element={<MovieList />} />
                    <Route path="/movielist" element={<MovieList />} />
                    <Route path="/singlemovie" element={<SingleMovie />} />
                    <Route path="/singlestar" element={<SingleStar />} />
                </Routes>
            </Box>
        </ThemeProvider>
    );
}

export default App;