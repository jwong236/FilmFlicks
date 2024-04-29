import React, {useEffect, useState} from 'react'
import {Link, useLocation, useNavigate} from "react-router-dom";
import {Box, Button, useTheme, Container, Snackbar} from "@mui/material";
import MovieCard from "../components/MovieCard.jsx";
import HomeButton from "../components/HomeButton.jsx";
import popcorn from '../assets/popcorn.png'

const HOST = import.meta.env.VITE_HOST;

export default function SingleMovie() {
    const [movieData, setMovieData] = useState({
        title: "default title",
        year: "default year",
        director: "default director",
        genres: ["genre1"],
        stars: ["star1"]

    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const title = search_params.get('title');
    const navigate = useNavigate();
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");

    const theme = useTheme();
    const textFieldStyle = {
        "& .MuiInputBase-input": {
            color: theme.palette.primary.dark,
        },
        "& .MuiInputBase-root": {
            backgroundColor: theme.palette.secondary.light,
        },
    };



    const addToShoppingCart = async (movie) => {
        try {
            console.log("The movie that was added is: " + movie.title);
            const response = await fetch(`http://${HOST}:8080/fabFlix/add`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({ "movieTitle": movie.title })
            });

            if (response.status === 401) {
                console.log("REDIRECTION FROM MOVIE LIST");
                navigate('/login');
            } else {
                setSnackbarMessage("Movie added successfully!");
                setOpenSnackbar(true);
                var timer = setTimeout(() => {
                    setOpenSnackbar(false);
                }, 3000);
            }
        } catch (error) {
            console.error('Error adding to cart: ', error);
            setSnackbarMessage("Failed to add movie to cart.");
            setOpenSnackbar(true);
            setTimeout(() => {
                setOpenSnackbar(false);
            }, 3000);
        }
    };

    useEffect(() => {
        let mounted = true;
        async function fetchMovieData(){
            try{
                const response = await fetch(`http://${HOST}:8080/fabFlix/singlemovie?title=${encodeURIComponent(title)}`,{
                    credentials: 'include'
                });

                if (!response.ok) {
                    console.error('response is not status 200');
                }

                console.log("DATA AS TEXT IN MOVIE LIST " + response.text);

                if (response.status === 401){
                    console.log("REDIRECTION FROM MOVIE LIST");
                    navigate('/login')
                }else{
                    const jsonData = await response.json();
                    console.log("no need to login");
                    if (mounted){
                        console.log(jsonData);
                        setMovieData(jsonData[0]);
                    }
                }

            }catch(error){
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();

        return ()=>{
            mounted = false;
        }
    }, [title]);

    return (
            <Box sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                width: '100%',
                height: '100vh',
                backgroundColor: '#f1f1f1',
                padding: 3,
                backgroundImage: `url(${popcorn})`,
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
                }}>
                <HomeButton/>
                <Box sx={{width: '30%'}}>
                    <Button
                        sx={{
                            backgroundColor: theme.palette.primary.main,
                            color: theme.palette.secondary.light,
                            '&:hover': {
                                backgroundColor: theme.palette.primary.dark
                            }
                        }}
                        onClick={() => addToShoppingCart(movieData)}>
                        Add
                    </Button>
                    <MovieCard title={movieData.title} year={movieData.year} director={movieData.director} rating={movieData.rating} stars={movieData.stars} genres={movieData.genres} link={false}/>

                </Box>

                <Snackbar
                    open={openSnackbar}
                    message={snackbarMessage}
                    autoHideDuration={3000}
                    onClose={() => setOpenSnackbar(false)}
                />
            </Box>
    );

}
