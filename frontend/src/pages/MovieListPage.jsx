import React, { useEffect } from 'react';
import { Box, Button, Typography, Snackbar } from "@mui/material";
import { useNavigate, useLocation } from 'react-router-dom';
import PageLayout from '../components/common/PageLayout.jsx';
import FullHeightContainer from '../components/common/FullHeightContainer.jsx';
import MovieListTable from '../components/movielistpage/MovieListTable.jsx';
import SplitSearchBar from '../components/homepage/SplitSearchBar.jsx';
import MoviesPerPageDropdown from '../components/components(deprecated)/MoviesPerPageDropdown.jsx';
import SortByDropdown from '../components/components(deprecated)/SortByDropdown.jsx';
import { useMovieListPageHooks } from '../hooks/useMovieListPageHooks';

export default function MovieList() {
    const {
        pageData,
        snackbar,
        fetchMovies,
        handleNextClick,
        handlePrevClick,
        addToShoppingCart,
        setPageSize,
        setSortRule,
        closeSnackbar,
    } = useMovieListPageHooks();

    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchMovies(location.state);
    }, [location.state, pageData.pageNumber, pageData.pageSize, pageData.sortRule, navigate]);

    return (
        <PageLayout>
            <FullHeightContainer
                sx={{
                    display: 'flex',
                    width: '95vw',
                    height: '85vh',
                    backgroundColor: 'info.light',
                    borderRadius: '20px',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography variant="h4" component='h4' color="primary.dark" sx={{ margin: '1rem' }}>
                    Advanced Search
                </Typography>
                <SplitSearchBar sx={{ marginBottom: '1rem' }} />
                <Box
                    sx={{
                        display: 'flex',
                        width: '95%',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        paddingBottom: '.5rem'
                    }}
                >
                    <Typography variant='h4' component='h4' color='primary.dark' sx={{ fontWeight: 'bold' }}>
                        Results
                    </Typography>
                    <Box
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            gap: '1rem'
                        }}
                    >
                        <MoviesPerPageDropdown pageSize={pageData.pageSize} setPageSize={setPageSize} />
                        <SortByDropdown sortRule={pageData.sortRule} setSortRule={setSortRule} />
                    </Box>
                </Box>
                <Box
                    sx={{
                        display: 'flex',
                        flexGrow: 1,
                        width: '95%',
                        overflowY: 'auto',
                        overflowX: 'auto',
                        flexDirection: 'row'
                    }}
                >
                    <MovieListTable data={pageData.movies} handleAdd={addToShoppingCart} />
                    <Snackbar
                        open={snackbar.open}
                        message={snackbar.message}
                        autoHideDuration={3000}
                        onClose={closeSnackbar}
                    />
                </Box>
                <Box
                    sx={{
                        display: 'flex',
                        paddingBottom: '.5rem',
                        paddingTop: '.5rem',
                        flexDirection: 'row',
                        alignItems: 'center',
                        gap: '2rem'
                    }}
                >
                    <Button
                        sx={{
                            backgroundColor: 'primary.main',
                            color: 'secondary.light',
                            height: '4vh',
                            '&:hover': {
                                backgroundColor: 'primary.dark'
                            }
                        }}
                        onClick={handlePrevClick}
                    >
                        Previous
                    </Button>
                    <Button
                        sx={{
                            backgroundColor: 'primary.main',
                            color: 'secondary.light',
                            height: '4vh',
                            '&:hover': {
                                backgroundColor: 'primary.dark'
                            }
                        }}
                        onClick={handleNextClick}
                    >
                        Next
                    </Button>
                </Box>
            </FullHeightContainer>
        </PageLayout>
    );
}
