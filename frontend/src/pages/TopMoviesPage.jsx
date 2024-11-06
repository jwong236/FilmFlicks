import React from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import PageLayout from '../components/common/PageLayout.jsx';
import FullHeightContainer from '../components/common/FullHeightContainer.jsx';
import MovieCard from '../components/components(deprecated)/MovieCard.jsx';
import { useTopMoviesPageHooks } from '../hooks/useTopMoviesPageHooks.jsx';

export default function TopMoviesPage() {
    const { movieData, navigate } = useTopMoviesPageHooks();

    const handleNavigate = (movieId) => {
        navigate(`/movie/${movieId}`);
    };

    return (
        <PageLayout>
            <FullHeightContainer
                sx={{
                    flexGrow: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                    padding: '3rem',
                }}
            >
                <Grid container spacing={3}>
                    {movieData.length > 0 ? movieData.map((movie) => (
                        <Grid item xs={4} sm={4} md={4} lg={4} xl={3} key={movie.id}>
                            <MovieCard
                                title={movie.title}
                                year={movie.year}
                                director={movie.director}
                                genres={movie.genres.map((genre) => genre.name)}
                                stars={movie.stars.map((star) => star.name)}
                                rating={movie.rating.rating}
                                link={true}
                                onClick={() => handleNavigate(movie.id)} // Pass the movie ID here for navigation
                            />
                        </Grid>
                    )) : (
                        <Box sx={{ width: '100%', textAlign: 'center' }}>
                            Loading movies...
                        </Box>
                    )}
                </Grid>
            </FullHeightContainer>
        </PageLayout>
    );
}
