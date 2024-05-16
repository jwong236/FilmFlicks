import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, useTheme, Typography } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import SplitSearchBar from '../components/SplitSearchBar.jsx';
import BrowseGenres from '../components/BrowseGenres';
import BrowseCharacters from '../components/BrowseCharacters';

import axios from 'axios';

const URL = import.meta.env.VITE_URL;

export default function Homepage() {
    const [genre, setGenre] = useState("");
    const [character, setCharacter] = useState("");
    const navigate = useNavigate();
    const theme = useTheme();
    const [genres, setGenres] = useState(["loading..."]);


    const handleGenreClick = (genre) => {
        navigate('/movielist', { state: { genre } });
    };

    const handleCharacterClick = (character) => {
        navigate('/movielist', { state: { character } });
    };

    useEffect(() => {
        const fetchGenres = async () => {
            try {
                const gArray = await axios.get(`${URL}/homepageGenres`, {
                    withCredentials: true
                });
                const tempArray = gArray.data.map(elem => elem.name);
                console.log(tempArray);
                setGenres(tempArray);
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    console.log("Unauthorized access: Redirecting to login page");
                    navigate('/login'); // Redirect on specific status code (401)
                } else {
                    console.error("Error fetching genres:", error); // Log other errors
                }
            }
        };
        fetchGenres();
    }, [navigate]);

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
