import {useEffect, useState} from 'react'

import MovieCard from '../components/MovieCard';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import popcorn from '../assets/popcorn.png'

// async function fetchEnv(){
//     return await import.meta.env.VITE_HOST;
// }

const HOST = import.meta.env.VITE_HOST;

function TopMovies() {
    const [movieData, setMovieData] = useState([]);
    //host = await fetchEnv()

    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                console.log(HOST);
                const response = await fetch(`http://${HOST}:8080/fabFlix/topmovies`);
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
        <Box sx={{
            padding: 3,
            backgroundImage: `url(${popcorn})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundAttachment: 'fixed',
            minHeight: '100vh',
            width: '100%',
        }}>
            <Grid container spacing={3}>
                {movieData.length > 0 ? (
                    movieData.map((movie, index) => (
                        <Grid item xs={12} sm={6} md={4} lg={3} xl={3} key={index}>
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

export default TopMovies
