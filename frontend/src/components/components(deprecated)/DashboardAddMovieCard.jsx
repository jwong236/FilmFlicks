import React, { useState } from 'react';
import { Box, Button, Typography, TextField } from '@mui/material';
import DashboardMetadataTable from './DashboardMetadataTable.jsx';
import DashboardHistoryTable from './DashboardHistoryTable.jsx';
const URL = import.meta.env.VITE_URL;
export default function DashboardAddMovieCard({ employeeName }) {
    const [starData, setStarData] = useState({
        starName: "",
        starBirthYear: "",
    });
    const [movieData, setMovieData] = useState({
        title: "",
        year: "",
        director: "",
        starName: "",
        starBirthYear: "",
        genreName: ""
    });
    const [history, setHistory] = useState([]);

    const handleAddStar = async () => {
        try {
            const response = await fetch(`${URL}/insert/star`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    name: starData.starName,
                    birthYear: starData.starBirthYear || null
                })
            });

            if (response.ok) {
                const responseBody = await response.json();
                setHistory(prevHistory => [...prevHistory, {
                    message: responseBody.message,
                    newStarId: responseBody.starId,
                    newMovieId: 0,
                    newGenreId: 0
                }]);
                setStarData({
                    starName: "",
                    starBirthYear: "",
                });
            } else {
                console.error('Failed to add star. Status:', response.status);
            }
        } catch (error) {
            console.error('Failed to send request:', error);
        }
    };


    const handleAddMovie = async () => {
        try {
            const response = await fetch(`${URL}/insert/movie`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify(movieData)
            });

            if (response.ok) {
                const responseBody = await response.json();
                setHistory(prevHistory => [...prevHistory, {
                    message: responseBody.message,
                    newMovieId: responseBody.newMovieId,
                    newStarId: responseBody.newStarId,
                    newGenreId: responseBody.newGenreId
                }]);

                setMovieData({
                    title: "",
                    year: "",
                    director: "",
                    starName: "",
                    starBirthYear: "",
                    genreName: ""
                });
            } else {
                console.error('Failed to add movie. Status:', response.status);
            }
        } catch (error) {
            console.error('Failed to send request:', error);
        }
    };


    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            backgroundColor: 'info.light',
            borderRadius: '20px',
            alignItems: 'center',
            padding: '1rem',
            gap: '20px',
            width: '80%',
            height: '100%',
            maxHeight: '80vh',
            overflowY: 'auto',
        }}>
            <Typography variant='h3' sx={{ color: 'primary.dark', width: '100%' }}>
                {employeeName}'s Dashboard:
            </Typography>
            <Box sx={{display: 'flex', flexDirection: 'row', gap: '1rem', width: '100%', height: '100%', justifyContent: 'center'}}>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem'}}>
                    <Typography variant='h6' sx={{color: 'primary.dark'}}>Add a New Star</Typography>
                    <TextField
                        placeholder="Star Name"
                        value={starData.starName}
                        onChange={e => setStarData({ ...starData, starName: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Star Birth Year"
                        value={starData.starBirthYear}
                        onChange={e => setStarData({ ...starData, starBirthYear: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <Button onClick={handleAddStar} variant="contained">
                        Add Star
                    </Button>
                </Box>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <Typography variant='h6' sx={{color: 'primary.dark'}}>Add a New Movie</Typography>
                    <TextField
                        placeholder="Movie Title"
                        value={movieData.title}
                        onChange={e => setMovieData({ ...movieData, title: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Movie Year"
                        value={movieData.year}
                        onChange={e => setMovieData({ ...movieData, year: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Movie Director"
                        value={movieData.director}
                        onChange={e => setMovieData({ ...movieData, director: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Star Name"
                        value={movieData.starName}
                        onChange={e => setMovieData({ ...movieData, starName: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Star Birth Year"
                        value={movieData.starBirthYear}
                        onChange={e => setMovieData({ ...movieData, starBirthYear: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Genre"
                        value={movieData.genreName}
                        onChange={e => setMovieData({ ...movieData, genreName: e.target.value })}
                        sx={{ width: '16rem' }}
                    />
                    <Button variant='contained' onClick={handleAddMovie}>Add Movie</Button>
                </Box>

            </Box>
            <DashboardHistoryTable history={history} />
            <DashboardMetadataTable />
        </Box>
    );
}
