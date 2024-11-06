import React from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import PageLayout from '../components/common/PageLayout.jsx';
import MovieCard from '../components/common/MovieCard.jsx';
import { useTopMoviesPageHooks } from '../hooks/useTopMoviesPageHooks.jsx';

export default function TopMoviesPage() {
    const { movieData, handleNavigate } = useTopMoviesPageHooks();
    return (
        <PageLayout
            sx={{
                justifyContent: 'center',
                alignItems: 'center',
                padding: '3rem'
            }}
        >
            <Grid container spacing={3}>
                {movieData.length > 0 ? movieData.map((movie) => (
                    <Grid item xs={12} sm={6} md={4} lg={3} xl={3} key={movie.id}>
                        <MovieCard
                            title={movie.title}
                            year={movie.year}
                            director={movie.director}
                            genres={movie.genres.map((genre) => genre.name)}
                            stars={movie.stars.map((star) => star.name)}
                            rating={movie.rating.rating}
                            link={true}
                            onClick={() => handleNavigate(movie.title)}
                        />
                    </Grid>
                )) : (
                    <Box sx={{ width: '100%', textAlign: 'center' }}>
                        Loading movies...
                    </Box>
                )}
            </Grid>
        </PageLayout>
    );
}
