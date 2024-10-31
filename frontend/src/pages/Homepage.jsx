import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, useTheme, Typography } from "@mui/material";
import Navbar from '../components/components(deprecated)/Navbar.jsx';
import Background from '../components/universal/Background.jsx';
import SplitSearchBar from '../components/components(deprecated)/SplitSearchBar.jsx';
import BrowseGenres from '../components/components(deprecated)/BrowseGenres.jsx';
import BrowseCharacters from '../components/components(deprecated)/BrowseCharacters.jsx';
import { useGenres } from '../hooks/useGenres';

export default function Homepage() {
    const navigate = useNavigate();
    const theme = useTheme();
    const { genres, error } = useGenres(navigate);

    const handleGenreClick = (genre) => {
        navigate('/movielist', { state: { genre } });
    };

    const handleCharacterClick = (character) => {
        navigate('/movielist', { state: { character } });
    };

    const characters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
        "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "*"];

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center' }}>
                <Box sx={{
                    display: 'flex',
                    backgroundColor: 'info.light',
                    width: '95vw',
                    height: '85vh',
                    borderRadius: '20px',
                    flexDirection: 'column',
                    alignItems: 'center',
                    paddingTop: '1rem'
                }}>
                    <Typography variant="h3" component='h3' color="primary.dark">
                        Advanced Search
                    </Typography>
                    <SplitSearchBar />
                    <BrowseGenres
                        genres={genres}
                        handleGenreClick={handleGenreClick}
                        theme={theme}
                    />
                    <BrowseCharacters
                        characters={characters}
                        handleCharacterClick={handleCharacterClick}
                        theme={theme}
                    />
                </Box>
            </Background>
        </Box>
    );
}
