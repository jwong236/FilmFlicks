import { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import NavBar from '../components/Navbar.jsx';
import Background from '../components/Background.jsx';
import MovieCard from '../components/MovieCard';

const HOST = import.meta.env.VITE_HOST;

function TopMovies() {
    const [movieData, setMovieData] = useState([]);

    useEffect(() => {
        let mounted = true;
        async function fetchMovieData() {
            try {
                const response = await fetch(`http://${HOST}:8080/fabFlix/topmovies`);
                if (!response.ok) {
                    console.error('Response is not status 200');
                    return;
                }
                const data = await response.json();
                if (mounted) {
                    setMovieData(data);
                }
            } catch (error) {
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();
        return () => {
            mounted = false;
        }
    }, []);

    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            height: '100vh',
            width: '100vw',
        }}>
            <NavBar />
            <Background sx={{
                flexGrow: 1,
                justifyContent: 'center',
                alignItems: 'center',
                backgroundSize: 'cover',
                backgroundAttachment: 'fixed',
                padding: '3rem'
            }}>
                <Grid container spacing={3}>
                    {movieData.length > 0 ? movieData.map((movie, index) => (
                        <Grid item xs={4} sm={4} md={4} lg={4} xl={3} key={index}>
                            <MovieCard
                                title={movie.title}
                                year={movie.year}
                                director={movie.director}
                                genres={movie.genres.split(", ")}
                                stars={movie.stars.split(", ")}
                                rating={movie.rating}
                                link={true}
                            />
                        </Grid>
                    )) : (
                        <Box sx={{ width: '100%', textAlign: 'center' }}>
                            Loading movies...
                        </Box>
                    )}
                </Grid>
            </Background>
        </Box>
    );
}

export default TopMovies;
