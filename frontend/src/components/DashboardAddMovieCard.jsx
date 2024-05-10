import React, { useState } from 'react';
import { Box, Button, Typography, TextField } from '@mui/material';
import DashboardMetadataTable from './DashboardMetadataTable.jsx'

export default function DashboardAddMovieCard({ employeeName }) {
    const [starName, setStarName] = useState("");
    const [starBirthYear, setStarBirthYear] = useState("");
    const [movieTitle, setMovieTitle] = useState("");
    const [movieYear, setMovieYear] = useState("");
    const [movieDirector, setMovieDirector] = useState("");

    const handleAddStar = () => {
        // Implement fetch to add a new star
    };

    const handleAddMovie = () => {
        // Implement fetch to add a new movie
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
            maxHeight: '80vh',
            overflowY: 'auto'
        }}>
            <Typography variant='h3' sx={{ color: 'primary.dark', width: '100%' }}>
                {employeeName}'s Dashboard:
            </Typography>
            <Box sx={{display: 'flex', flexDirection: 'row', gap: '1rem', width: '100%', justifyContent: 'center'}}>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: '1rem'}}>
                    <Typography variant='h6' sx={{color: 'primary.dark'}}>Add a New Star</Typography>
                    <TextField
                        placeholder="Star Name"
                        value={starName}
                        onChange={(e) => setStarName(e.target.value)}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Star Birth Year"
                        value={starBirthYear}
                        onChange={(e) => setStarBirthYear(e.target.value)}
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
                        value={movieTitle}
                        onChange={(e) => setMovieTitle(e.target.value)}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Movie Year"
                        value={movieYear}
                        onChange={(e) => setMovieYear(e.target.value)}
                        sx={{ width: '16rem' }}
                    />
                    <TextField
                        placeholder="Movie Director"
                        value={movieDirector}
                        onChange={(e) => setMovieDirector(e.target.value)}
                        sx={{ width: '16rem' }}
                    />
                    <Button onClick={handleAddMovie} variant="contained">
                        Add Movie
                    </Button>
                </Box>
            </Box>
            <DashboardMetadataTable />
        </Box>
    );
}
