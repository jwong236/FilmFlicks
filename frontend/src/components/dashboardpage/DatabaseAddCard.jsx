import React, { useState } from 'react';
import { Box, Button, TextField, Typography, Grid } from '@mui/material';
import axios from 'axios';

const URL = import.meta.env.VITE_BACKEND_URL;

const DatabaseAddCard = ({ setHistory }) => {
    const [movieData, setMovieData] = useState({
        title: '',
        year: '',
        director: '',
        movieIdForStars: '',
        starName: '',
        starBirthYear: '',
        movieIdForGenres: '',
        genreName: '',
    });

    const [starData, setStarData] = useState({ starName: '', starBirthYear: '' });

    const handleInputChange = (field, value) => {
        setMovieData((prev) => ({ ...prev, [field]: value }));
    };

    const handleAddMovieData = async (endpoint, requestData, resetFields) => {
        try {
            const formData = new URLSearchParams();
            for (const key in requestData) {
                formData.append(key, requestData[key]);
            }

            const response = await axios.post(`${URL}/database/movie/${endpoint}`, formData, {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                withCredentials: true,
            });

            const { status, data } = response;

            setHistory((prevHistory) => [
                ...prevHistory,
                {
                    status,
                    action: endpoint,
                    message: typeof data === 'string' ? data : JSON.stringify(data),
                    data: requestData,
                },
            ]);

            if (status === 200) {
                setMovieData((prev) => ({
                    ...prev,
                    ...resetFields,
                }));
            }
        } catch (error) {
            const errorMessage = error.response?.data
                ? typeof error.response.data === 'object'
                    ? JSON.stringify(error.response.data)
                    : error.response.data
                : error.message;

            setHistory((prevHistory) => [
                ...prevHistory,
                {
                    status: error.response?.status || 'Network Error',
                    action: endpoint,
                    message: errorMessage,
                    data: requestData,
                },
            ]);

            console.error(`Error in ${endpoint}:`, errorMessage);
        }
    };

    const handleAddStar = async () => {
        try {
            const formData = new URLSearchParams();
            formData.append('name', starData.starName);
            formData.append('birthYear', starData.starBirthYear || '');

            const response = await axios.post(`${URL}/database/star/add`, formData, {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                withCredentials: true,
            });

            const { status, data } = response;

            setHistory((prevHistory) => [
                ...prevHistory,
                {
                    status,
                    action: 'add-star',
                    message: typeof data === 'string' ? data : JSON.stringify(data),
                    data: { name: starData.starName, birthYear: starData.starBirthYear },
                },
            ]);

            if (status === 200) {
                setStarData({ starName: '', starBirthYear: '' });
            }
        } catch (error) {
            const errorMessage = error.response?.data
                ? typeof error.response.data === 'object'
                    ? JSON.stringify(error.response.data)
                    : error.response.data
                : error.message;

            setHistory((prevHistory) => [
                ...prevHistory,
                {
                    status: error.response?.status || 'Network Error',
                    action: 'add-star',
                    message: errorMessage,
                    data: { name: starData.starName, birthYear: starData.starBirthYear },
                },
            ]);

            console.error(`Error in add-star:`, errorMessage);
        }
    };

    return (
        <Box>
            <Grid container spacing={4}>
                {/* Basic Movie Info */}
                <Grid item xs={12} md={6}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                        <Typography variant="h6" sx={{ color: 'primary.dark' }}>
                            Add a New Movie
                        </Typography>
                        <TextField
                            placeholder="Movie Title"
                            value={movieData.title}
                            onChange={(e) => handleInputChange('title', e.target.value)}
                        />
                        <TextField
                            placeholder="Movie Year"
                            value={movieData.year}
                            onChange={(e) => handleInputChange('year', e.target.value)}
                        />
                        <TextField
                            placeholder="Movie Director"
                            value={movieData.director}
                            onChange={(e) => handleInputChange('director', e.target.value)}
                        />
                        <Button
                            onClick={() =>
                                handleAddMovieData('add-basic', {
                                    title: movieData.title,
                                    year: movieData.year,
                                    director: movieData.director,
                                }, { title: '', year: '', director: '' })
                            }
                            variant="contained"
                            color="primary"
                        >
                            Add Basic Movie
                        </Button>
                    </Box>
                </Grid>

                {/* Add Stars to Movie */}
                <Grid item xs={12} md={6}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                        <Typography variant="h6" sx={{ color: 'primary.dark' }}>
                            Link Stars to Movie
                        </Typography>
                        <TextField
                            placeholder="Movie ID"
                            value={movieData.movieIdForStars}
                            onChange={(e) => handleInputChange('movieIdForStars', e.target.value)}
                        />
                        <TextField
                            placeholder="Star Name(s) (comma-separated)"
                            value={movieData.starName}
                            onChange={(e) => handleInputChange('starName', e.target.value)}
                        />
                        <TextField
                            placeholder="Star Birth Year(s) (comma-separated)"
                            value={movieData.starBirthYear}
                            onChange={(e) => handleInputChange('starBirthYear', e.target.value)}
                        />
                        <Button
                            onClick={() =>
                                handleAddMovieData('add-star', {
                                    movieId: movieData.movieIdForStars,
                                    starNames: movieData.starName,
                                    starBirthYears: movieData.starBirthYear,
                                }, { movieIdForStars: '', starName: '', starBirthYear: '' })
                            }
                            variant="contained"
                        >
                            Link Stars to Movie
                        </Button>
                    </Box>
                </Grid>

                {/* Add Genres to Movie */}
                <Grid item xs={12} md={6}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                        <Typography variant="h6" sx={{ color: 'primary.dark' }}>
                            Link Genres to Movie
                        </Typography>
                        <TextField
                            placeholder="Movie ID"
                            value={movieData.movieIdForGenres}
                            onChange={(e) => handleInputChange('movieIdForGenres', e.target.value)}
                        />
                        <TextField
                            placeholder="Genre(s) (comma-separated)"
                            value={movieData.genreName}
                            onChange={(e) => handleInputChange('genreName', e.target.value)}
                        />
                        <Button
                            onClick={() =>
                                handleAddMovieData('add-genre', {
                                    movieId: movieData.movieIdForGenres,
                                    genreNames: movieData.genreName,
                                }, { movieIdForGenres: '', genreName: '' })
                            }
                            variant="contained"
                        >
                            Link Genres to Movie
                        </Button>
                    </Box>
                </Grid>

                {/* Add Star */}
                <Grid item xs={12} md={6}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                        <Typography variant="h6" sx={{ color: 'primary.dark' }}>
                            Add a New Star
                        </Typography>
                        <TextField
                            placeholder="Star Name"
                            value={starData.starName}
                            onChange={(e) => setStarData({ ...starData, starName: e.target.value })}
                        />
                        <TextField
                            placeholder="Star Birth Year"
                            value={starData.starBirthYear}
                            onChange={(e) => setStarData({ ...starData, starBirthYear: e.target.value })}
                        />
                        <Button onClick={handleAddStar} variant="contained">
                            Add Star
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
};

export default DatabaseAddCard;
