import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, TextField, Button, useTheme, InputAdornment, Typography, Chip } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import SearchIcon from '@mui/icons-material/Search';

export default function Homepage() {
    const [title, setTitle] = useState("");
    const [year, setYear] = useState("");
    const [director, setDirector] = useState("");
    const [star, setStar] = useState("");
    const [genre, setGenre] = useState("");
    const [character, setCharacter] = useState("");
    const navigate = useNavigate();
    const theme = useTheme();
    const handleSearch = () => {
        if (!title && !year && !director && !star) {
            console.log('All search fields are empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: { title, year, director, star } });
    };

    const handleGenreClick = (genre) => {
        navigate('/movielist', { state: { genre } });
    };

    const handleCharacterClick = (character) => {
        navigate('/movielist', { state: { character } });
    };

    const genres = ["Action", "Adult", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary",
        "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Musical", "Mystery", "Reality-TV", "Romance",
        "Sci-Fi", "Sport", "Thriller", "War", "Western"];
    const characters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
        "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "*"];

    const textFieldStyle = {
        "& .MuiInputBase-input": {
            color: theme.palette.primary.dark,
        },
        "& .MuiInputBase-root": {
            backgroundColor: theme.palette.secondary.light,
        },

    };

    const chipStyle = {
        cursor: 'pointer',
        color: theme.palette.primary.dark,
        paddingLeft: '1rem',
        paddingRight: '1rem',
        fontSize: '1.2rem',
        backgroundColor: '#F8EFD3',
        '&:hover': {
            backgroundColor: 'secondary.dark' // Background color on hover
        }
    };

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <Box sx={{
                    display: 'flex',
                    backgroundColor: 'info.light',
                    width: '95vw',
                    height: '85vh',
                    borderRadius: '20px',
                    flexDirection: 'column',
                }}>
                    <Box sx={{
                        alignSelf: 'flex-start',
                        display: 'flex',
                        flexDirection: 'row',
                        width: '100%',
                        paddingTop: '1rem',
                        paddingBottom: '1rem',
                        justifyContent: 'center',
                        alignItems: 'center',
                        gap: '1rem',
                    }}>
                        <TextField
                            placeholder="Title"
                            value={title}
                            onChange={e => setTitle(e.target.value)}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <SearchIcon />
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                ...textFieldStyle,
                                width: '30vw',
                                minWidth: '8rem'
                            }}
                        />
                        <TextField
                            placeholder="Year"
                            value={year}
                            onChange={e => setYear(e.target.value)}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <SearchIcon />
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                ...textFieldStyle,
                                width: '6vw',
                                minWidth: '6rem'
                            }}
                        />
                        <TextField
                            placeholder="Director"
                            value={director}
                            onChange={e => setDirector(e.target.value)}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <SearchIcon />
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                ...textFieldStyle,
                                width: '15vw',
                                minWidth: '15rem'
                            }}
                        />
                        <TextField
                            placeholder="Star"
                            value={star}
                            onChange={e => setStar(e.target.value)}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <SearchIcon />
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                ...textFieldStyle,
                                width: '15vw',
                                minWidth: '15rem'
                            }}
                        />
                        <Button
                            onClick={handleSearch}
                            sx={{
                                backgroundColor: '#FF907E',
                                color: 'secondary.light',
                                fontWeight: 'bold',
                                fontSize: '1rem',

                                '&:hover': {
                                    backgroundColor: 'primary.main',
                                    transform: 'scale(1.05)'
                                }
                            }}>
                            Search
                        </Button>
                    </Box>
                    <Box sx={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        flexDirection: 'column',
                        paddingTop: '2rem',
                        paddingBottom: '2rem'
                    }}>
                        <Typography variant = "h3" component = 'h3' color = "primary.dark">
                            Browse by Movie Genre
                        </Typography>

                    </Box>
                    <Box sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        justifyContent: 'center',
                        gap: '1rem',
                        paddingTop: '1rem',
                        paddingBottom: '1rem',
                        paddingRight: '1rem',
                        paddingLeft: '1rem'
                    }}>
                        {genres.map((genreName) => (
                            <Chip
                                key={genreName}
                                label={genreName}
                                onClick={() => handleGenreClick(genreName)}
                                sx={chipStyle}
                            />
                        ))}
                    </Box>
                    <Box sx={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        flexDirection: 'column',
                        paddingTop: '2rem',
                        paddingBottom: '2rem'
                    }}>
                        <Typography variant = "h3" component = 'h3' color = "primary.dark">
                            Browse by Movie Title
                        </Typography>
                    </Box>
                    <Box sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        justifyContent: 'center',
                        gap: '1rem',
                        paddingTop: '1rem',
                        paddingBottom: '1rem',
                        paddingRight: '1rem',
                        paddingLeft: '1rem'
                    }}>
                        {characters.map((character) => (
                            <Chip
                                key={character}
                                label={character}
                                onClick={() => handleCharacterClick(character)}
                                sx={chipStyle}
                            />
                        ))}
                    </Box>
                </Box>

            </Background>
        </Box>
    );
}
