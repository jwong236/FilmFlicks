import React from 'react';
import { Box, Button, Snackbar, useTheme } from "@mui/material";
import MovieCard from "../components/common/MovieCard.jsx";
import PageLayout from '../components/common/PageLayout.jsx';
import FullHeightContainer from '../components/common/FullHeightContainer.jsx';
import { useSingleMoviePageHooks } from '../hooks/useSingleMoviePageHooks.jsx';

export default function SingleMoviePage() {
    const theme = useTheme();
    const {
        movieData,
        addToShoppingCart,
        openSnackbar,
        snackbarMessage,
        setOpenSnackbar,
    } = useSingleMoviePageHooks();


    return (
        <PageLayout>
            <FullHeightContainer
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                }}
            >
                <Box sx={{ width: '30%' }}>
                    <MovieCard
                        title={movieData.title}
                        year={movieData.year}
                        director={movieData.director}
                        rating={movieData.rating}
                        stars={movieData.stars}
                        genres={movieData.genres}
                        link={false}
                    />
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={addToShoppingCart}
                        sx={{
                            marginTop: '2rem',
                            width: '100%',
                        }}
                    >
                        ADD
                    </Button>
                </Box>
            </FullHeightContainer>
            <Snackbar
                open={openSnackbar}
                message={snackbarMessage}
                autoHideDuration={3000}
                onClose={() => setOpenSnackbar(false)}
            />
        </PageLayout>
    );
}
