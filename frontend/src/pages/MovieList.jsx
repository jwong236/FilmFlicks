import {useEffect, useState} from 'react'

import MovieCard from '../components/MovieCard';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';

function MovieList() {
    const [movieData, setMovieData] = useState([]);

    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                const response = await fetch('http://localhost:8080/fabFlix_war/movielist');
                if (!response.ok) {
                    console.error('response is not status 200');
                }
                const data = await response.json();
                if (mounted){
                    setMovieData(data);
                }
            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();
        return ()=>{
            mounted = false;
        }
    }, []);

    return (
        <Box sx={{ flexGrow: 1, padding: 3 }}>
            <Grid container spacing={3}>
                {movieData.length > 0 ? (
                    movieData.map((movie, index) => (
                        <Grid item xs={12} sm={6} md={5} lg={3} key={index}>
                            <MovieCard
                                title={movie.title}
                                year={movie.year}
                                director={movie.director}
                                genres={movie.genres.split(", ")}
                                stars={movie.stars.split(", ")}
                                rating={movie.rating}
                            />
                        </Grid>
                    ))
                ) : (
                    <Box sx={{ width: '100%', textAlign: 'center' }}>
                        Loading movies...
                    </Box>
                )}
            </Grid>
        </Box>
    );
}

export default MovieList
